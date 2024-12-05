package com.example.exemploteste.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.exemploteste.model.Book;

// Interface que estende JpaRepository para operações de banco de dados em relação à entidade Book
public interface BookRepository extends JpaRepository<Book, Long> {
}