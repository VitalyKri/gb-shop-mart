package ru.gb.gbshopmart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.gbshopmart.dao.CategoryDao;
import ru.gb.gbshopmart.dao.ProductDao;
import ru.gb.gbshopmart.entity.Category;
import ru.gb.gbshopmart.entity.Manufacturer;
import ru.gb.gbshopmart.web.dto.CategoryDto;
import ru.gb.gbshopmart.web.dto.ManufacturerDto;
import ru.gb.gbshopmart.web.dto.mapper.CategoryMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryDao categoryDao;
    private final ProductDao productDao;


    private final CategoryMapper categoryMapper;


    @Transactional(propagation = Propagation.NEVER, isolation = Isolation.DEFAULT)
    public long count() {
        return productDao.count();
    }

    public List<CategoryDto> findAll() {
        return categoryDao.findAll().stream().map(categoryMapper::toCategoryDto).collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public CategoryDto findById(Long id) {
        return categoryMapper.toCategoryDto(categoryDao.findById(id).orElse(null));
    }

    @Transactional
    public CategoryDto save(final CategoryDto categoryDto) {

        Category category = categoryMapper.toCategory(categoryDto,productDao);

        if (category.getId() != null) {
            Category finalCategory = category;
            categoryDao.findById(categoryDto.getId()).ifPresent(
                    (p) -> finalCategory.setVersion(p.getVersion())
            );
        } else if (category.getTitle()!=null){
            category = categoryDao.findByTitle(category.getTitle()).orElse(null);
        }

        return categoryMapper.toCategoryDto(categoryDao.save(category));
    }


    public void deleteById(Long id) {
        try {
            categoryDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
        }
    }

}
