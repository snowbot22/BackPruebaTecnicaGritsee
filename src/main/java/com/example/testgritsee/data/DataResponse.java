package com.example.testgritsee.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataResponse {
    private LocalDate fecha;
    private String url;
    private String sucursal;
}
