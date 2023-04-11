package ru.fruits.client.repository;

import lombok.RequiredArgsConstructor;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.fruits.client.entity.Order;

import javax.persistence.EntityManager;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomizedOrderRepositoryImpl implements CustomizedOrderRepository {
    private final EntityManager em;

    @Override
    @Transactional
    public List<Order> search(String terms, int limit, int offset) {
        SearchSession searchSession = Search.session(em);

        SearchResult<Order> result = searchSession.search(Order.class)
                .where(f -> f.wildcard()
                        .fields("name")
                        .matching("*" + terms + "*"))
                .fetch(20);

        return result.hits();
    }
}