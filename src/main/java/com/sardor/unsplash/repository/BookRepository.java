package com.sardor.unsplash.repository;

import com.sardor.unsplash.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query(value = "select * from book where name like ?1%", nativeQuery = true)
    List<Book> findAllByName(String name);

    @Query(value = "select * from book b where category_id = ?1", nativeQuery = true)
    List<Book> findAllByCategoryId(Integer category_id);

    @Query(value = "select * from book b where author_id = ?1", nativeQuery = true)
    List<Book> findAllByAuthorId(Integer author_id);

    @Query(value = "select * from book b where b.language like ?1%",nativeQuery = true)
    List<Book> findAllByLanguage(String lang);
}
