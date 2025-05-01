package com.montra.jtt808.core.handler;

import com.montra.jtt808.core.Jtt808Message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class Jtt808ServerHandler extends SimpleChannelInboundHandler<Jtt808Message> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Jtt808Message msg) throws Exception {
        try {
            // Verify checksum first
            if (!verifyChecksum(msg)) {
                sendErrorResponse(ctx, msg, "Invalid checksum");
                return;
            }

            switch (msg.getMessageId()) {
                case 0x0001: // Heartbeat
                    handleHeartbeat(ctx, msg);
                    break;
                    
                case 0x0100: // Registration
                    handleRegistration(ctx, msg);
                    break;
                    
                case 0x0200: // Location report
                    handleLocationReport(ctx, msg);
                    break;
                    
                default:
                    handleUnknownMessage(ctx, msg);
            }
        } catch (Exception e) {
            ctx.fireExceptionCaught(e);
        }
    }

    private boolean verifyChecksum(Jtt808Message msg) {
        // Implement your checksum verification logic
        // Compare calculated checksum with msg.getChecksum()
        return true; // Simplified for example
    }

    private void handleHeartbeat(ChannelHandlerContext ctx, Jtt808Message msg) {
        System.out.printf("Heartbeat from %s (seq: %d)%n", 
            msg.getTerminalPhone(), msg.getSequenceNum());
            
        // Build response (Message ID 0x8001)
        Jtt808Message response = new Jtt808Message(
            (short) 0x8001,
            (short) 0,
            msg.getTerminalPhone(),
            msg.getSequenceNum(),
            new byte[0], // Empty body
            (byte) 0     // Checksum will be calculated by encoder
        );
        
        ctx.writeAndFlush(response);
    }

    private void handleRegistration(ChannelHandlerContext ctx, Jtt808Message msg) {
        System.out.printf("Registration request from %s%n", msg.getTerminalPhone());
        
        // Process registration (validate device, etc.)
        boolean success = true; // Your validation logic here
        byte resultCode = success ? (byte)0 : (byte)1;
        
        // Build response (Message ID 0x8100)
        byte[] responseBody = new byte[]{resultCode};
        Jtt808Message response = new Jtt808Message(
            (short) 0x8100,
            (short) 0,
            msg.getTerminalPhone(),
            msg.getSequenceNum(),
            responseBody,
            (byte)0
        );
        
        ctx.writeAndFlush(response);
    }

    private void handleLocationReport(ChannelHandlerContext ctx, Jtt808Message msg) {
        ByteBuf buf = Unpooled.wrappedBuffer(msg.getMessageBody());
        
        try {
            // Parse location data (example implementation)
            int alarmFlag = buf.readInt();
            int statusFlag = buf.readInt();
            double latitude = buf.readInt() / 1_000_000.0;
            double longitude = buf.readInt() / 1_000_000.0;
            short speed = buf.readShort();
            short direction = buf.readShort();
            String timestamp = readBcdTime(buf);
            
            System.out.printf("Location from %s: %.6f,%.6f %s%n",
                msg.getTerminalPhone(), latitude, longitude, timestamp);
                
            // Send standard acknowledgement
            Jtt808Message ack = new Jtt808Message(
                (short) 0x8001,
                (short) 0,
                msg.getTerminalPhone(),
                msg.getSequenceNum(),
                new byte[0],
                (byte)0
            );
            ctx.writeAndFlush(ack);
            
        } finally {
            buf.release();
        }
    }

    private String readBcdTime(ByteBuf buf) {
        byte[] bytes = new byte[6];
        buf.readBytes(bytes);
        return String.format("20%02d-%02d-%02d %02d:%02d:%02d",
            bytes[0], bytes[1], bytes[2], bytes[3], bytes[4], bytes[5]);
    }

    private void handleUnknownMessage(ChannelHandlerContext ctx, Jtt808Message msg) {
        System.out.printf("Unknown message ID 0x%04X from %s%n",
            msg.getMessageId(), msg.getTerminalPhone());
    }

    private void sendErrorResponse(ChannelHandlerContext ctx, Jtt808Message msg, String reason) {
        System.out.printf("Error processing message from %s: %s%n",
            msg.getTerminalPhone(), reason);
            
        // You might send a generic error response here
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.err.println("Handler error:");
        cause.printStackTrace();
        ctx.close();
    }
}