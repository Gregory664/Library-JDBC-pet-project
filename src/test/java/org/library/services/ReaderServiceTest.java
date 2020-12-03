package org.library.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.library.entity.*;
import org.library.exceptions.newExc.EntityNotFoundByIdException;
import org.library.interfaces.BookRentRepository;
import org.library.interfaces.ReaderRepository;
import org.library.repositories.BookRentRepositoryImpl;
import org.library.repositories.ReaderRepositoryImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReaderServiceTest {
    private static final ReaderRepository readerRepository = mock(ReaderRepositoryImpl.class);
    private static final BookRentRepository bookRentRepository = mock(BookRentRepositoryImpl.class);
    private static final ReaderService readerService = new ReaderService(readerRepository, bookRentRepository);
    private static List<Reader> readerList;

    @BeforeAll
    static void initialize() {
        BookCopy bookCopy1 = new BookCopy(1, Book.builder()
                .id(1)
                .title("test title")
                .author(new Author(1, "test author 1"))
                .publisher(new Publisher(1, "test publisher 1"))
                .genre(new Genre(1, "test genre 1"))
                .length(100)
                .build());
        BookCopy bookCopy2 = new BookCopy(2, Book.builder()
                .id(2)
                .title("test title")
                .author(new Author(2, "test author 2"))
                .publisher(new Publisher(2, "test publisher 2"))
                .genre(new Genre(2, "test genre 2"))
                .length(200)
                .build());
        BookCopy bookCopy3 = new BookCopy(3, Book.builder()
                .id(3)
                .title("test title")
                .author(new Author(3, "test author 3"))
                .publisher(new Publisher(3, "test publisher 3"))
                .genre(new Genre(3, "test genre 3"))
                .length(300)
                .build());
        BookCopy bookCopy4 = new BookCopy(4, Book.builder()
                .id(4)
                .title("test title")
                .author(new Author(4, "test author 4"))
                .publisher(new Publisher(4, "test publisher 4"))
                .genre(new Genre(4, "test genre 4"))
                .length(400)
                .build());

        Map<BookCopy, Period> rentBookCopies1 = Map.of(
                bookCopy1, new Period(LocalDate.now(), LocalDate.now().plusDays(2)),
                bookCopy2, new Period(LocalDate.now(), LocalDate.now().plusDays(7))
        );

        Map<BookCopy, Period> rentBookCopies2 = Map.of(
                bookCopy3, new Period(LocalDate.now(), LocalDate.now().plusDays(1)),
                bookCopy4, new Period(LocalDate.now(), LocalDate.now().plusDays(3))
        );

        readerList = List.of(
                Reader.builder()
                        .id(1)
                        .fio("test fio")
                        .address("test address")
                        .phone("88005553535")
                        .passport("2000111222")
                        .rentBookCopies(rentBookCopies1)
                        .build(),
                Reader.builder()
                        .id(2)
                        .fio("test fio 2")
                        .address("test address 2")
                        .phone("88005553536")
                        .passport("2000333444")
                        .rentBookCopies(rentBookCopies2)
                        .build()
        );

        when(bookRentRepository.getRentBookCopiesByReaderId(1)).thenReturn(rentBookCopies1);
        when(bookRentRepository.getRentBookCopiesByReaderId(2)).thenReturn(rentBookCopies2);
        when(readerRepository.findAll()).thenReturn(readerList);
        when(readerRepository.existsByPassport("2000111222")).thenReturn(true);
        when(readerRepository.findByFioLike("test fio")).thenReturn(List.of(readerList.get(0)));
        when(readerRepository.findById(1)).thenReturn(Optional.of(readerList.get(0)));
        when(readerRepository.existsById(1)).thenReturn(true);
        when(readerRepository.deleteById(1)).thenReturn(true);
        when(readerRepository.save(readerList.get(0))).thenReturn(true);
        when(readerRepository.count()).thenReturn(2L);
    }

    @Test
    void existsByPassport() {
        assertTrue(readerService.existsByPassport("2000111222"));
        verify(readerRepository, times(1)).existsByPassport(anyString());
    }

    @Test
    void findByFioLike() {
        List<Reader> test_fio = readerService.findByFioLike("test fio");
        assertNotNull(test_fio);
        assertEquals(List.of(readerList.get(0)), test_fio);

        verify(readerRepository, atLeast(1)).findByFioLike("test fio");
        verify(bookRentRepository, atLeast(1)).getRentBookCopiesByReaderId(anyInt());
    }

    @Test
    void findAll() {
        List<Reader> all = readerService.findAll();
        assertNotNull(all);
        assertEquals(readerList, all);

        verify(bookRentRepository, times(all.size())).getRentBookCopiesByReaderId(anyInt());
    }

    @Test
    void findById() throws EntityNotFoundByIdException {
        Reader byId = readerService.findById(1);
        assertNotNull(byId);
        assertEquals(readerList.get(0), byId);

        verify(readerRepository, times(1)).findById(anyInt());

        Throwable throwable = assertThrows(EntityNotFoundByIdException.class, () -> readerService.findById(3));
        assertNotNull(throwable);
        assertNotEquals("", throwable.getMessage());
    }

    @Test
    void existsById() {
        assertTrue(readerService.existsById(1));
        verify(readerRepository, times(1)).existsById(1);
    }

    @Test
    void deleteAll() {
        readerService.deleteAll();
        verify(readerRepository, times(1)).deleteAll();
    }

    @Test
    void deleteById() {
        assertTrue(readerService.deleteById(1));
        verify(readerRepository, times(1)).deleteById(1);
    }

    @Test
    void save() {
        assertTrue(readerService.save(readerList.get(0)));
        verify(readerRepository, times(1)).save(readerList.get(0));
    }

    @Test
    void count() {
        assertEquals(2, readerService.count());
        verify(readerRepository, times(1)).count();
    }
}