package ru.fruits.client.testcontainers;

import org.testcontainers.containers.MySQLContainer;

public class ClientMysqlContainer extends MySQLContainer {
    private static final String DOCKER_IMAGE = "mysql:latest";
    private static final String DB_NAME = "mydb";
    private final int PORT = 3306;
    private static final String DB_USERNAME = "user";
    private static final String DB_PASSWORD = "password";

    public ClientMysqlContainer() {
        super(DOCKER_IMAGE);
        addFixedExposedPort(PORT, PORT);
        withReuse(true);
        withDatabaseName(DB_NAME);
        withUsername(DB_USERNAME);
        withPassword(DB_PASSWORD);
    }
}
