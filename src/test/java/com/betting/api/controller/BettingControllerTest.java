package com.betting.api.controller;

import com.betting.api.TestUtils;
import com.betting.api.dto.BettingRequest;
import com.betting.api.service.BettingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(BettingController.class)
public class BettingControllerTest {

    private final static String URL = "/api/v1/betting/bet";
    private final Double result = 48.385;

    @MockBean
    private BettingService bettingService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testBet_Successful() throws Exception {
        BettingRequest bettingRequest = BettingRequest.builder()
                .betNumber(52)
                .betAmount(75.0)
                .build();

        when(bettingService.calculateWinning(bettingRequest))
                .thenReturn(result);

        MvcResult mockResult = mockMvc.perform(
                MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(TestUtils.objectToJson(bettingRequest))).andReturn();
        Assertions.assertEquals(Double.valueOf(mockResult.getResponse().getContentAsString()), result);
    }
}
