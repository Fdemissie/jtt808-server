package com.montra.jtt808.core.handler;

import com.montra.jtt808.core.Jtt808Message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class Jtt808MessageEncoder extends MessageToByteEncoder<Jtt808Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Jtt808Message msg, ByteBuf out) throws Exception {
        // Write start flag
        out.writeByte(0x7E);
        
        // Write message ID
        out.writeShort(msg.getMessageId());
        
        // Write message body properties (length in lower 10 bits)
        out.writeShort(msg.getMessageBodyProps());
        
        // Write terminal phone (6 bytes BCD)
        writeBcd(out, msg.getTerminalPhone(), 6);
        
        // Write sequence number
        out.writeShort(msg.getSequenceNum());
        
        // Write message body
        if (msg.getMessageBody() != null && msg.getMessageBody().length > 0) {
            out.writeBytes(msg.getMessageBody());
        }
        
        // Calculate and write checksum
        byte checksum = calculateChecksum(out);
        out.writeByte(checksum);
        
        // Write end flag
        out.writeByte(0x7E);
    }

    private void writeBcd(ByteBuf out, String phone, int length) {
        byte[] bytes = new byte[length];
        for (int i = 0; i < Math.min(length, phone.length() / 2); i++) {
            bytes[i] = (byte) Integer.parseInt(phone.substring(i*2, i*2+2));
        }
        out.writeBytes(bytes);
    }

    private byte calculateChecksum(ByteBuf buf) {
        byte checksum = 0;
        int start = buf.readerIndex() + 1; // Skip start flag
        int end = buf.writerIndex();
        for (int i = start; i < end; i++) {
            checksum ^= buf.getByte(i);
        }
        return checksum;
    }
}