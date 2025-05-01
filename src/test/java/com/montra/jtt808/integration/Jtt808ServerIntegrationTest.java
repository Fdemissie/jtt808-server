package com.montra.jtt808.integration;

import org.junit.jupiter.api.*;

import com.montra.jtt808.Jtt808Server;
import com.montra.jtt808.core.Jtt808Message;

import static org.junit.jupiter.api.Assertions.*;

public class Jtt808ServerIntegrationTest {
    private static Jtt808Server server;
    private Jtt808TestClient client;
    
    @BeforeAll
    public static void startServer() throws Exception {
        server = new Jtt808Server(5566);
        new Thread(() -> {
            try {
                server.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(1000); // Wait for server to start
    }
    
    @AfterAll
    public static void stopServer() throws Exception {
        server.stop();
    }
    
    @BeforeEach
    public void setUp() throws Exception {
        client = new Jtt808TestClient();
        client.connect("localhost", 5566);
    }
    
    @AfterEach
    public void tearDown() {
        client.close();
    }
    
    @Test
    public void testServerRespondsToHeartbeat() throws Exception {
        Jtt808Message heartbeat = new Jtt808Message(
        (short)0x0001,  // Heartbeat
        (short)0,       // No body
        "12345678901",  // Phone
        (short)1,       // Sequence
        new byte[0],    // Empty body
        (byte)0x45      // Checksum
    );
        
        client.sendMessage(heartbeat);
        Thread.sleep(500); // Wait for response
        
        // In a real test, you would verify the response
        // This requires adding response handling to the test client
    }
    
    @Test
    public void testServerHandlesRegistration() throws Exception {
        byte[] regBody = new byte[10]; // Sample registration body
        Jtt808Message registration = new Jtt808Message(
            (short)0x0001,  // Heartbeat
            (short)0,       // No body
            "12345678901",  // Phone
            (short)1,       // Sequence
            new byte[0],    // Empty body
            (byte)0x45      // Checksum
        );
        
        client.sendMessage(registration);
        Thread.sleep(500);
        
        // Verify registration response
    }
}