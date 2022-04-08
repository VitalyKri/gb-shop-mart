package ru.gb.gbshopmart.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.gbapi.product.dto.ProductDto;
import ru.gb.gbshopmart.service.ProductService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductDto> getProductList() {
        return productService.findAll();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable("productId") Long id) {
        ProductDto product;
        if (id != null) {
            product = productService.findById(id);
            if (product != null) {
                return new ResponseEntity<>(product, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> handlePost(@Validated @RequestBody ProductDto productDto) {
        try {
            ProductDto savedProduct = productService.save(productDto);
            if (savedProduct == null){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(URI.create("/api/v1/product/" + savedProduct.getId()));
            return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.CONFLICT);
        }

    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> handleUpdate(@PathVariable("productId") Long id, @Validated @RequestBody ProductDto productDto) {
        try {
            productDto.setId(id);
            productService.save(productDto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }

    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("productId") Long id) {
        productService.deleteById(id);
    }
}
