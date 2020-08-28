package com.agh.iet.ubooku.exception.book;

public class NotFoundBookWithGivenIdException extends RuntimeException {
    public NotFoundBookWithGivenIdException(String bookId) {
        super("Not found book with given id: " + bookId);
    }
}
