package com.mizzle.simulator.payload.request;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ReceiveSocketPayload {

    private Image image;
    private Information information;

    @Data
    public static class Image{
        private String path;
        private String name;
    }

    @Data
    public static class Information{
        private Object key;
    }
    
    public ReceiveSocketPayload(){}

    @Builder
    public ReceiveSocketPayload(Image image, Information information){
        this.image = image;
        this.information = information;
    }

}
