package org.library.interfaces;

import org.library.entity.Publisher;

import java.util.Optional;

public interface PublisherRepository extends JDBCRepository<Publisher, Integer> {
    Optional<Publisher> findByTitle(String title);
}
