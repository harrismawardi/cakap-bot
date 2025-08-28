# 📚 CakapBot API

CakapBot is an **AI-assisted language learning chatbot**.  
Learners practise Malay (and later other languages) through **real-time chat sessions**, guided by lesson plans stored in MongoDB and powered by LLMs (e.g. Gemini, OpenAI).

This repo contains the **backend API** built with **Spring Boot** following a **Hexagonal (Ports & Adapters)** architecture.

---

## ✨ Features (V1)

- Start a new chat session for a specific **lesson** and **language**.
- Send messages in Malay and receive AI-generated replies with:
    - Malay message
    - English translation
    - 1–3 hints in English
- Session management (per lesson, per user session).
- Lesson content served from MongoDB.
- In-memory session store (Redis planned).
- Pluggable LLM clients (Gemini and OpenAI clients implemented).


![Diagram V1 Design.jpg]("assets/Diagram V1 Design.jpg")
---

## 🚀 Getting Started

### Prerequisites
- Java 21+
- Maven 3.9+
- API key for Gemini (or OpenAI if swapping clients)

### Run
```bash
./gradlew bootRun
```
App starts on: http://localhost:8080

## 🔗 API Endpoints

### Chat API
**1. Start a chat:**

```bash
GET /api/v1/chat/start?lessonId={slug}&lang={code}
```

**Response:**
```json
{
  "sessionId": "01J8P8YQ6K2T7Z2S9Y2X2F3K1V",
  "message": "Selamat pagi! Sedia berlatih?",
  "englishTranslation": "Good morning! Ready to practise?",
  "hints": ["Reply with 'Selamat pagi'"]
}
```

**2. Send a message**

```bash
POST /api/v1/chat/message
```

**Request:**
```json
{
  "sessionId": "01J8P8YQ6K2T7Z2S9Y2X2F3K1V",
  "message": "Khabar baik"
}
```

**Response:**

```json
{
  "sessionId": "01J8P8YQ6K2T7Z2S9Y2X2F3K1V",
  "message": "Bagus! Awak dari mana?",
  "englishTranslation": "Great! Where are you from?",
  "hints": ["Ask where they are from"]
}
```

---
## 🏗️ Architecture

This project follows **Hexagonal Architecture**:

```lua
        +------------------+
        |   Controllers    |  (REST API, DTOs)
        +------------------+
                 |
                 v
        +------------------+
        |  Application     |  (ChatService, AIReplyService)
        +------------------+
                 |
+-------------+-------------+
|             |             |
v             v             v
+-------+   +----------+   +-----------+
| Chat  |   | Lessons  |   | LLM       |
| Store |   | Store    |   | Client    |
+-------+   +----------+   +-----------+
(Memory)     (Mongo)       (Gemini)
```


- Domain: pure business types (ChatHistory, Lesson, BotTurn) + ports (ChatStore, LessonStore, LlmClient).
- Application: use cases (ChatService, AIReplyService).
- Adapters:
  - Inbound: REST controllers.
  - Outbound:
    - Memory/Redis for sessions.
    - Mongo for lessons.
    - Gemini/OpenAI REST API for LLM.

---
## 🛣️ Roadmap

- Redis session store
- Streaming chat via WebSocket
- Admin UI for lesson management
- Multi-language support (id, en)
- Persona system for different tutor voices
