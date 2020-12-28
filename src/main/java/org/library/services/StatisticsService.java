package org.library.services;

import org.library.interfaces.StatisticsRepository;

import java.util.Map;

public class StatisticsService {
    private final StatisticsRepository repository;

    public StatisticsService(StatisticsRepository repository) {
        this.repository = repository;
    }

    public Map<String, Integer> countByAuthorNameInShelf() {
        return repository.countByAuthorNameInShelf();
    }

    public Map<String, Integer> countByAuthorNameInRent() {
        return repository.countByAuthorNameInRent();
    }

    public Map<String, Integer> countByGenreTitleInShelf() {
        return repository.countByGenreTitleInShelf();
    }

    public Map<String, Integer> countByGenreTitleInRent() {
        return repository.countByGenreTitleInRent();
    }


}
