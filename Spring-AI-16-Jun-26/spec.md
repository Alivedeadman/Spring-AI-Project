# Current Feature Specification

**Status**: Configuration Complete (Dual LLMs)  
**Goal**: Configure Google GenAI and Groq LLM Providers

## Implementation Steps
- [x] Step 1: Define the primary objective of this Spring AI application with the user.
- [x] Step 2: Update this specification with exact technical requirements and endpoints.
- [x] Step 3: Implement the necessary Spring AI configurations (Google and Groq via `application.yaml`).
- [ ] Step 4: Create Services integrating `ChatClient` (or other Spring AI features).
- [x] Step 5: Create REST Controllers to expose the functionality (`AiTestController`).

## Notes
- We have configured `spring.ai.google.genai` and `spring.ai.openai` (pointing to Groq).
- A TestController is available at `/api/test/google` and `/api/test/groq`.
