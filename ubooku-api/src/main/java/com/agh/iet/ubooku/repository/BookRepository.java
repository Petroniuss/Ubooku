package com.agh.iet.ubooku.repository;

import com.agh.iet.ubooku.model.book.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * In order to instantiate correct beans we have to first generate some classes (QBooK)
 * we do this via running correct maven command (generate-sources) but
 * we can use anyone after this (since maven executes them sequentially
 */
@Repository
public interface BookRepository extends
        MongoRepository<Book, String>, QuerydslPredicateExecutor<Book> {

}
