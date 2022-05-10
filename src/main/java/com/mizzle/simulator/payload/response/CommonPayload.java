package com.mizzle.simulator.payload.response;

import lombok.Builder;
import lombok.Data;

@Data
public class CommonPayload {
    private boolean check;
    private Object information;

    public CommonPayload(){}

    @Builder
    public CommonPayload(boolean check, Object information){
        this.check = check;
        this.information = information;
    }

}
