package ru.gb.gbexternalapi.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.gbexternalapi.dto.ProductDto;
import ru.gb.gbexternalapi.service.api.ProductApi;

import java.util.List;

@FeignClient(url = "localhost:8080/api/v1/product",value = "productGateway")
public interface ProductGateway extends ProductApi {

}
