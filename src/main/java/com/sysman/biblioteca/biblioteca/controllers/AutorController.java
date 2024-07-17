package com.sysman.biblioteca.biblioteca.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sysman.biblioteca.biblioteca.entities.Autor;
import com.sysman.biblioteca.biblioteca.services.AutorService;

@RestController
@RequestMapping("/api/autores")
public class AutorController {

    @Autowired
    private AutorService service;

    @GetMapping
    public List<Autor> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Long id) {
        Optional<Autor> autorOptional = service.findById(id);
        if (autorOptional.isPresent()) {
            return ResponseEntity.ok(autorOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Autor> create(@RequestBody Autor autor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(autor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Autor> update(@PathVariable Long id, @RequestBody Autor updatedAutor) {
        updatedAutor.setId(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(updatedAutor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
