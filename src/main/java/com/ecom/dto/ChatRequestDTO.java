package com.ecom.dto;

import java.util.List;

public class ChatRequestDTO {

    private List<ChatMessageDTO> messages;

    private String pageContext;

    private Long contextId;

    public ChatRequestDTO() {}

    public List<ChatMessageDTO> getMessages() { return messages; }
    public void setMessages(List<ChatMessageDTO> messages) { this.messages = messages; }

    public String getPageContext() { return pageContext; }
    public void setPageContext(String pageContext) { this.pageContext = pageContext; }

    public Long getContextId() { return contextId; }
    public void setContextId(Long contextId) { this.contextId = contextId; }
}
