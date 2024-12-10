package com.backend.novelservice.service.httpclient;
import lombok.Data;

@Data
public class ImgurResponse {
    private Data data;

    @lombok.Data
    public static class Data {
        private String id;
        private String link;
        private String type;
    }
}