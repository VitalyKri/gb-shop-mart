package ru.gb.gbshopmart.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.gb.gbshopmart.entity.Category;
import ru.gb.gbshopmart.entity.Product;
import ru.gb.gbshopmart.service.CategoryService;
import ru.gb.gbshopmart.service.ManufacturerService;
import ru.gb.gbshopmart.web.dto.CategoryDto;
import ru.gb.gbshopmart.web.dto.ManufacturerDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoryControllerIntegTest {

    @Mock
    CategoryService categoryService;

    @InjectMocks
    CategoryController categoryController;

    List<CategoryDto> categoriesDto = new ArrayList<>();

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        categoriesDto.add(CategoryDto.builder().id(1L).title("car").build());
        categoriesDto.add(CategoryDto.builder().id(2L).title("engine").build());

        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }


    @Test
    void mockMvcGetManufacturerListTest() throws Exception {

        given(categoryService.findAll()).willReturn(categoriesDto);

        mockMvc.perform(get("/api/v1/category"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].title").value("car"))
                .andExpect(jsonPath("$.[1].id").value("2"));

    }

}