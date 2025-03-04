package com.ai.backend.controllers;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ai.backend.services.YoutubeVideoUpload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping("/api/youtube/video")
@CrossOrigin("*")
public class VideoUploadController {

   @Autowired
   private YoutubeVideoUpload videoUploadService;

   @PostMapping("/upload")
   public ResponseEntity<String> uploadVideo(@RequestParam("title") String title,
         @RequestParam("description") String description, @RequestParam("visibility") String visibility,
         @RequestParam("videoFile") MultipartFile videoFile, @RequestHeader("Authorization") String accessToken)
         throws IOException {

      String response = videoUploadService.uploadVideo(title, description, visibility, videoFile,
            accessToken.replace("Bearer ", ""));

      return ResponseEntity.ok(response);
   }

   // generate video metadata
   @PostMapping("/generate-metadata")
   public ResponseEntity<Map<String, Object>> generateVideoMetadata(@RequestParam("title") String title)
         throws JsonMappingException, JsonProcessingException {

      Map<String, Object> result = videoUploadService.generateVideoMetadata(title);

      return ResponseEntity.ok(result);
   }

}
