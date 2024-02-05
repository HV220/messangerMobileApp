package com.example.messanger.models;

public class Message {

    private String textMessage;
    private String senderId;
    private String receiverId;

    public Message(String textMessage, String senderId, String receiverId) {
        this.textMessage = textMessage;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public Message() {
    }

    public String getTextMessage() {
        return textMessage;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "textMessage='" + textMessage + '\'' +
                ", senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                '}';
    }
}
