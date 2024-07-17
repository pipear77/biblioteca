package com.sysman.biblioteca.biblioteca.services;

import java.util.List;
import java.util.Optional;

import org.aspectj.bridge.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysman.biblioteca.biblioteca.entities.Autor;
import com.sysman.biblioteca.biblioteca.entities.Libro;
import com.sysman.biblioteca.biblioteca.repositories.AutorRepository;
import com.sysman.biblioteca.biblioteca.repositories.LibroRepository;

@Service
public class LibroServiceImpl implements LibroService {

    @Autowired
    private LibroRepository repository;

    @Autowired
    private AutorRepository repositoryAutor;

    @Override
    @Transactional(readOnly = true)
    public List<Libro> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Libro> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Libro save(Libro libro) {
        if (libro.getAutor() == null) {
            throw new IllegalArgumentException("El libro debe tener un autor.");
        }

        Autor autor = repositoryAutor.findById(libro.getAutor().getId())
                .orElseThrow(() -> new IllegalArgumentException("El autor no existe."));

        libro.setAutor(autor);
        return repository.save(libro);
    }

    @Override
    public Libro update(Long id, Libro libro) {
        Libro existingLibro = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El libro no existe."));

        if (libro.getAutor() != null && libro.getAutor().getId() != null) {
            Autor autor = repositoryAutor.findById(libro.getAutor().getId())
                    .orElseThrow(() -> new IllegalArgumentException("El autor no existe."));
            existingLibro.setAutor(autor);
        }

        existingLibro.setTitulo(libro.getTitulo());
        existingLibro.setDescripcion(libro.getDescripcion());
        existingLibro.setFechaPublicacion(libro.getFechaPublicacion());

        return repository.save(existingLibro);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
