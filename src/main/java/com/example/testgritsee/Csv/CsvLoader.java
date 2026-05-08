package com.example.testgritsee.Csv;

import com.example.testgritsee.data.DataResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvLoader {
    private final List<DataResponse> dataBaseEnMemoria = new ArrayList<>();


    @PostConstruct
    public void cargarCsv()  {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/prueba.csv")
        ))){
            String line;
            boolean esPrimeraLinea = true;

            while ((line = br.readLine()) != null){
                if (esPrimeraLinea) { esPrimeraLinea = false; continue; }

                String[] columnas = line.split(",");
                if(columnas.length >= 2){
                    String date = columnas[0];
                    String url = columnas[1];

                    //Logica de extraccion de sucursal de url
                    String sucursal = "desconocida";
                    if(url.contains("/")){
                        String nodo = url.split(("/"))[3];
                        sucursal= nodo.contains("-") ? nodo.split("-", 2)[1] : nodo;
                    }

                    dataBaseEnMemoria.add(DataResponse.builder()
                            .fecha(LocalDate.parse(date))
                            .url(url)
                            .sucursal(sucursal)
                            .build());
                }
            }
            System.out.println("CSV cargado con exito");
        }
        catch (Exception e){
            System.err.println("Error al cargar CSV: " + e);
        }
    }

    public List<DataResponse> getDataBaseEnMemoria(){
        return dataBaseEnMemoria;
    }
}
