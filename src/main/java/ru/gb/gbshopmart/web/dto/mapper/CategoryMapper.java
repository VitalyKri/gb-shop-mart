package ru.gb.gbshopmart.web.dto.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gb.gbapi.category.dto.CategoryDto;
import ru.gb.gbapi.product.dto.ProductDto;
import ru.gb.gbshopmart.dao.ProductDao;
import ru.gb.gbshopmart.entity.Category;
import ru.gb.gbshopmart.entity.Product;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface CategoryMapper {

    Category toCategory(CategoryDto categoryDto, @Context ProductDao productDao);

    CategoryDto toCategoryDto(Category category);


    default Set<Product> getProducts(Set<ProductDto> products, @Context ProductDao productDao) {

        if (products == null){
            return null;
        }
        HashMap<Long, ProductDto> mapProduct = new HashMap<>();

        products.stream().forEach(categoryDto -> {
            mapProduct.put(categoryDto.getId(), categoryDto);
        });
        return productDao.findAll().stream()
                .filter(mapProduct::containsKey)
                .collect(Collectors.toSet());
    }

    default Set<ProductDto> getProducts(Set<Product> products) {

        if (products == null){
            return null;
        }
        return products.stream()
                .map(product -> ProductDto.builder()
                        .id(product.getId())
                        .title(product.getTitle())
                        .build())
                .collect(Collectors.toSet());
    }

}
