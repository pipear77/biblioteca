package com.sysman.biblioteca.biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sysman.biblioteca.biblioteca.entities.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long> {

}
