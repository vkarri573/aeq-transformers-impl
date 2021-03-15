package com.aeq.transformers.impl.app;

import com.aeq.transformers.impl.app.controller.BattleController;
import com.aeq.transformers.impl.app.model.FinalGameResult;
import com.aeq.transformers.impl.app.service.BattleService;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(BattleController.class)
public class BattleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BattleService battleService;
    FinalGameResult finalGameResult = new FinalGameResult();
    String expectedPayload = "{numberOfBattles: 1,winningTeam: Decepticons,winnerMembersOfWinningTeam: [Soundwave],losingTeam: Autobots,survivingMembersOfLosingTeam: [Hubcap]}";

    @Test
    public void conductGameShouldReturnFinalresult() throws Exception {

        finalGameResult.setNumberOfBattles(1);
        finalGameResult.setWinningTeam("Decepticons");
        finalGameResult.setWinnerMembersOfWinningTeam(Arrays.asList("Soundwave"));
        finalGameResult.setLosingTeam("Autobots");
        finalGameResult.setSurvivingMembersOfLosingTeam(Arrays.asList("Hubcap"));

        when(battleService.conductGameForSelectedTransformers(any(List.class))).thenReturn(finalGameResult);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/battle/transformers?ids=100,101,102");
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responsePayload = mvcResult.getResponse().getContentAsString();
        JSONAssert.assertEquals(expectedPayload, responsePayload, false);
    }
}
