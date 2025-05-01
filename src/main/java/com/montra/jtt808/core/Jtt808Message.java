package com.montra.jtt808.core;

public class Jtt808Message {
    private final short messageId;
    private final short messageBodyProps;
    private final String terminalPhone;
    private final short sequenceNum;
    private final byte[] messageBody;
    private final byte checksum;

    public Jtt808Message(short messageId, short messageBodyProps,
                        String terminalPhone, short sequenceNum,
                        byte[] messageBody, byte checksum) {
        this.messageId = messageId;
        this.messageBodyProps = messageBodyProps;
        this.terminalPhone = terminalPhone;
        this.sequenceNum = sequenceNum;
        this.messageBody = messageBody;
        this.checksum = checksum;
    }

    // Getters
    public short getMessageId() { return messageId; }
    public short getMessageBodyProps() { return messageBodyProps; }
    public String getTerminalPhone() { return terminalPhone; }
    public short getSequenceNum() { return sequenceNum; }
    public byte[] getMessageBody() { return messageBody; }
    public byte getChecksum() { return checksum; }
}
