package ru.fruits.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.fruits.client.IntegrationTestBase;
import ru.fruits.client.entity.Order;
import ru.fruits.client.service.OrderService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/sql/data.sql")
@AutoConfigureMockMvc
class OrdersControllerTest extends IntegrationTestBase {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @SpyBean
    OrderService orderService;

    @Test
    @WithMockUser
    @SneakyThrows
    void getOrders() {
        // given
        MvcResult response = mockMvc.perform(get("/api/v1/orders/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        // when
        Order[] orders = objectMapper.readValue(response.getResponse().getContentAsString(), Order[].class);

        // then
        verify(orderService, times(1)).getOrders();
        assertEquals(2, orders.length);
        assertEquals("test_order_2", orders[1].getName());
    }
}