package com.example.testgritsee.data;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/data")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class DataController {
    private final DataService dataService;

    //Obtener todos los datos
    @GetMapping()
    public ResponseEntity<Page<DataResponse>> getAllDatos(
            //Paginacion
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fecha") String sortField,
            @RequestParam(defaultValue = "desc") String direction
    ){
        Page<DataResponse> respuesta = dataService.getAll(page, size, sortField, direction);

        return ResponseEntity.ok(respuesta);
    }

    //Obtener datos filtrados
    @GetMapping("/buscar")
    public ResponseEntity<Page<DataResponse>> obtenerDatos(
            //Paginacion
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fecha") String sortField,
            @RequestParam(defaultValue = "desc") String direction,

            //Filtrado
            @RequestParam(required = false) String sucursal,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicioFecha,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finFecha,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaExacta
    ){
        Page<DataResponse> respuesta = dataService.getFilteredData(
                page, size, sortField, direction, sucursal, inicioFecha, finFecha, fechaExacta);

        return ResponseEntity.ok(respuesta);
    }
}
