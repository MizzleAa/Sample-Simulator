package com.mizzle.simulator.payload.request;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class LoadPayload {
    private int page;
    private int size;
}
