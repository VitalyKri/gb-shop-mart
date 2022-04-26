package ru.gb.gbexternalapi.service.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.gbexternalapi.dto.ManufacturerDto;

import java.util.List;

public interface ManufacturerApi {

    @GetMapping(produces = "application/json;charset=UTF-8")
    List<ManufacturerDto> getManufacturerList();
//
//    @GetMapping(value = "/{manufacturerId}",produces = "application/json;charset=UTF-8")
//    ResponseEntity<?> getManufacturer(@PathVariable("productId") Long id);
//
//    @PostMapping(produces = "application/json;charset=UTF-8")
//    ResponseEntity<?> handlePost(ManufacturerDto manufacturerDto);
//
//    @PutMapping(value = "/{manufacturerId}",produces = "application/json;charset=UTF-8")
//    ResponseEntity<?> handleUpdate(@PathVariable("productId") Long id, ManufacturerDto manufacturerDto);
//
//    @DeleteMapping(value = "/{productId}",produces = "application/json;charset=UTF-8")
//    void deleteById(@PathVariable("productId")  Long id);
}