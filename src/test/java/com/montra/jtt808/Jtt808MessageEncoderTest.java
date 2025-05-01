package com.montra.jtt808;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import com.montra.jtt808.core.Jtt808Message;
import com.montra.jtt808.core.handler.Jtt808MessageDecoder;
import com.montra.jtt808.core.handler.Jtt808MessageEncoder;
import com.montra.jtt808.core.handler.Jtt808ServerHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.embedded.EmbeddedChannel;

public class Jtt808MessageEncoderTest {
    // @Test
    // public void testHeartbeatHandler() {
    //     // Create test message
    //     Jtt808Message heartbeat = new Jtt808Message(
    //         (short)0x0001,  // Heartbeat
    //         (short)0,       // No body
    //         "12345678901",  // Phone
    //         (short)1,       // Sequence
    //         new byte[0],    // Empty body
    //         (byte)0x45      // Checksum
    //     );
        
    //     EmbeddedChannel channel = new EmbeddedChannel(
    //         new Jtt808MessageEncoder(),  // Converts Jtt808Message -> ByteBuf
    //         new Jtt808MessageDecoder(),  // Converts ByteBuf -> Jtt808Message
    //         new Jtt808ServerHandler()    // Processes Jtt808Message
    //     );
        
    //     // Write the message through the pipeline
    //     channel.writeOutbound(heartbeat);  // First encode the message
        
    //     // Read the encoded ByteBuf and write it back in as inbound
    //     ByteBuf encoded = channel.readOutbound();
    //     channel.writeInbound(encoded);
        
    //     // Verify the response
    //     Jtt808Message response = channel.readOutbound();
    //     assertNotNull(response);
    //     assertEquals(0x8001, response.getMessageId());  // Should be heartbeat response
    //     assertEquals("12345678901", response.getTerminalPhone());
        
    //     encoded.release();  // Important: release the ByteBuf
    // }
}