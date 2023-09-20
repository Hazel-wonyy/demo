package com.timely.demo.product.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ToolVO {
    //gpt studio

    //user result
    private Long toolId;
    private Long resultId;
    private Long userId;
    private String body;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime cts;
    private int state;

    //mytool
    private String title;
    private String placeholer;
    private String span1;
    private String span2;
    private String span3;
    private String span4;
    private String span5;

}