package com.ecom.dto;

public class ChatResponseDTO {

    private boolean success;
    private String reply;   
    private String error; 

    public ChatResponseDTO() {}

    public static ChatResponseDTO ok(String reply) {
        ChatResponseDTO r = new ChatResponseDTO();
        r.success = true;
        r.reply = reply;
        return r;
    }

    public static ChatResponseDTO error(String errorMsg) {
        ChatResponseDTO r = new ChatResponseDTO();
        r.success = false;
        r.error = errorMsg;
        return r;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getReply() { return reply; }
    public void setReply(String reply) { this.reply = reply; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
}
