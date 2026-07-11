package com.veeral.springai.springai16jun26.service.cropdiseaseidentifier;

import com.veeral.springai.springai16jun26.dto.cropdiseaseidentifier.CropHealthResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CropHealthService {

    /**
     * Analyzes crop images and description using multiple AI models.
     *
     * @param description A text description of the crop and symptoms.
     * @param images      A list of images of the crop.
     * @return A combined response containing analysis from different AI providers.
     */
    CropHealthResponse analyzeCropHealth(String description, List<MultipartFile> images);
}
