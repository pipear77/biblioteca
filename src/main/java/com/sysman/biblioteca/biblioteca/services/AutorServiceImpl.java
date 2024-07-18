package com.sysman.biblioteca.biblioteca.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysman.biblioteca.biblioteca.entities.Autor;
import com.sysman.biblioteca.biblioteca.repositories.AutorRepository;

@Service
public class AutorServiceImpl implements AutorService {

    @Autowired
    private AutorRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<Autor> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Autor findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El autor no existe."));
    }

    @Transactional
    @Override
    public Autor save(Autor autor) {
        return repository.save(autor);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Autor update(Long id, Autor autor) {
        Autor existingAutor = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El autor no existe."));

        existingAutor.setNombre(autor.getNombre());
        existingAutor.setApellido(autor.getApellido());

        return repository.save(existingAutor);
    }

}
