package ru.gb.gbshopmart.web.dto.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import ru.gb.gbshopmart.dao.CategoryDao;
import ru.gb.gbshopmart.dao.ManufacturerDao;
import ru.gb.gbshopmart.entity.Category;
import ru.gb.gbshopmart.entity.Manufacturer;
import ru.gb.gbshopmart.entity.Product;
import ru.gb.gbshopmart.web.dto.CategoryDto;
import ru.gb.gbshopmart.web.dto.ProductDto;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(uses = ManufacturerMapper.class)
public interface ProductMapper {
    Product toProduct(ProductDto productDto, @Context ManufacturerDao manufacturerDao);

    ProductDto toProductDto(Product product);

    default Manufacturer getManufacturer(String manufacturer, @Context ManufacturerDao manufacturerDao) {
        return manufacturerDao.findByName(manufacturer).orElseThrow(NoSuchElementException::new);
    }

    default String getManufacturer(Manufacturer manufacturer) {
        return manufacturer.getName();
    }

    default Set<Category> getCategories(Set<CategoryDto> categories, @Context CategoryDao categoryDao) {

        HashMap<Long, CategoryDto> mapCategory = new HashMap<>();

        categories.stream().forEach(categoryDto -> {
            mapCategory.put(categoryDto.getId(), categoryDto);
        });
        return categoryDao.findAll().stream()
                .filter(mapCategory::containsKey)
                .collect(Collectors.toSet());
    }

    default Set<CategoryDto> getCategories(Set<Category> categories) {

        return categories.stream()
                .map(category -> CategoryDto.builder()
                        .id(category.getId())
                        .title(category.getTitle())
                        .build())
                .collect(Collectors.toSet());
    }

}
