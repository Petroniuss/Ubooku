package com.agh.iet.ubooku.controller;

import com.agh.iet.ubooku.exception.book.NotFoundBookWithGivenIdException;
import com.agh.iet.ubooku.model.book.Book;
import com.agh.iet.ubooku.model.book.Categories;
import com.agh.iet.ubooku.payload.ApiResponse;
import com.agh.iet.ubooku.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController  {


    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllBooks() {
        return ResponseEntity.ok(bookService.loadAllBooks());
    }

    @GetMapping("/{bookId}/get")
    public ResponseEntity<?> getBook(@PathVariable @NotNull @NotEmpty String bookId) {
        final Book book = bookService.loadBook(bookId).orElseThrow(() -> new NotFoundBookWithGivenIdException(bookId));
        return ResponseEntity.ok(book);
    }

    @GetMapping(value = "/{bookId}/get-pdf",
                produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> getBookPdf(@PathVariable @NotNull @NotEmpty String bookId) {
        return ResponseEntity.ok(bookService.loadBookPdf(bookId));
    }

    @GetMapping(value = "/{bookId}/get-thumbnail",
                produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> getBookThumbnail(@PathVariable @NotNull @NotEmpty String bookId) throws IOException {
        return ResponseEntity.ok(bookService.getThumbnailImage(bookId));
    }

    @GetMapping(value = "/search")
    public ResponseEntity<?> searchBooks(@RequestParam("query") String query) {
        return ResponseEntity.ok(bookService.loadBooksBySearchQuery(query));
    }

    @GetMapping(value = "/search/categories")
    public ResponseEntity<?> searchBooksWithCategories(@RequestParam("cat") List<String> categories) {
        return ResponseEntity.ok(
                bookService
                        .loadBooksByCategories(
                                categories
                                        .stream()
                                        .map(Categories::valueOf)
                                        .collect(Collectors.toList())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleMissingRequestParameterException(Exception ex) {
        return ResponseEntity.badRequest().body(new ApiResponse(false, ex.getMessage()));
    }

    @ExceptionHandler(NotFoundBookWithGivenIdException.class)
    public ResponseEntity<?> handleNotFoundBookWithGivenIdException(NotFoundBookWithGivenIdException ex) {
        return ResponseEntity.badRequest().body(new ApiResponse(false, ex.getMessage()));
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleIOException(IOException exception) {
        return new ResponseEntity<>(new ApiResponse(false, "Failed to execute due to IOException: " + exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
