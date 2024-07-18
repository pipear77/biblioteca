package com.sysman.biblioteca.biblioteca.services;

import java.util.List;
import java.util.Optional;

import com.sysman.biblioteca.biblioteca.entities.Autor;

public interface AutorService {

    List<Autor> findAll();

    Autor save(Autor autor);

    Autor update(Long id, Autor autor);

    void deleteById(Long id);

    Autor findById(Long id);
}
