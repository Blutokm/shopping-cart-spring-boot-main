package com.ecom.controller;

import com.ecom.dto.ChatRequestDTO;
import com.ecom.dto.ChatResponseDTO;
import com.ecom.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private GeminiService geminiService;


    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ChatResponseDTO> chat(@RequestBody ChatRequestDTO request) {

        if (request.getMessages() == null || request.getMessages().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(ChatResponseDTO.error("Tin nhắn không được để trống."));
        }

        String lastMessage = request.getMessages()
                .get(request.getMessages().size() - 1)
                .getContent();
        if (lastMessage == null || lastMessage.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(ChatResponseDTO.error("Tin nhắn không hợp lệ."));
        }
        if (lastMessage.length() > 2000) {
            return ResponseEntity.badRequest()
                    .body(ChatResponseDTO.error("Tin nhắn quá dài. Vui lòng rút gọn."));
        }

        if (request.getMessages().size() > 20) {
            request.setMessages(
                request.getMessages().subList(
                    request.getMessages().size() - 20,
                    request.getMessages().size()
                )
            );
        }

        ChatResponseDTO response = geminiService.chat(request);
        return ResponseEntity.ok(response);
    }
}
