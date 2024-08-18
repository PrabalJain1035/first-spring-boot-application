package com.example.my_spring_boot_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto<T> {

    private String statusCode;
    private String statusDesc;
    private T data;
}
