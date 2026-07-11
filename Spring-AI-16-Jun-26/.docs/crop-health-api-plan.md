# Crop Health API Implementation Plan

This document outlines the approach to implement the Crop Health API that analyzes images and descriptions using
multiple AI providers concurrently to diagnose crop diseases.

## User Review Required

> [!NOTE]
> Based on your feedback, we will inject the specific `ChatClient` beans from `LLMClientConfigurationConfig02` (
`googleChatClientWithDefaultSystemAndUserMessage` and `groqChatClientWithDefaultSystemAndUserMessage`).

> [!TIP]
> **Alternative for Concurrency:** Instead of direct virtual threads via `StructuredTaskScope`, we will use Java's
`CompletableFuture` along with Spring Boot's standard `ThreadPoolTaskExecutor`. In Spring Boot 3.2+, you can simply
> enable virtual threads globally using `spring.threads.virtual.enabled=true` in your properties, and the standard
> executor will automatically use virtual threads under the hood!

## Proposed Changes

### Configuration Properties

We will rely on the property `app.crop-health.max-attachments` (defaulting to 2) to control the number of allowed image
attachments.

### DTOs

New data transfer objects to represent the unified response using Java Records for immutability and conciseness. (
Standard camelCase will be used for JSON properties).

#### [NEW] AiProviderResult.java

- Java record representing a single provider's response.
- Fields: `String providerName`, `String identifiedDisease`, `int confidenceLevel`, `String recommendations`.

#### [NEW] CropHealthResponse.java

- Java record representing the combined response.
- Fields: `List<AiProviderResult> results`.

---

### Service Layer

The business logic that handles the multi-modal AI requests.

#### [NEW] CropHealthService.java

- Interface defining the analysis method.

#### [NEW] CropHealthServiceImpl.java

- Implementation that uses the injected `googleChatClientWithDefaultSystemAndUserMessage` and
  `groqChatClientWithDefaultSystemAndUserMessage`.
- Utilizes `CompletableFuture.supplyAsync()` to execute the requests to both providers concurrently.
- Uses `BeanOutputConverter` to enforce the output format to match `AiProviderResult`. This automatically works even for
  providers that don't natively support JSON schema, as Spring AI appends formatting instructions to the prompt!
- Maps the uploaded images to Spring AI `Media` objects.

---

### Controller Layer

REST endpoint for processing the upload.

#### [NEW] CropHealthController.java

- Exposes `POST /api/v1/crop-health/analyze`.
- Uses `@RequestPart("description") String description` and `@RequestPart("images") List<MultipartFile> images`.
- Reads `@Value("${app.crop-health.max-attachments:2}") int maxAttachments` and throws an `IllegalArgumentException`
  with an appropriate message if the uploaded list size exceeds this limit.
- Validates that the list is not empty and content types are valid images.

---

### Exception Handling

Centralized error handling.

#### [MODIFY] GlobalExceptionHandler.java

- Add handlers for `MaxUploadSizeExceededException`, `MissingServletRequestPartException`.
- The existing `IllegalArgumentException` handler will effectively catch the attachment limit validation and return a
  clean 400 Bad Request error.

## Verification Plan

### Manual Verification

- Test the endpoint using Postman or cURL.
- Pass a single image.
- Pass 3 images (to verify the configurable attachment limit throws an error).
- Observe the combined JSON result from Groq and Google GenAI.
