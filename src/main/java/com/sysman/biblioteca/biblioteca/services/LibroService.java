package com.sysman.biblioteca.biblioteca.services;

import java.util.List;

import java.util.Optional;

import com.sysman.biblioteca.biblioteca.entities.Autor;
import com.sysman.biblioteca.biblioteca.entities.Libro;

public interface LibroService {

    List<Libro> findAll();

    Optional<Libro> findById(Long id);

    Libro save(Libro libro);

    Libro update(Long id, Libro libro);

    void deleteById(Long id);
}
