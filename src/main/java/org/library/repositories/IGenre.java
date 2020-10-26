package org.library.repositories;

import org.library.entity.Genre;
import org.library.utils.JDBCRepository;

public interface IGenre extends JDBCRepository<Genre, Integer> {
}
