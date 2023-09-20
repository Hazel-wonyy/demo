package com.timely.demo.product.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ProductVO {
    //gpt studio

    //product
    private Long pid;
    private String value;
    private String label;

    //product item
    private Long itemId;
    private int type;
    private String title;

    //placeholder
    private Long phId;
    private String placeholder;

    //span
    private Long spanId;
    private int state;

}
