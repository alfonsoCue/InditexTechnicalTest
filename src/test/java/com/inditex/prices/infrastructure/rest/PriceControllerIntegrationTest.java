package com.inditex.prices.infrastructure.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PriceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test 1: petición a las 10:00 del día 14 del producto 35455 para la brand 1 (ZARA)")
    void test1_2020_06_14_10_00_00() throws Exception
    {
        mockMvc.perform(get("/price")
                        .param("date", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product_id").value(35455))
                .andExpect(jsonPath("$.brand_id").value(1))
                .andExpect(jsonPath("$.price_list").value(1))
                .andExpect(jsonPath("$.start_date").value("2020-06-14T00:00:00"))
                .andExpect(jsonPath("$.end_date").value("2020-12-31T23:59:59"))
                .andExpect(jsonPath("$.price").value(35.5))
                .andExpect(jsonPath("$.curr").value("EUR"));
    }
    @Test
    @DisplayName("Test 2: petición a las 16:00 del día 14 del producto 35455 para la brand 1 (ZARA)")
    void test1_2020_06_14_16_00_00() throws Exception
    {
        mockMvc.perform(get("/price")
                        .param("date", "2020-06-14T16:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product_id").value(35455))
                .andExpect(jsonPath("$.brand_id").value(1))
                .andExpect(jsonPath("$.price_list").value(2))
                .andExpect(jsonPath("$.start_date").value("2020-06-14T15:00:00"))
                .andExpect(jsonPath("$.end_date").value("2020-06-14T18:30:00"))
                .andExpect(jsonPath("$.price").value(25.45))
                .andExpect(jsonPath("$.curr").value("EUR"));
    }
    @Test
    @DisplayName("Test 3: petición a las 21:00 del día 14 del producto 35455 para la brand 1 (ZARA)")
    void test3_2020_06_14_21_00_00() throws Exception
    {
        mockMvc.perform(get("/price")
                        .param("date", "2020-06-14T21:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product_id").value(35455))
                .andExpect(jsonPath("$.brand_id").value(1))
                .andExpect(jsonPath("$.price_list").value(1))
                .andExpect(jsonPath("$.start_date").value("2020-06-14T00:00:00"))
                .andExpect(jsonPath("$.end_date").value("2020-12-31T23:59:59"))
                .andExpect(jsonPath("$.price").value(35.5))
                .andExpect(jsonPath("$.curr").value("EUR"));
    }
    @Test
    @DisplayName("Test 4: petición a las 10:00 del día 15 del producto 35455 para la brand 1 (ZARA)")
    void test4_2020_06_15_10_00_00() throws Exception
    {
        mockMvc.perform(get("/price")
                        .param("date", "2020-06-15T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product_id").value(35455))
                .andExpect(jsonPath("$.brand_id").value(1))
                .andExpect(jsonPath("$.price_list").value(3))
                .andExpect(jsonPath("$.start_date").value("2020-06-15T00:00:00"))
                .andExpect(jsonPath("$.end_date").value("2020-06-15T11:00:00"))
                .andExpect(jsonPath("$.price").value(30.5))
                .andExpect(jsonPath("$.curr").value("EUR"));
    }
    @Test
    @DisplayName("Test 5: petición a las 21:00 del día 16 del producto 35455 para la brand 1 (ZARA)")
    void test5_2020_06_16_21_00_00() throws Exception
    {
        mockMvc.perform(get("/price")
                        .param("date", "2020-06-16T21:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product_id").value(35455))
                .andExpect(jsonPath("$.brand_id").value(1))
                .andExpect(jsonPath("$.price_list").value(4))
                .andExpect(jsonPath("$.start_date").value("2020-06-15T16:00:00"))
                .andExpect(jsonPath("$.end_date").value("2020-12-31T23:59:59"))
                .andExpect(jsonPath("$.price").value(38.95))
                .andExpect(jsonPath("$.curr").value("EUR"));
    }
    @Test
    @DisplayName("Test 6: petición con una fecha sin precios")
    void test6_date_without_prices_should_return_NotFound() throws Exception
    {
        mockMvc.perform(get("/price")
                        .param("date", "2021-01-01T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exception").value("PriceNotFoundException"));
    }
    @Test
    @DisplayName("Test 7: petición con un brandId que no existe")
    void test7_brandId_not_exist_should_return_NotFound() throws Exception
    {
        mockMvc.perform(get("/price")
                        .param("date", "2020-06-16T21:00:00")
                        .param("productId", "35455")
                        .param("brandId", "2"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exception").value("PriceNotFoundException"));
    }
    @Test
    @DisplayName("Test 8: petición con un productId que no existe")
    void test8_productId_not_exist_should_return_NotFound() throws Exception
    {
        mockMvc.perform(get("/price")
                        .param("date", "2020-06-16T21:00:00")
                        .param("productId", "35456")
                        .param("brandId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exception").value("PriceNotFoundException"));
    }

}
