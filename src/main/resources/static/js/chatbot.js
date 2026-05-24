(function () {
    'use strict';

    // ── State ──────────────────────────────────────────────────────────────
    let conversationHistory = []; // Lưu lịch sử hội thoại trong phiên
    let isLoading = false;
    let isOpen = false;
    let messageCount = 0;

    // ── DOM References ─────────────────────────────────────────────────────
    let fab, chatWindow, messagesContainer, inputEl, sendBtn, quickRepliesEl;

    // ── Init ───────────────────────────────────────────────────────────────
    document.addEventListener('DOMContentLoaded', function () {
        fab            = document.getElementById('chat-fab');
        chatWindow     = document.getElementById('chat-window');
        messagesContainer = document.getElementById('chat-messages');
        inputEl        = document.getElementById('chat-input');
        sendBtn        = document.getElementById('send-btn');
        quickRepliesEl = document.getElementById('quick-replies');

        if (!fab || !chatWindow) return; // Widget chưa được nhúng vào trang

        // Event listeners
        fab.addEventListener('click', toggleChat);
        document.getElementById('chat-close-btn').addEventListener('click', closeChat);
        sendBtn.addEventListener('click', sendMessage);
        inputEl.addEventListener('keydown', handleKeyDown);
        inputEl.addEventListener('input', autoResize);

        // Quick reply buttons
        document.querySelectorAll('.quick-btn').forEach(function (btn) {
            btn.addEventListener('click', function () {
                sendQuickReply(this.dataset.text || this.textContent.trim());
            });
        });

        // Đóng chat khi click bên ngoài
        document.addEventListener('click', function (e) {
            if (isOpen && !chatWindow.contains(e.target) && !fab.contains(e.target)) {
                closeChat();
            }
        });
    });

    // ── Toggle / Open / Close ──────────────────────────────────────────────
    function toggleChat() {
        isOpen ? closeChat() : openChat();
    }

    function openChat() {
        isOpen = true;
        chatWindow.style.display = 'flex';
        fab.innerHTML = getCloseIcon();
        hideBadge();
        setTimeout(function () { inputEl.focus(); }, 300);
        scrollToBottom();
    }

    function closeChat() {
        isOpen = false;
        chatWindow.style.display = 'none';
        fab.innerHTML = getChatIcon();
    }

    // ── Send Message ───────────────────────────────────────────────────────
    function handleKeyDown(e) {
        if (e.key === 'Enter' && !e.shiftKey) {
            e.preventDefault();
            sendMessage();
        }
    }

    function autoResize() {
        inputEl.style.height = 'auto';
        inputEl.style.height = Math.min(inputEl.scrollHeight, 110) + 'px';
    }

    function sendQuickReply(text) {
        inputEl.value = text;
        sendMessage();
    }

    async function sendMessage() {
        if (isLoading) return;

        const text = inputEl.value.trim();
        if (!text) return;

        // Thêm vào history và hiển thị
        appendUserMessage(text);
        conversationHistory.push({ role: 'user', content: text });
        inputEl.value = '';
        inputEl.style.height = 'auto';
        hideQuickReplies();

        // Loading
        isLoading = true;
        sendBtn.disabled = true;
        const typingEl = showTypingIndicator();

        try {
            // Lấy context trang hiện tại
            const contextData = getPageContext();

            const response = await fetch('/api/chat', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    messages: conversationHistory,
                    pageContext: contextData.context,
                    contextId: contextData.id
                })
            });

            if (!response.ok) {
                throw new Error('HTTP ' + response.status);
            }

            const data = await response.json();
            typingEl.remove();

            if (data.success && data.reply) {
                appendBotMessage(data.reply);
                conversationHistory.push({ role: 'model', content: data.reply });
            } else {
                appendBotMessage(data.error || 'Xin lỗi, đã có lỗi xảy ra. Vui lòng thử lại.');
            }

        } catch (err) {
            typingEl.remove();
            console.error('[Chatbot] Error:', err);
            appendBotMessage('Không thể kết nối với trợ lý AI. Vui lòng kiểm tra kết nối mạng và thử lại.');
        } finally {
            isLoading = false;
            sendBtn.disabled = false;
            inputEl.focus();
        }
    }

    // ── DOM Helpers ────────────────────────────────────────────────────────
    function appendUserMessage(text) {
        const wrapper = document.createElement('div');
        wrapper.innerHTML =
            '<div class="msg-user">' + escapeHtml(text) + '</div>' +
            '<div class="msg-time">' + getTime() + '</div>';
        wrapper.style.cssText = 'display:flex;flex-direction:column;align-items:flex-end';
        messagesContainer.appendChild(wrapper);
        scrollToBottom();
        messageCount++;
    }

    function appendBotMessage(text) {
        // Render markdown đơn giản (bold, list)
        const formattedText = simpleMarkdown(text);

        const wrapper = document.createElement('div');
        wrapper.className = 'msg-bot-wrapper';
        wrapper.innerHTML =
            '<div class="msg-bot-avatar">✨</div>' +
            '<div style="display:flex;flex-direction:column;">' +
                '<div class="msg-bot">' + formattedText + '</div>' +
                '<div class="msg-time">' + getTime() + ' · Gemini</div>' +
            '</div>';
        messagesContainer.appendChild(wrapper);
        scrollToBottom();

        // Nếu cửa sổ đang đóng, hiện badge
        if (!isOpen) showBadge();
    }

    function showTypingIndicator() {
        const el = document.createElement('div');
        el.className = 'msg-bot-wrapper';
        el.id = 'typing-indicator';
        el.innerHTML =
            '<div class="msg-bot-avatar">✨</div>' +
            '<div class="msg-typing">' +
                '<span class="typing-dot"></span>' +
                '<span class="typing-dot"></span>' +
                '<span class="typing-dot"></span>' +
            '</div>';
        messagesContainer.appendChild(el);
        scrollToBottom();
        return el;
    }

    function hideQuickReplies() {
        if (quickRepliesEl) quickRepliesEl.style.display = 'none';
    }

    function scrollToBottom() {
        setTimeout(function () {
            messagesContainer.scrollTop = messagesContainer.scrollHeight;
        }, 50);
    }

    function showBadge() {
        let badge = fab.querySelector('.badge');
        if (!badge) {
            badge = document.createElement('span');
            badge.className = 'badge';
            fab.appendChild(badge);
        }
        badge.textContent = '1';
    }

    function hideBadge() {
        const badge = fab.querySelector('.badge');
        if (badge) badge.remove();
    }

    // ── Utilities ──────────────────────────────────────────────────────────

    /**
     * Đọc context trang từ data attribute (được set bởi Thymeleaf).
     * Thêm vào trang sản phẩm:
     *   <div id="chat-page-ctx" data-context="product" data-id="42" style="display:none"></div>
     */
    function getPageContext() {
        const el = document.getElementById('chat-page-ctx');
        if (el) {
            return {
                context: el.dataset.context || 'home',
                id: el.dataset.id ? parseInt(el.dataset.id) : null
            };
        }
        return { context: 'home', id: null };
    }

    function getTime() {
        return new Date().toLocaleTimeString('vi-VN', { hour: '2-digit', minute: '2-digit' });
    }

    function escapeHtml(text) {
        const div = document.createElement('div');
        div.appendChild(document.createTextNode(text));
        return div.innerHTML;
    }

    /**
     * Chuyển đổi markdown đơn giản sang HTML.
     * Hỗ trợ: **bold**, *italic*, - list items, xuống dòng
     */
    function simpleMarkdown(text) {
        return escapeHtml(text)
            .replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>')
            .replace(/\*(.*?)\*/g, '<em>$1</em>')
            .replace(/^[-•]\s(.+)$/gm, '<li>$1</li>')
            .replace(/(<li>.*<\/li>)/s, '<ul style="margin:6px 0 6px 16px;padding:0">$1</ul>')
            .replace(/\n/g, '<br>');
    }

    // ── Icons SVG ──────────────────────────────────────────────────────────
    function getChatIcon() {
        return '<svg width="26" height="26" viewBox="0 0 24 24" fill="none" stroke="#fff" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">' +
               '<path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"/>' +
               '</svg>';
    }

    function getCloseIcon() {
        return '<svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="#fff" stroke-width="2.5" stroke-linecap="round">' +
               '<line x1="18" y1="6" x2="6" y2="18"/><line x1="6" y1="6" x2="18" y2="18"/>' +
               '</svg>';
    }

    // Khởi động icon ban đầu
    document.addEventListener('DOMContentLoaded', function () {
        const f = document.getElementById('chat-fab');
        if (f) f.innerHTML = getChatIcon();
    });

})(); // IIFE - tránh ô nhiễm global scope
