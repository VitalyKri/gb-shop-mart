package ru.gb.gbshopmart.web.dto.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gb.gbapi.category.dto.CategoryDto;
import ru.gb.gbapi.product.dto.ProductDto;
import ru.gb.gbshopmart.dao.CategoryDao;
import ru.gb.gbshopmart.dao.ManufacturerDao;
import ru.gb.gbshopmart.entity.Category;
import ru.gb.gbshopmart.entity.Manufacturer;
import ru.gb.gbshopmart.entity.Product;

import java.util.HashMap;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(uses = {ManufacturerMapper.class, CategoryMapper.class})
public interface ProductMapper {
    Product toProduct(ProductDto productDto, @Context ManufacturerDao manufacturerDao, @Context CategoryDao categoryDao);

    ProductDto toProductDto(Product product);

    default Manufacturer getManufacturer(String manufacturer, @Context ManufacturerDao manufacturerDao) {
        if (manufacturer == null) {
            return null;
        }
        return manufacturerDao.findByName(manufacturer).orElseThrow(NoSuchElementException::new);
    }

    default String getManufacturer(Manufacturer manufacturer) {
        if (manufacturer == null) {
            return null;
        }

        return manufacturer.getName();
    }


    @Mapping(source = "categories", target = "categories")
    default Set<Category> getCategories(Set<CategoryDto> categories, @Context CategoryDao categoryDao) {

        if (categories == null) {
            return null;
        }
        Set<Category> categoryHashSet = new HashSet<>();
        for (CategoryDto categoryDto :
                categories) {
            Category category = categoryDao.findByTitle(categoryDto.getTitle()).orElseThrow(NoSuchElementException::new);
            categoryHashSet.add(category);
        }
        return categoryHashSet;
    }

    @Mapping(source = "categories", target = "categories")
    default Set<CategoryDto> getCategories(Set<Category> categories) {
        if (categories == null) {
            return null;
        }
        return categories.stream()
                .map(category -> CategoryDto.builder()
                        .id(category.getId())
                        .title(category.getTitle())
                        .build())
                .collect(Collectors.toSet());
    }

}
