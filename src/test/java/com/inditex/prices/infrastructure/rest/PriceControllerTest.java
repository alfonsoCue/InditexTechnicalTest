package com.inditex.prices.infrastructure.rest;

import com.inditex.prices.application.PriceService;
import com.inditex.prices.domain.exception.PriceNotFoundException;
import com.inditex.prices.domain.model.Price;
import com.inditex.prices.infrastructure.rest.mapper.PriceResponseMapper;
import com.inditex.prices.infrastructure.rest.model.PriceResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PriceController.class)
public class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PriceService priceService;
    @MockBean
    private PriceResponseMapper priceResponseMapper;


    @Test
    void getPrice_status_OK() throws Exception {

        when(priceService.search(any(), any(), any())).thenReturn(mock(Price.class));
        when(priceResponseMapper.toResponse(any())).thenReturn(mock(PriceResponse.class));

        mockMvc.perform(get("/price")
                        .param("date", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk());
    }
    @Test
    void getPrice_when_brandId_negative_status_BAD_REQUEST() throws Exception {

        when(priceService.search(any(), any(), any())).thenReturn(mock(Price.class));
        when(priceResponseMapper.toResponse(any())).thenReturn(mock(PriceResponse.class));

        mockMvc.perform(get("/price")
                        .param("date", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "-1"))
                .andExpect(status().isBadRequest());
    }
    @Test
    void getPrice_when_productId_negative_status_BAD_REQUEST() throws Exception {

        when(priceService.search(any(), any(), any())).thenReturn(mock(Price.class));
        when(priceResponseMapper.toResponse(any())).thenReturn(mock(PriceResponse.class));

        mockMvc.perform(get("/price")
                        .param("date", "2020-06-14T10:00:00")
                        .param("productId", "-35455")
                        .param("brandId", "1"))
                .andExpect(status().isBadRequest());
    }
    @Test
    void getPrice_when_date_malformed_status_BAD_REQUEST() throws Exception {

        when(priceService.search(any(), any(), any())).thenReturn(mock(Price.class));
        when(priceResponseMapper.toResponse(any())).thenReturn(mock(PriceResponse.class));

        mockMvc.perform(get("/price")
                        .param("date", "2020-06-99T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isBadRequest());
    }
    @Test
    void getPrice_when_date_not_informed_status_BAD_REQUEST() throws Exception {

        when(priceService.search(any(), any(), any())).thenReturn(mock(Price.class));
        when(priceResponseMapper.toResponse(any())).thenReturn(mock(PriceResponse.class));

        mockMvc.perform(get("/price")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isBadRequest());
    }
    @Test
    void getPrice_when_productId_not_informed_status_BAD_REQUEST() throws Exception {

        when(priceService.search(any(), any(), any())).thenReturn(mock(Price.class));
        when(priceResponseMapper.toResponse(any())).thenReturn(mock(PriceResponse.class));

        mockMvc.perform(get("/price")
                        .param("date", "2025-06-14T10:00:00")
                        .param("brandId", "1"))
                .andExpect(status().isBadRequest());
    }
    @Test
    void getPrice_when_brandId_not_informed_status_BAD_REQUEST() throws Exception {

        when(priceService.search(any(), any(), any())).thenReturn(mock(Price.class));
        when(priceResponseMapper.toResponse(any())).thenReturn(mock(PriceResponse.class));

        mockMvc.perform(get("/price")
                        .param("date", "2025-06-14T10:00:00")
                        .param("productId", "35455"))
                .andExpect(status().isBadRequest());
    }
    @Test
    void getPrice_when_price_not_found_status_NOT_FOUND() throws Exception {

        when(priceService.search(any(), any(), any())).thenThrow(new PriceNotFoundException());
        when(priceResponseMapper.toResponse(any())).thenReturn(mock(PriceResponse.class));

        mockMvc.perform(get("/price")
                        .param("date", "2025-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exception").value("PriceNotFoundException"));
    }
    @Test
    void getPrice_when_internal_error_status_INTERNAL_SERVER_ERROR() throws Exception {

        when(priceService.search(any(), any(), any())).thenThrow(new RuntimeException());
        when(priceResponseMapper.toResponse(any())).thenReturn(mock(PriceResponse.class));

        mockMvc.perform(get("/price")
                        .param("date", "2025-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isInternalServerError());
    }

}
