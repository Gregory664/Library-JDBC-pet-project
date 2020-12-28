package org.library.interfaces;

import java.util.Map;

public interface StatisticsRepository {
    Map<String, Integer> countByAuthorNameInShelf();

    Map<String, Integer> countByAuthorNameInRent();

    Map<String, Integer> countByGenreTitleInShelf();

    Map<String, Integer> countByGenreTitleInRent();
}

