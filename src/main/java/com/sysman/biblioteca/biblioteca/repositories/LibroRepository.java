package com.sysman.biblioteca.biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sysman.biblioteca.biblioteca.entities.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {

}
