package ru.gb.gbshopmart.web.dto.mapper;


import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import ru.gb.gbapi.product.dto.ProductImageDto;

import ru.gb.gbshopmart.entity.ProductImage;


@Mapper
public interface ProductImageMapper {

    @Mapping(source = "productId", target = "product.id")
    ProductImage toProductImage(ProductImageDto productImageDto);

    @Mapping(source = "product.id", target = "productId")
    ProductImageDto toProductImageDto(ProductImage productImage);


}
