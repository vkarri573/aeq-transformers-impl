package com.aeq.transformers.impl.app;

import com.aeq.transformers.impl.app.controller.TransformerController;
import com.aeq.transformers.impl.app.model.Transformer;
import com.aeq.transformers.impl.app.service.TransformerService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@WebMvcTest(TransformerController.class)
public class TransformerControllerTest {

    Transformer mockTransformer = new Transformer(100l, "Soundwave", "D", 8, 9, 2, 6, 7, 5, 6, 10);
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TransformerService transformerService;
    String mockPayload = "{\"id\": 100,\"name\": \"Soundwave\",\"team\": \"D\",\"strength\": 8,\"intelligence\": 9,\"speed\": 2,\"endurance\": 6,\"rank\": 7,\"courage\": 5,\"firePower\": 6,\"skill\": 10,\"overallRating\": 31}";

    @Test
    public void getAllTransformersShouldReturnTransformers() throws Exception {
        List<Transformer> mockTransformersList = new ArrayList<>();
        mockTransformersList.add(mockTransformer);
        when(transformerService.getAllTransformers()).thenReturn(mockTransformersList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/transformers");
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String responsePayload = mvcResult.getResponse().getContentAsString();

        String expectedPayload = "[{id: 100,name: Soundwave,team: D,strength: 8,intelligence: 9,speed: 2,endurance: 6,rank: 7,courage: 5,firePower: 6,skill: 10}]";
        JSONAssert.assertEquals(expectedPayload, responsePayload, false);
    }

    @Test
    public void createTransformerShouldReturnCreatedMessage() throws Exception {
        when(transformerService.createTransformer(any(Transformer.class))).thenReturn(mockTransformer);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/transformers")
                .content(mockPayload)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());

        String responsePayload = mvcResult.getResponse().getContentAsString();
        String expectedPayload = "{\"status\":\"Transformer created\"}";
        assertEquals(expectedPayload, responsePayload);
    }

    @Test
    public void updateTransformerShouldReturnUpdatedMessage() throws Exception {
        when(transformerService.updateTransformer(any(Transformer.class))).thenReturn(mockTransformer);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/transformers")
                .content(mockPayload)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.ACCEPTED.value(), mvcResult.getResponse().getStatus());

        String responsePayload = mvcResult.getResponse().getContentAsString();
        String expectedPayload = "{\"status\":\"Transformer updated\"}";
        assertEquals(expectedPayload, responsePayload);
    }

    @Test
    public void deleteTransformerShouldReturnDeletedMessage() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/transformers")
                .content(mockPayload)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());

        String responsePayload = mvcResult.getResponse().getContentAsString();
        String expectedPayload = "{\"status\":\"Transformer deleted\"}";
        assertEquals(expectedPayload, responsePayload);
    }
}
