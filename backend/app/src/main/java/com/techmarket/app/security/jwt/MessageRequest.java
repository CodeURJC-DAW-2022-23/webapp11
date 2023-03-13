package com.techmarket.app.security.jwt;

public class MessageRequest {


    private String message;
    private Long id;

    public MessageRequest() {
    }

    public MessageRequest(String message, Long id) {
        this.message = message;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MessageRequest [message=" + message + ", id=" + id + "]";
    }
}
