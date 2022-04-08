package ru.gb.gbshopmart.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.gb.gbapi.manufacturer.dto.ManufacturerDto;
import ru.gb.gbshopmart.entity.Category;
import ru.gb.gbshopmart.entity.Manufacturer;
import ru.gb.gbshopmart.service.CategoryService;
import ru.gb.gbshopmart.service.ManufacturerService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoryControllerTest {

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
                                .title("test")
                                .build())))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(1)
    void testGetCategoryList() throws Exception{
        mockMvc.perform(get("/api/v1/category"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("id")))
            .andExpect(jsonPath("$.[0].title").value("test"));
    }

    @Test
    @Order(1)
    void getCategoryById() throws Exception {
        mockMvc.perform(get("/api/v1/category/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.title").value("test"));
    }

    @Test
    @Order(2)
    void testUpdateByPostMethCategory() throws Exception  {
        mockMvc.perform(post("/api/v1/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                        .writeValueAsString(Category.builder()
                                .id(1L)
                                .title("test2")
                                .build())))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/api/v1/category/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.title").value("test2"));
    }

    @Test
    @Order(4)
    void testUpdateByPutMethCategory() throws Exception  {
        mockMvc.perform(put("/api/v1/category/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                        .writeValueAsString(Category.builder()
                                .title("test3")
                                .build())))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/category/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.title").value("test3"));
    }

    @Test
    @Order(4)
    void testFailedUpdateByPutMethCategory() throws Exception  {
        mockMvc.perform(put("/api/v1/category/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                        .writeValueAsString(Category.builder()
                                .title("test3")
                                .build())))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(5)
    void testDeleteCategory() throws Exception  {
        mockMvc.perform(post("/api/v1/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                        .writeValueAsString(Category.builder()
                                .id(2L)
                                .title("testDelete")
                                .build())))
                .andExpect(status().isCreated());
        mockMvc.perform(delete("/api/v1/category/2"))
                .andExpect(status().isNoContent());
    }


}