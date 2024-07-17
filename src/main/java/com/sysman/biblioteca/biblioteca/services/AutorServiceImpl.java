package com.sysman.biblioteca.biblioteca.services;

import java.util.List;
import java.util.Optional;

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
    public Optional<Autor> findById(Long id) {
        return repository.findById(id);
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

}
