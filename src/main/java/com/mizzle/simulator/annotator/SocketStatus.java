package com.mizzle.simulator.annotator;

import java.lang.annotation.*;

@Inherited
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SocketStatus {
    String path();
    String name();
    String sendTime();
    String status();
}