package com.betting.api;

import com.betting.api.dto.PlayerRequest;
import com.betting.api.utils.RandomNumberGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BettingGameIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final String BASE_URL = "/api/v1/betting/bet";

    @Test
    public void playGame_shouldReturnWin() throws Exception {
        try (MockedStatic<RandomNumberGenerator> mockedStatic = Mockito.mockStatic(RandomNumberGenerator.class)) {
            mockedStatic.when(() -> RandomNumberGenerator.generate(Mockito.anyInt(), Mockito.anyInt())).thenReturn(70);

            // Arrange
            PlayerRequest request = new PlayerRequest();
            request.setBetAmount(40.5);
            request.setBetNumber(50);

            // Act
            MvcResult mvcResult = mockMvc
                    .perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andReturn();

            String jsonResponse = mvcResult.getResponse().getContentAsString();
            //Assert
            Double response = new ObjectMapper().readValue(jsonResponse, Double.class);
            double expectedWin = request.getBetAmount() * (99.0 / (100 - request.getBetNumber()));
            assertThat(response).isEqualTo(expectedWin);
        }
    }

    @Test
    public void playGame_randomNumberLessThanBettingNumber_shouldReturnZero() throws Exception {
        try (MockedStatic<RandomNumberGenerator> mockedStatic = Mockito.mockStatic(RandomNumberGenerator.class)) {
            mockedStatic.when(() -> RandomNumberGenerator.generate(Mockito.anyInt(), Mockito.anyInt())).thenReturn(10);

            // Arrange
            PlayerRequest request = new PlayerRequest();
            request.setBetAmount(40.5);
            request.setBetNumber(50);

            // Act
            MvcResult mvcResult = mockMvc
                    .perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andReturn();

            String jsonResponse = mvcResult.getResponse().getContentAsString();
            //Assert
            Double response = new ObjectMapper().readValue(jsonResponse, Double.class);
            assertThat(response).isEqualTo(0);
        }
    }
}