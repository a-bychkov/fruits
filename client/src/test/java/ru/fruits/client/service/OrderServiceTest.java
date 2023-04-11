package ru.fruits.client.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.fruits.client.dto.OrderFilter;
import ru.fruits.client.entity.Order;
import ru.fruits.client.repository.OrderRepository;
import ru.fruits.client.testcontainers.ClientElasticsearchContainer;
import ru.fruits.client.testcontainers.ClientMysqlContainer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for {@link OrderService}.
 */
@SpringBootTest
@Testcontainers
class OrderServiceTest {
    @Autowired
    OrderService underTest;
    @Autowired
    OrderRepository repository;
    @Autowired
    JdbcTemplate jdbcTemplate;

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
    @Sql("/sql/data.sql")
    void getOrders() {
        List<Order> orders = underTest.getOrders();
        assertEquals(2, orders.size());
    }

    @Test
    @Sql("/sql/data.sql")
    void getOrdersWithFilter() {
        // given
        OrderFilter filter = OrderFilter.builder()
                .name("nOthE")
                .price(500)
                .build();
        // when

        // then
        List<Order> orders = underTest.getOrders(filter);

        deleteTestDataFromDB();

        assertEquals(1, orders.size());
        assertEquals("another test order", orders.get(0).getName());
    }

    @Test
    @DisplayName("Should return two Orders via fulltext search")
    void should_return_orders_when_getOrderFullText() {
        // given
        final String substring = "eSt";
        final int limit = 20;
        final int offset = 0;

        // when
        saveTestOrdersIntoDB();

        // then
        List<Order> result = underTest.getOrderFullText(substring, limit, offset);

        deleteTestDataFromDB();

        assertEquals(2, result.size());
    }

    @SneakyThrows
    private void saveTestOrdersIntoDB() {
        Order o1 = new Order();
        o1.setName("test name");
        o1.setPrice(100);
        repository.save(o1);

        Order o2 = new Order();
        o2.setName("another teST name");
        o2.setPrice(200);
        repository.save(o2);

        // wait for index update
        Thread.sleep(1000);
    }

    private void deleteTestDataFromDB() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "orders");
    }
}