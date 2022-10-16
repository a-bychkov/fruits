package ru.fruits.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.fruits.client.IntegrationTestBase;
import ru.fruits.client.entity.Order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/sql/data.sql")
@AutoConfigureMockMvc
class OrdersControllerTest extends IntegrationTestBase {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void getOrders() {
        MvcResult response = mockMvc.perform(get("/api/v1/orders/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Order[] orders = objectMapper.readValue(response.getResponse().getContentAsString(), Order[].class);

        assertEquals(2, orders.length);
        assertEquals("test_order_2", orders[1].getName());
    }
}