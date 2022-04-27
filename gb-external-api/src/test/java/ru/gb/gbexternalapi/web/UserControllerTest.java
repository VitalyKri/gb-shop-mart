package ru.gb.gbexternalapi.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.gb.gbapi.category.dto.CategoryDto;
import ru.gb.gbapi.common.enums.Status;
import ru.gb.gbapi.manufacturer.dto.ManufacturerDto;
import ru.gb.gbapi.product.dto.ProductDto;
import ru.gb.gbapi.security.UserDto;
import ru.gb.gbexternalapi.dao.security.AccountRoleDao;
import ru.gb.gbexternalapi.dao.security.AuthorityDao;
import ru.gb.gbexternalapi.entity.security.AccountRole;
import ru.gb.gbexternalapi.entity.security.Authority;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthorityDao authorityDao;

    @Autowired
    AccountRoleDao accountRoleDao;


    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

        authorityDao.save(Authority.builder().permission("product.read").build());
        accountRoleDao.save(AccountRole.builder().name("ROLE_USER").build());

    }

    @Test
    @Order(1)
    void testCreateUser() throws Exception {
        mockMvc.perform(post("/api/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                        .writeValueAsString(UserDto.builder()
                                .firstname("Vitaly")
                                .lastname("Krivobokov")
                                .email("woodanon123@gmail.com")
                                .username("vitaly")
                                .password("admin")
                                .phone("89999999999")
                                .build())))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    void testGetUser() throws Exception {
        mockMvc.perform(get("/api/v1/user"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("username")))
                .andExpect(jsonPath("$.[0].firstname").value("Vitaly"))
                .andExpect(jsonPath("$.[0].lastname").value("Krivobokov"));
    }

    @Test
    @Order(3)
    void testGetById() throws Exception {
        mockMvc.perform(get("/api/v1/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("username")))
                .andExpect(jsonPath("$.firstname").value("Vitaly"))
                .andExpect(jsonPath("$.lastname").value("Krivobokov"));
    }


    @Test
    @Order(4)
    void testDeleteUser() throws Exception {
        mockMvc.perform(get("/api/v1/user/1"))
                .andExpect(status().isOk());
        mockMvc.perform(delete("/api/v1/user/1"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].firstname").doesNotExist());
    }



}