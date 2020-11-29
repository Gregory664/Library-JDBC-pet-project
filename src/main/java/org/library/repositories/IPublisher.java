package org.library.repositories;

import org.library.entity.Publisher;
import org.library.utils.JDBCRepository;

import java.util.Optional;

public interface IPublisher extends JDBCRepository<Publisher, Integer> {
    Optional<Publisher> findByTitle(String title);
}
