package ru.gb.gbshopmart.web.rest;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.gbshopmart.service.CategoryService;
import ru.gb.gbshopmart.web.dto.CategoryDto;
import ru.gb.gbshopmart.web.dto.ManufacturerDto;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {


    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getManufacturerList() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") Long id){

        CategoryDto categoryDto = null;
        if (id !=null){
            categoryDto = categoryService.findById(id);

        }
        if (categoryDto!=null){
            return new ResponseEntity<>(categoryDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping
    public ResponseEntity<?> handlePost(@Validated @RequestBody CategoryDto categoryDto){
        CategoryDto savedCategory = categoryService.save(categoryDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/api/v1/category/"+savedCategory.getId()));
        return new ResponseEntity<>(httpHeaders,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> handleUpdate(@PathVariable("id") Long id,@Validated @RequestBody CategoryDto categoryDto){
        categoryDto.setId(id);
        categoryService.save(categoryDto);
        return new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id) {
        categoryService.deleteById(id);
    }

}
