package ru.gb.gbshopmart.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.gb.gbapi.category.dto.CategoryDto;
import ru.gb.gbapi.common.enums.Status;
import ru.gb.gbapi.product.dto.ProductDto;
import ru.gb.gbshopmart.entity.Category;
import ru.gb.gbshopmart.entity.Manufacturer;
import ru.gb.gbshopmart.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception{

        mockMvc.perform(post("/api/v1/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                        .writeValueAsString(Category.builder()
                                .id(13L)
                                .title("testCat")
                                .build())))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/v1/manufacturer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                        .writeValueAsString(Manufacturer.builder()
                                .id(13L)
                                .name("testMan")
                                .build())))
                .andExpect(status().isCreated());


        mockMvc.perform(post("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                        .writeValueAsString(ProductDto.builder()
                                .id(13L)
                                .title("testProd")
                                .cost(BigDecimal.valueOf(10))
                                .manufactureDate(LocalDate.now())
                                .manufacturer("testMan")
                                .status(Status.ACTIVE)
                                .build())))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetProductList() throws Exception {
        mockMvc.perform(get("/api/v1/product"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.[0].title").value("testProd"))
                .andExpect(jsonPath("$.[0].manufacturer").value("testMan"));
    }

    @Test
    void testGetProduct() throws Exception {
        mockMvc.perform(get("/api/v1/product/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.title").value("testProd"))
                .andExpect(jsonPath("$.manufacturer").value("testMan"));
    }

    @Test
    void testSaveNewProductWithNotValidMan() throws Exception {

        mockMvc.perform(post("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                        .writeValueAsString(ProductDto.builder()
                                .id(99999L)
                                .title("testProdNew")
                                .cost(BigDecimal.valueOf(10))
                                .manufactureDate(LocalDate.now())
                                .manufacturer("testMan12")
                                .status(Status.ACTIVE)
                                .build())))
                .andExpect(status().isConflict());

    }

    @Test
    void testUpdateProductNotValidCategory() throws Exception {
        HashSet<CategoryDto> categoryDtos = new HashSet<>();
        CategoryDto testCat = CategoryDto.builder().id(1L).title("testCat").build();
        categoryDtos.add(testCat);

        mockMvc.perform(put("/api/v1/product/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                        .writeValueAsString(ProductDto.builder()
                                .status(Status.ACTIVE)
                                .title("testUpdate")
                                .cost(BigDecimal.valueOf(10))
                                .categories(categoryDtos)
                                .build())))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/api/v1/product/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.title").value("testUpdate"))
                .andExpect(jsonPath("$.categories.[0].title").value("testCat"));
        testCat.setTitle("1231");
        mockMvc.perform(put("/api/v1/product/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                        .writeValueAsString(ProductDto.builder()
                                .status(Status.ACTIVE)
                                .title("testUpdate")
                                .cost(BigDecimal.valueOf(10))
                                .categories(categoryDtos)
                                .build())))
                .andExpect(status().isConflict());
    }

}