package com.veeral.springai.springai16jun26.model.cropdiseaseidentifier;

import java.util.List;

public record CropHealthResponse(
        List<AiProviderResult> results
) {
}
