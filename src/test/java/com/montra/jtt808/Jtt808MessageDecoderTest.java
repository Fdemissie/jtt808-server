package com.montra.jtt808;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import com.montra.jtt808.core.Jtt808Message;
import com.montra.jtt808.core.handler.Jtt808MessageDecoder;

import static org.junit.jupiter.api.Assertions.*;

public class Jtt808MessageDecoderTest {
    // @Test
    // public void testDecodeLocationReport() throws Exception {
    //     // Build a complete valid message
    //     ByteBuf buf = Unpooled.buffer();
        
    //     // Header
    //     buf.writeByte(0x7E); // Start flag
    //     buf.writeShort(0x0200); // Message ID
    //     buf.writeShort(0x0025); // Body length = 37
        
    //     // Terminal info
    //     buf.writeBytes(new byte[]{0x01, 0x23, 0x45, 0x67, (byte)0x89, 0x01}); // Phone
    //     buf.writeShort(0x0001); // Sequence
        
    //     // Body (37 bytes)
    //     buf.writeBytes(new byte[37]);
        
    //     // Footer
    //     byte checksum = calculateChecksum(buf);
    //     buf.writeByte(checksum);
    //     buf.writeByte(0x7E); // End flag
        
    //     // Test decoding
    //     Jtt808MessageDecoder decoder = new Jtt808MessageDecoder();
    //     EmbeddedChannel channel = new EmbeddedChannel(decoder);
        
    //     assertTrue(channel.writeInbound(buf.duplicate()));
    //     Jtt808Message decoded = channel.readInbound();
        
    //     assertNotNull(decoded);
    //     assertEquals(0x0200, decoded.getMessageId());
    //     assertEquals("012345678901", decoded.getTerminalPhone());
        
    //     buf.release();
    //     channel.finishAndReleaseAll();
    // }
    
    // private byte calculateChecksum(ByteBuf buf) {
    //     byte checksum = 0;
    //     // Start from byte after start flag (index 1)
    //     for (int i = 1; i < buf.writerIndex(); i++) {
    //         checksum ^= buf.getByte(i);
    //     }
    //     return checksum;
    // }
    // @Test
    // public void testDecodeHeartbeatMessage() throws Exception {
    //     // Sample JTT808 heartbeat message (hex): 7E 00 01 00 00 01 23 45 67 89 01 00 00 01 7E
    //     byte[] heartbeat = {
    //         0x7E,                               // Start flag
    //         0x00, 0x01,                         // Message ID (0x0001)
    //         0x00, 0x00,                         // Body properties (length=0)
    //         0x01, 0x23, 0x45, 0x67, (byte) 0x89, 0x01, // Terminal phone (12345678901)
    //         0x00, 0x00,                         // Sequence number
    //         // No message body
    //         0x01,                               // Checksum (dummy value)
    //         0x7E                                // End flag
    //     };
        
    //     EmbeddedChannel channel = new EmbeddedChannel(new Jtt808MessageDecoder());
    //     ByteBuf buf = Unpooled.wrappedBuffer(heartbeat);
    //     channel.writeInbound(buf);
        
    //     Jtt808Message msg = channel.readInbound();
    //     assertNotNull(msg);
    //     assertEquals(0x0001, msg.getMessageId());
    //     assertEquals("12345678901", msg.getTerminalPhone());
    //     assertEquals(0, msg.getSequenceNum());
    //     assertNull(msg.getMessageBody());
    // }

    // @Test
    // public void testDecodeLocationMessage() throws Exception {
    //     // Sample location report message
    //     byte[] locationMsg = {
    //         0x7E,                               // Start flag
    //         0x02, 0x00,                         // Message ID (0x0200)
    //         0x00, 0x25,                         // Body properties (length=37)
    //         0x01, 0x23, 0x45, 0x67, (byte) 0x89, 0x01, // Terminal phone
    //         0x00, 0x01,                         // Sequence number
    //         // Message body (37 bytes)
    //         0x00, 0x00, 0x00, 0x00,             // Alarm flag
    //         0x00, 0x00, 0x00, 0x00,             // Status flag
    //         0x02, 0x62, 0x57, (byte)0xF0,       // Latitude (39956144 = 39.956144)
    //         0x01, 0x63, 0x58, (byte)0xF0,       // Longitude (116.345678)
    //         0x00, 0x3C,                         // Speed (60 km/h)
    //         0x00, (byte)0xB4,                   // Direction (180 degrees)
    //         0x23, 0x05, 0x19, 0x11, 0x30, 0x30, // Timestamp (BCD: 23-05-19 11:30:30)
    //         0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, // Additional fields
    //         0x45,                               // Checksum (dummy value)
    //         0x7E                                // End flag
    //     };
        
    //     EmbeddedChannel channel = new EmbeddedChannel(new Jtt808MessageDecoder());
    //     // assertTrue(channel.writeInbound(Unpooled.wrappedBuffer(locationMsg)));
        
    //     Jtt808Message msg = channel.readInbound();
    //     assertNotNull(msg);
    //     assertEquals(0x0200, msg.getMessageId());
    //     assertEquals(37, msg.getMessageBody().length);
    // }
}