package com.montra.jtt808.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class Jtt808TestDataGenerator {
    public static byte[] createLocationMessageBody() {
        ByteBuf buf = Unpooled.buffer(37);
        
        // Alarm flag (none)
        buf.writeInt(0);
        
        // Status flags
        buf.writeInt(0x00000002); // Bit 1: ACC ON
        
        // Latitude (39.956144)
        buf.writeInt(39956144);
        
        // Longitude (116.345678)
        buf.writeInt(116345678);
        
        // Speed (60 km/h)
        buf.writeShort(60);
        
        // Direction (180 degrees)
        buf.writeShort(180);
        
        // Timestamp (BCD: 23-05-19 11:30:30)
        buf.writeByte(0x23); // Year
        buf.writeByte(0x05); // Month
        buf.writeByte(0x19); // Day
        buf.writeByte(0x11); // Hour
        buf.writeByte(0x30); // Minute
        buf.writeByte(0x30); // Second
        
        // Additional fields
        buf.writeBytes(new byte[9]);
        
        byte[] result = new byte[buf.readableBytes()];
        buf.readBytes(result);
        return result;
    }
    
    // Add methods for other message types...
}