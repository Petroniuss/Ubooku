package com.agh.iet.ubooku.controller;

import com.agh.iet.ubooku.model.book.Book;
import com.agh.iet.ubooku.payload.ApiResponse;
import com.agh.iet.ubooku.payload.BookUploadRequest;
import com.agh.iet.ubooku.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;


@RestController
@RequestMapping("/upload")
public class FileUploadController {


    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    private static final String MIME_APPLICATION_PDF = "application/pdf";

    private final BookService bookService;

    @Autowired
    public FileUploadController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping(consumes = {"multipart/form-data"}, value = "/book")
    public ResponseEntity<?> uploadBook(@RequestPart("file") @NotNull MultipartFile file,
                                        @RequestPart("details") @NotNull @Valid BookUploadRequest details) throws IOException {

        if (file.getContentType() == null || !file.getContentType().equals(MIME_APPLICATION_PDF)) {
            throw new InvalidFileFormatException(file.getContentType(), MIME_APPLICATION_PDF);
        }

        final Book book = bookService.saveBook(details, file);

        return new ResponseEntity<>(new ApiResponse(true, "Successfully uploaded!"), HttpStatus.ACCEPTED);
    }


    @ExceptionHandler(InvalidFileFormatException.class)
    public ResponseEntity<?> handleInvalidFileFormatException(InvalidFileFormatException ex) {
        return ResponseEntity.badRequest().body(new ApiResponse(false, ex.getMessage()));
    }

    private class InvalidFileFormatException extends RuntimeException {
        InvalidFileFormatException(String actualFormat, String desiredFormat) {
            super("Uploaded file must be " + desiredFormat + ", not " + actualFormat);
        }
    }

}
