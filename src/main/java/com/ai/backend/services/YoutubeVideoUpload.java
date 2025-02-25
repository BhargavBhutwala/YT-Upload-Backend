package com.ai.backend.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

@Service
public class YoutubeVideoUpload {

   public static final String UPLOAD_URL = "https://www.googleapis.com/upload/youtube/v3/videos?uploadType=resumable&part=snippet,status";

   public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

   public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

   public String uploadVideo(String title, String description, String visibility, MultipartFile videoFile,
         String accessToken)
         throws IOException {

      HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory();

      String metaData = "{\n" +
            "  \"snippet\": {\n" + //
            "    \"title\": \"" + title + "\",\n" + //
            "    \"description\": \"" + description + "\",\n" + //
            "    \"tags\": [\"cool\", \"video\", \"technology\"],\n" + //
            "    \"categoryId\": 22\n" + //
            "  },\n" + //
            "  \"status\": {\n" + //
            "    \"privacyStatus\": \"" + visibility + "\",\n" + //
            "    \"embeddable\": true,\n" + //
            "    \"license\": \"youtube\"\n" + //
            "  }\n" + //
            "}";

      HttpRequest request = requestFactory.buildPostRequest(
            new GenericUrl(UPLOAD_URL),
            ByteArrayContent.fromString("application/json", metaData));

      request.getHeaders().setAuthorization("Bearer " + accessToken);
      request.getHeaders().setContentType("application/json");

      HttpResponse response = request.execute();

      String videoUploadURL = response.getHeaders().getLocation();

      HttpRequest request2 = requestFactory.buildPutRequest(
            new GenericUrl(videoUploadURL),
            new InputStreamContent("video/*", videoFile.getInputStream()));

      HttpResponse httpResponse = request2.execute();

      return "Video Uploaded Successfully!";

   }
}
