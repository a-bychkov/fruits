package ru.fruits.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.fruits.client.entity.Order;
import ru.fruits.client.service.OrderService;
import ru.fruits.client.testcontainers.ClientElasticsearchContainer;
import ru.fruits.client.testcontainers.ClientMysqlContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Sql("/sql/data.sql")
@AutoConfigureMockMvc
@Testcontainers
class OrdersControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @SpyBean
    OrderService orderService;

    @Container
    static ElasticsearchContainer elasticsearchContainer = new ClientElasticsearchContainer();
    @Container
    static MySQLContainer mysqlContainer = new ClientMysqlContainer();

    @BeforeAll
    static void setUp() {
        elasticsearchContainer.start();
        mysqlContainer.start();
    }

    @AfterAll
    static void tearDown() {
        elasticsearchContainer.stop();
        mysqlContainer.stop();
    }

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
        assertEquals("another test order", orders[1].getName());
    }
}