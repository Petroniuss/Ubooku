package com.agh.iet.ubooku.service;

import com.agh.iet.ubooku.exception.book.NotFoundBookWithGivenIdException;
import com.agh.iet.ubooku.model.book.Book;
import com.agh.iet.ubooku.model.book.Categories;
import com.agh.iet.ubooku.model.book.QBook;
import com.agh.iet.ubooku.payload.BookUploadRequest;
import com.agh.iet.ubooku.repository.BookRepository;
import com.agh.iet.ubooku.service.storage.StorageService;
import com.google.cloud.storage.BlobId;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.agh.iet.ubooku.service.StorageService.MIME_IMAGE_PNG;


/*
TODO: GENERATE THUMBNAILS FOR PDFs DURING UPLOAD AND STORE THEM TOGETHER   --> this might shorten loading time.
 */

@Service
public class BookService {

    private final StorageService storageService;
    private final BookRepository bookRepository;

    @Autowired
    public BookService(StorageService storageService, BookRepository bookRepository) {
        this.storageService = storageService;
        this.bookRepository = bookRepository;
    }

    public Book saveBook(BookUploadRequest bookUploadRequest, MultipartFile file) throws IOException {
        String id = bookUploadRequest.getTitle() + "-" + bookUploadRequest.getAuthor() + ":" + new Date().getTime();
        
        BlobId blobId =
                storageService
                        .store(file, id);

        BlobId imgBlobId =
                storageService
                        .store(generateThumbnailFromPdf(file.getBytes()), id + "-thumbnail", MIME_IMAGE_PNG);

        final Book book = Book.builder()
                .title(bookUploadRequest.getTitle())
                .author(bookUploadRequest.getAuthor())
                .categories(bookUploadRequest.getCategories())
                .id(blobId.getName())
                .blobId(blobId)
                .thumbnailBlobId(imgBlobId)
                .build();

        return bookRepository.save(book);
    }


    public List<Book> loadAllBooks() {
        return bookRepository.findAll();
    }


    public Optional<Book> loadBook(String bookId) {
        return bookRepository.findById(bookId);
    }

    public byte[] loadBookPdf(String bookId) {
        BlobId blobId = bookRepository.findById(bookId).orElseThrow(
                () -> new NotFoundBookWithGivenIdException(bookId)
        ).getBlobId();

        return storageService.load(blobId);
    }

    public List<Book> loadBooksBySearchQuery(String query) {

        Predicate predicate = QBook.book.author.containsIgnoreCase(query)
                .or(QBook.book.title.containsIgnoreCase(query));
        return (List<Book>) bookRepository.findAll(predicate);
    }

    public List<Book> loadBooksByCategories(List<Categories> categories) {
        QBook qBook = QBook.book;

        Predicate predicate = categories.stream()
                .map(qBook.categories::contains)
                .reduce(qBook.categories.isNotEmpty(), BooleanExpression::and);

        return (List<Book>) bookRepository.findAll(predicate);
    }

    public byte[] getThumbnailImage(String bookId) {
        BlobId blobId = bookRepository.findById(bookId).orElseThrow(
                () -> new NotFoundBookWithGivenIdException(bookId)
        ).getThumbnailBlobId();

        return storageService.load(blobId);
    }

    private byte[] generateThumbnailFromPdf(byte[] bytes) throws IOException {
        PDDocument document = PDDocument.load(bytes);

        PDFRenderer pdfRenderer = new PDFRenderer(document);

        BufferedImage bufferedImage = pdfRenderer.renderImage(0);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);

        document.close();

        return byteArrayOutputStream.toByteArray();
    }

}
