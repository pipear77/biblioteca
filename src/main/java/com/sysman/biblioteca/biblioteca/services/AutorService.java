package com.sysman.biblioteca.biblioteca.services;

import java.util.List;
import java.util.Optional;

import com.sysman.biblioteca.biblioteca.entities.Autor;

public interface AutorService {

    List<Autor> findAll();

    Optional<Autor> findById(Long id);

    Autor save(Autor autor);

    void deleteById(Long id);
}
