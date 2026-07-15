package com.veeral.springai.springai16jun26.model.cropdiseaseidentifier;

public record AiProviderResult(
        String providerName,
        String identifiedDisease,
        int confidenceLevel,
        String recommendations
) {
}
