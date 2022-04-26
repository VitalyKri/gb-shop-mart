package ru.gb.gbexternalapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.gbexternalapi.dto.ManufacturerDto;
import ru.gb.gbexternalapi.dto.ProductDto;
import ru.gb.gbexternalapi.service.ManufacturerGateway;
import ru.gb.gbexternalapi.service.ProductGateway;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shop")
public class ShopController {

    private final ProductGateway productGateway;

    private final ManufacturerGateway manufacturerGateway;

    @GetMapping("/products")
    public List<ProductDto> getAllProduct(){
        return productGateway.getProductList();
    }

    @GetMapping("/manufacturers")
    public List<ManufacturerDto> getAllManufacturs(){
        return manufacturerGateway.getManufacturerList();
    }
}
