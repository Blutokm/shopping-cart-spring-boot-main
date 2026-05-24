package com.ecom.service;

import com.ecom.dto.ChatMessageDTO;
import com.ecom.dto.ChatRequestDTO;
import com.ecom.dto.ChatResponseDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Value("${gemini.max-tokens:1024}")
    private int maxTokens;

    @Value("${store.name:My Shop}")
    private String storeName;

    @Value("${store.phone:}")
    private String storePhone;

    @Value("${store.email:}")
    private String storeEmail;

    @Value("${store.address:}")
    private String storeAddress;

    @Autowired
    private ChatContextService chatContextService; 

    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(15))
            .build();

    public ChatResponseDTO chat(ChatRequestDTO request) {
        try {
            String systemInstruction = buildSystemInstruction(request);

            String requestBody = buildGeminiRequestBody(systemInstruction, request.getMessages());

            String fullUrl = apiUrl + "?key=" + apiKey;
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(fullUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .timeout(Duration.ofSeconds(30))
                    .build();

            HttpResponse<String> response = httpClient.send(
                    httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String replyText = parseGeminiResponse(response.body());
                return ChatResponseDTO.ok(replyText);
            } else {
                System.err.println("[GeminiService] API Error " + response.statusCode() + ": " + response.body());
                return ChatResponseDTO.error("Không thể kết nối AI lúc này. Vui lòng thử lại sau.");
            }

        } catch (Exception e) {
            System.err.println("[GeminiService] Exception: " + e.getMessage());
            return ChatResponseDTO.error("Đã xảy ra lỗi kỹ thuật. Vui lòng thử lại.");
        }
    }

    private String buildSystemInstruction(ChatRequestDTO request) {
        StringBuilder sb = new StringBuilder();

        sb.append("Bạn là trợ lý mua sắm AI thông minh của cửa hàng **").append(storeName).append("**.\n\n");

        sb.append("## Vai trò của bạn:\n");
        sb.append("- Tư vấn sản phẩm phù hợp dựa trên nhu cầu khách hàng\n");
        sb.append("- Hỗ trợ kiểm tra giỏ hàng và đặt hàng\n");
        sb.append("- Giải đáp thắc mắc về chính sách đổi trả, vận chuyển, thanh toán\n");
        sb.append("- Hướng dẫn sử dụng website\n\n");

        sb.append("## Quy tắc trả lời:\n");
        sb.append("- Luôn trả lời bằng **tiếng Việt**, thân thiện và chuyên nghiệp\n");
        sb.append("- Câu trả lời ngắn gọn, rõ ràng (tối đa 3-4 câu trừ khi cần giải thích dài)\n");
        sb.append("- Chỉ tư vấn sản phẩm có trong danh sách — KHÔNG bịa đặt sản phẩm không tồn tại\n");
        sb.append("- Nếu không biết, hãy nói 'Tôi sẽ chuyển câu hỏi này đến nhân viên hỗ trợ'\n");
        sb.append("- Khi thích hợp, hãy gợi ý khách hàng thêm sản phẩm vào giỏ hàng\n\n");

        sb.append("## Thông tin cửa hàng:\n");
        sb.append("- Tên: ").append(storeName).append("\n");
        if (!storePhone.isBlank()) sb.append("- Hotline: ").append(storePhone).append("\n");
        if (!storeEmail.isBlank()) sb.append("- Email: ").append(storeEmail).append("\n");
        if (!storeAddress.isBlank()) sb.append("- Địa chỉ: ").append(storeAddress).append("\n");
        sb.append("- Chính sách: Miễn phí vận chuyển đơn trên 500.000đ | Đổi trả trong 7 ngày\n\n");

        // Inject danh sách sản phẩm từ DB
        String productList = chatContextService.getProductListText();
        if (productList != null && !productList.isBlank()) {
            sb.append("## Danh sách sản phẩm hiện có:\n").append(productList).append("\n");
        }

        // Inject context trang hiện tại
        if ("product".equals(request.getPageContext()) && request.getContextId() != null) {
            String productDetail = chatContextService.getProductDetailText(request.getContextId());
            if (productDetail != null && !productDetail.isBlank()) {
                sb.append("## Khách đang xem sản phẩm:\n").append(productDetail).append("\n");
                sb.append("Hãy chủ động tư vấn về sản phẩm này nếu phù hợp.\n");
            }
        } else if ("cart".equals(request.getPageContext())) {
            sb.append("## Lưu ý:\n");
            sb.append("Khách đang ở trang giỏ hàng. Hãy hỗ trợ hoàn tất đơn hàng.\n");
        }

        return sb.toString();
    }

 
    private String buildGeminiRequestBody(String systemInstruction, List<ChatMessageDTO> messages) throws Exception {
        ObjectNode root = mapper.createObjectNode();

        // System instruction
        ObjectNode sysNode = mapper.createObjectNode();
        ArrayNode sysParts = mapper.createArrayNode();
        ObjectNode sysText = mapper.createObjectNode();
        sysText.put("text", systemInstruction);
        sysParts.add(sysText);
        sysNode.set("parts", sysParts);
        root.set("system_instruction", sysNode);

        // Contents (conversation history)
        ArrayNode contents = mapper.createArrayNode();
        for (ChatMessageDTO msg : messages) {
            ObjectNode turn = mapper.createObjectNode();
            // Gemini dùng "model" thay vì "assistant"
            turn.put("role", "assistant".equals(msg.getRole()) ? "model" : msg.getRole());
            ArrayNode parts = mapper.createArrayNode();
            ObjectNode part = mapper.createObjectNode();
            part.put("text", msg.getContent());
            parts.add(part);
            turn.set("parts", parts);
            contents.add(turn);
        }
        root.set("contents", contents);

        // Generation config
        ObjectNode genConfig = mapper.createObjectNode();
        genConfig.put("maxOutputTokens", maxTokens);
        genConfig.put("temperature", 0.7);
        genConfig.put("topP", 0.9);
        root.set("generationConfig", genConfig);

        // Safety settings (giảm độ nghiêm ngặt cho chat thương mại)
        ArrayNode safetySettings = mapper.createArrayNode();
        String[] categories = {
            "HARM_CATEGORY_HARASSMENT",
            "HARM_CATEGORY_HATE_SPEECH",
            "HARM_CATEGORY_SEXUALLY_EXPLICIT",
            "HARM_CATEGORY_DANGEROUS_CONTENT"
        };
        for (String cat : categories) {
            ObjectNode setting = mapper.createObjectNode();
            setting.put("category", cat);
            setting.put("threshold", "BLOCK_ONLY_HIGH");
            safetySettings.add(setting);
        }
        root.set("safetySettings", safetySettings);

        return mapper.writeValueAsString(root);
    }

    /**
     * Parse phản hồi JSON từ Gemini API và lấy text.
     */
    private String parseGeminiResponse(String responseBody) throws Exception {
        JsonNode root = mapper.readTree(responseBody);

        // Kiểm tra nếu bị block bởi safety filter
        JsonNode promptFeedback = root.path("promptFeedback");
        if (!promptFeedback.isMissingNode()) {
            String blockReason = promptFeedback.path("blockReason").asText("");
            if (!blockReason.isBlank() && !"BLOCK_REASON_UNSPECIFIED".equals(blockReason)) {
                return "Xin lỗi, tôi không thể trả lời câu hỏi này. Vui lòng thử câu hỏi khác.";
            }
        }

        // Lấy text từ candidates[0].content.parts[0].text
        JsonNode candidates = root.path("candidates");
        if (candidates.isArray() && candidates.size() > 0) {
            JsonNode firstCandidate = candidates.get(0);

            // Kiểm tra finish reason
            String finishReason = firstCandidate.path("finishReason").asText("");
            if ("SAFETY".equals(finishReason)) {
                return "Tôi không thể trả lời câu hỏi này. Bạn có câu hỏi nào khác về sản phẩm không?";
            }

            JsonNode parts = firstCandidate.path("content").path("parts");
            if (parts.isArray() && parts.size() > 0) {
                return parts.get(0).path("text").asText("Xin lỗi, tôi không hiểu. Bạn có thể hỏi lại không?");
            }
        }

        return "Xin lỗi, đã có lỗi xảy ra. Vui lòng thử lại.";
    }
}
