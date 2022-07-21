package com.sample.crawler.controller.model;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UserRequest {

    @NotNull
    private String id;
    @Min(3)
    @Max(10)
    private Integer age;
}
