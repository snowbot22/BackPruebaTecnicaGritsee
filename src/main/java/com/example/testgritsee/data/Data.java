/*
package com.example.testgritsee.data;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "datos")
@ToString(onlyExplicitlyIncluded = true)
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate fecha;

    @Column(name = "url", nullable = false)
    private String url;

    //Extraccion sucursal
    public String getSucursal(){
        if (this.url != null && this.url.contains("/")) {
            String nodo = this.url.split("/")[3];

            if(nodo.contains("-")){
                return nodo.split("-", 2)[1];
            }
        }
        return "sin sucursal";
    }
}
*/