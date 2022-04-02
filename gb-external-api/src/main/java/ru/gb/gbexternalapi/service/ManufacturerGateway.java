package ru.gb.gbexternalapi.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.gbexternalapi.dto.ManufacturerDto;
import ru.gb.gbexternalapi.service.api.ManufacturerApi;

import java.util.List;

@FeignClient(url = "localhost:8080/api/v1/product",value = "ManufacturerGateway")
public interface ManufacturerGateway extends ManufacturerApi {



}
