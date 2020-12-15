package org.library.services;

import org.library.entity.BookCopy;
import org.library.entity.Shelf;
import org.library.exceptions.BookCopyIsExistsInShelfException;
import org.library.exceptions.BookNotFoundOnShelfException;
import org.library.interfaces.BookShelfRepository;

import java.util.Map;

public class BookShelfService {
    private final BookShelfRepository repository;

    public BookShelfService(BookShelfRepository repository) {
        this.repository = repository;
    }

    public Map<Integer, Shelf> getBookCopyIdAndShelf(int bookId) {
        return repository.getBookCopyIdAndShelf(bookId);
    }

    public boolean deleteBookCopyFromShelf(BookCopy bookCopy, Shelf shelf) throws BookNotFoundOnShelfException {
        //TODO
        Map<Integer, Shelf> bookCopyIdAndShelf = bookCopy.getBook().getBookCopyIdAndShelf();

        if (!bookCopyIdAndShelf.containsKey(bookCopy.getId())) {
            throw new BookNotFoundOnShelfException(bookCopy.getId(), shelf.getId());
        }
        bookCopyIdAndShelf.remove(bookCopy.getId());
        return repository.deleteBookCopyFromShelf(bookCopy, shelf);
    }

    public boolean addBookCopyToShelf(BookCopy bookCopy, Shelf shelf) throws BookCopyIsExistsInShelfException {
        Map<Integer, Shelf> bookCopyIdAndShelf = bookCopy.getBook().getBookCopyIdAndShelf();

        if (bookCopyIdAndShelf.containsKey(bookCopy.getId())) {
            throw new BookCopyIsExistsInShelfException(bookCopy.getId(), shelf.getInventNum());
        }

        bookCopyIdAndShelf.put(bookCopy.getId(), shelf);
        return repository.addBookCopyToShelf(bookCopy, shelf);
    }
}
