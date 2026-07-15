package com.veeral.springai.springai16jun26.controller.cropdiseaseidentifier;

import com.veeral.springai.springai16jun26.model.cropdiseaseidentifier.CropHealthResponse;
import com.veeral.springai.springai16jun26.service.cropdiseaseidentifier.CropHealthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/crop-health")
public class CropHealthController {

    private final CropHealthService cropHealthService;

    @Value("${app.crop-health.max-attachments:2}")
    private int maxAttachments;

    public CropHealthController(CropHealthService cropHealthService) {
        this.cropHealthService = cropHealthService;
    }

    @PostMapping(value = "/analyze", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CropHealthResponse> analyzeCropHealth(
            @RequestPart(value = "description", required = false) String description,
            @RequestPart("images") List<MultipartFile> images) {

        if (images == null || images.isEmpty()) {
            throw new IllegalArgumentException("At least one image must be provided.");
        }

        if (images.size() > maxAttachments) {
            throw new IllegalArgumentException("Maximum number of allowed attachments is " + maxAttachments + ".");
        }

        // Additional validation to ensure all files are valid images can be added here
        for (MultipartFile image : images) {
            if (image.isEmpty()) {
                throw new IllegalArgumentException("Uploaded image file is empty.");
            }
            if (image.getContentType() == null || !image.getContentType().startsWith("image/")) {
                throw new IllegalArgumentException("Uploaded file must be an image. Invalid file type: " + image.getContentType());
            }
        }

        CropHealthResponse response = cropHealthService.analyzeCropHealth(description, images);
        return ResponseEntity.ok(response);
    }
}
