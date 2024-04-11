package com.inditex.prices.infrastructure.rest;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

    @ParameterizedTest
    @CsvSource({
            "2020-06-14T10:00:00, 35455, 1, 35455, 1, 1, 2020-06-14T00:00:00, 2020-12-31T23:59:59, 35.5, EUR",
            "2020-06-14T16:00:00, 35455, 1, 35455, 1, 2, 2020-06-14T15:00:00, 2020-06-14T18:30:00, 25.45, EUR",
            "2020-06-14T21:00:00, 35455, 1, 35455, 1, 1, 2020-06-14T00:00:00, 2020-12-31T23:59:59, 35.5, EUR",
            "2020-06-15T10:00:00, 35455, 1, 35455, 1, 3, 2020-06-15T00:00:00, 2020-06-15T11:00:00, 30.5, EUR",
            "2020-06-16T21:00:00, 35455, 1, 35455, 1, 4, 2020-06-15T16:00:00, 2020-12-31T23:59:59, 38.95, EUR"
    })
    void givenParameter_whenSearchPrice_thenReturn200AndCorrectValues(String inputDate,
              String inputProductId,
              String inputBrandId,
              Integer expectedProductId,
              Integer expectedBrandId,
              Integer expectedPriceList,
              String expectedStartDate,
              String expectedEndDate,
              Float expectedPrice,
              String expectedCurrency) throws Exception
    {
        mockMvc.perform(get("/price")
                        .param("date", inputDate)
                        .param("productId", inputProductId)
                        .param("brandId", inputBrandId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product_id").value(expectedProductId))
                .andExpect(jsonPath("$.brand_id").value(expectedBrandId))
                .andExpect(jsonPath("$.price_list").value(expectedPriceList))
                .andExpect(jsonPath("$.start_date").value(expectedStartDate))
                .andExpect(jsonPath("$.end_date").value(expectedEndDate))
                .andExpect(jsonPath("$.price").value(expectedPrice))
                .andExpect(jsonPath("$.currency").value(expectedCurrency));
    }
    @ParameterizedTest
    @CsvSource({
            "2021-01-01T10:00:00, 35455, 1, PriceNotFoundException",
            "2020-06-16T21:00:00, 35455, 2, PriceNotFoundException",
            "2020-06-16T21:00:00, 35456, 1, PriceNotFoundException"
    })
    void givenParameters_whenSearchPrice_thenReturnPriceNotFoundException(
            String inputDate,
            String inputProductId,
            String inputBrandId,
            String expectedException
    ) throws Exception
    {
        mockMvc.perform(get("/price")
                        .param("date", inputDate)
                        .param("productId", inputProductId)
                        .param("brandId", inputBrandId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exception").value(expectedException));
    }

}
