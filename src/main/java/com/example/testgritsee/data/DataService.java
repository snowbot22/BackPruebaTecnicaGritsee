package com.example.testgritsee.data;

import com.example.testgritsee.Csv.CsvLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataService {

    private final CsvLoader csvLoader;

    //Obtener todos los datos
    public Page<DataResponse> getAll(int page, int size, String sortField, String direction) {

        // 1. Obtener la lista completa desde nuestro DataLoader
        List<DataResponse> todosLosDatos = csvLoader.getDataBaseEnMemoria();

        // 2. Crear una copia de la lista para ordenarla (¡Súper importante!)
        // Hacemos esto para no alterar el orden original del CSV en la memoria global
        List<DataResponse> datosParaOrdenar = new ArrayList<>(todosLosDatos);

        // 3. Aplicar el ordenamiento (Usando el método auxiliar que creamos antes)
        Comparator<DataResponse> comparator = getComparator(sortField);
        if ("desc".equalsIgnoreCase(direction)) {
            comparator = comparator.reversed();
        }
        datosParaOrdenar.sort(comparator);

        // 4. Calcular los índices para la Paginación Manual
        int start = Math.min((int) PageRequest.of(page, size).getOffset(), datosParaOrdenar.size());
        int end = Math.min((start + size), datosParaOrdenar.size());

        // 5. Extraer solo los registros de la página actual
        List<DataResponse> paginaDeDatos = datosParaOrdenar.subList(start, end);

        // 6. Empaquetar todo de vuelta en un objeto Page para que el Controlador no note la diferencia
        return new PageImpl<>(paginaDeDatos, PageRequest.of(page, size), datosParaOrdenar.size());
    }

    //Obtener por filtrado
    @Transactional(readOnly = true)
    public Page<DataResponse> getFilteredData (int page, int size, String sortField, String direction,
                                               String sucursal, LocalDate inicioFecha, LocalDate finFecha, LocalDate fechaExacta){

        //Obtener datos de CSV
        List<DataResponse> datos = csvLoader.getDataBaseEnMemoria();

        //Filtros
        List<DataResponse> datosFiltrados = datos.stream()
                .filter(d -> sucursal == null || d.getSucursal().toLowerCase().contains(sucursal.toLowerCase()))
                .filter(d-> inicioFecha == null || !d.getFecha().isBefore(inicioFecha))
                .filter(d -> finFecha == null || !d.getFecha().isAfter(finFecha))
                .filter(d -> fechaExacta == null || d.getFecha().isEqual(fechaExacta))
                .toList();

        //Crear lista modificable a partir de filtro
        List<DataResponse> datosParaOrdenar = new ArrayList<>(datosFiltrados);

        //Ordenar
        Comparator<DataResponse> comparator = getComparator(sortField);
        if("desc".equalsIgnoreCase(direction)){
            comparator = comparator.reversed();
        }
        datosParaOrdenar.sort(comparator);

        //Paginacion
        int inicioPag = Math.min((int) PageRequest.of(page, size).getOffset(), datosFiltrados.size());
        int finalPag = Math.min((inicioPag + size), datosParaOrdenar.size());
        List<DataResponse> paginaDatos = datosParaOrdenar.subList(inicioPag, finalPag);

        //Devolver objeto page
        return new PageImpl<>(paginaDatos, PageRequest.of(page,size), datosParaOrdenar.size());
    }


    //Metodo auxiliar para ordenar
    private Comparator<DataResponse> getComparator(String sortField){
        if("sucursal".equalsIgnoreCase(sortField)){
            return Comparator.comparing(DataResponse::getSucursal);
        } else if ("url".equalsIgnoreCase(sortField)) {
            return Comparator.comparing(DataResponse::getUrl);
        }
        return Comparator.comparing(DataResponse::getFecha);
    }
}
