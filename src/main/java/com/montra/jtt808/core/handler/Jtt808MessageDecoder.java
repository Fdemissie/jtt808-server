package com.montra.jtt808.core.handler;

import com.montra.jtt808.core.Jtt808Message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class Jtt808MessageDecoder extends LengthFieldBasedFrameDecoder {
    private static final int HEADER_LENGTH = 13; // Start(1) + ID(2) + Props(2) + Phone(6) + Seq(2)
    private static final int FOOTER_LENGTH = 2;  // Checksum(1) + End(1)

    public Jtt808MessageDecoder() {
        super(
            1024,    // maxFrameLength
            2,       // lengthFieldOffset (after message ID)
            2,       // lengthFieldLength (body properties)
            -12,     // lengthAdjustment: -(phone(6) + seq(2) + checksum(1) + end(1) + id(2))
            0        // initialBytesToStrip
        );
    }
    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null) {
            return null;
        }

        try {
            // Verify start flag
            if (frame.readByte() != 0x7E) {
                throw new CorruptedFrameException("Invalid start flag");
            }

            // Read message ID
            short messageId = frame.readShort();

            // Read body properties
            short bodyProps = frame.readShort();
            int bodyLength = bodyProps & 0x03FF; // Lower 10 bits

            // Read terminal phone (6 bytes BCD)
            String phone = readBcd(frame);

            // Read sequence number
            short seqNum = frame.readShort();

            // Read message body
            byte[] body = new byte[bodyLength];
            frame.readBytes(body);

            // Read checksum
            byte checksum = frame.readByte();

            // Verify end flag
            if (frame.readByte() != 0x7E) {
                throw new CorruptedFrameException("Invalid end flag");
            }

            return new Jtt808Message(messageId, bodyProps, phone, seqNum, body, checksum);
        } finally {
            frame.release();
        }
    }

    private String readBcd(ByteBuf buf) {
        byte[] phoneBytes = new byte[6];
        buf.readBytes(phoneBytes);
        return bytesToBcdString(phoneBytes);
    }

    private String bytesToBcdString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    private byte calculateChecksum(ByteBuf buf, int endIndex) {
        byte checksum = 0;
        int startIndex = 1; // Skip start flag (0x7E)
        for (int i = startIndex; i < endIndex; i++) {
            checksum ^= buf.getByte(i);
        }
        return checksum;
    }
}