package com.inditex.prices.infrastructure.rest;

import com.inditex.prices.application.PriceService;
import com.inditex.prices.domain.exception.PriceNotFoundException;
import com.inditex.prices.domain.model.Price;
import com.inditex.prices.infrastructure.rest.mapper.PriceResponseMapper;
import com.inditex.prices.infrastructure.rest.model.PriceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PriceControllerTest {

    @Mock
    private PriceService priceService;
    @Mock
    private PriceResponseMapper priceResponseMapper;

    private PriceController priceController;

    @BeforeEach
    void init(){
        priceController = new PriceController(priceService, priceResponseMapper);
    }
    @Test
    void givenCorrectParameters_WhenSearchPrice_ThenResponse200()
    {
        when(priceService.search(any(), any(), any())).thenReturn(mock(Price.class));
        ResponseEntity<PriceResponse> response = priceController.searchPrice(LocalDateTime.now(), 1L, 1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void givenCorrectParameters_WhenSearchPrice_ThenNotFoundException()
    {
        when(priceService.search(any(), any(), any())).thenThrow(new PriceNotFoundException());
        PriceNotFoundException exception = assertThrows(PriceNotFoundException.class, ()-> priceController.searchPrice(LocalDateTime.now(), 1L, 1L));

        assertNotNull(exception);
        assertEquals("Not prices found", exception.getMessage());
    }
}
