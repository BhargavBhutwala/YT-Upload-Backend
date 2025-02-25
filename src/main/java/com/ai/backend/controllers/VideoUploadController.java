package com.ai.backend.controllers;

import java.io.IOException;

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

@RestController
@RequestMapping("/api/youtube")
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

}
