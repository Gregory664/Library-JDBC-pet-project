package org.library.interfaces;

import org.library.entity.Shelf;

import java.util.Optional;

public interface ShelfRepository extends JDBCRepository<Shelf, Integer> {
    Optional<Shelf> findByInventNum(String inventNum);
}
