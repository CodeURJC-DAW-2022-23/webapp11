package com.techmarket.app.model;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String messageId;
    private String text;
    private boolean status; // resolved or not resolved

}

    public String getMessageId() {
        return this.messageId;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
