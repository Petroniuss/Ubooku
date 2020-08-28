package com.agh.iet.ubooku.service;


import com.agh.iet.ubooku.model.book.Book;
import com.agh.iet.ubooku.model.book.QBook;
import com.agh.iet.ubooku.repository.BookRepository;
import com.querydsl.core.types.Predicate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;

//@RunWith(MockitoJUnitRunner.class) this is equivalent to @Before
public class BookServiceTest {

    @Mock
    BookRepository bookRepository;

    @Mock
    StorageService storageService;

    @InjectMocks
    BookService bookService;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loadBooksByCategoriesReturnsEmptyListWhen0CategoriesSupplied() {
        when(bookRepository.findAll(QBook.book.author.isNotEmpty()))
                .thenReturn(Collections.emptyList());

        final List<Book> returnedCollection = bookService.loadBooksByCategories(Collections.emptyList());

        Assert.assertThat(returnedCollection, is(notNullValue()));

        Assert.assertThat(returnedCollection, is(empty()));
    }

    @Test(expected = NullPointerException.class)
    public void loadBooksByCategoriesWhenNull() {
        when(bookRepository.findAll((Predicate) null)).thenThrow(new NullPointerException());

        final List<Book> returnedCollection = bookService.loadBooksByCategories(null);
    }
}
