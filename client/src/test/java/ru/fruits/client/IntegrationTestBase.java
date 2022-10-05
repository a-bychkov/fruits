package ru.fruits.client;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.fruits.client.initializer.Mysql;

@SpringBootTest
@ContextConfiguration(initializers = {
    Mysql.Initializer.class
})
@Transactional
public abstract class IntegrationTestBase {

    @BeforeAll
    static void init() {
        Mysql.dbContainer.start();
    }
}