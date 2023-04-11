package ru.fruits.client.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
@Log4j2
public class IndexingService {
    private final EntityManager em;

    @Transactional
    public void initiateIndexing() throws InterruptedException {
        log.info("Initiating indexing...");

        SearchSession searchSession = Search.session(em);
        MassIndexer indexer = searchSession.massIndexer().threadsToLoadObjects(7);
        indexer.startAndWait();

        log.info("All entities indexed");
    }
}
