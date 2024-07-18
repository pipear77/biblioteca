package com.sysman.biblioteca.biblioteca.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sysman.biblioteca.biblioteca.entities.Libro;
import com.sysman.biblioteca.biblioteca.services.LibroService;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Libro>> findAll() {
        List<Libro> libros = service.findAll();
        return ResponseEntity.ok(libros);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Libro> optionalLibro = service.findById(id);
        if (optionalLibro.isPresent()) {
            return ResponseEntity.ok(optionalLibro.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Libro no encontrado con id: " + id);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Libro libro) {
        try {
            Libro savedLibro = service.save(libro);
            return ResponseEntity.ok(savedLibro);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Libro libro) {
        try {
            Libro updatedLibro = service.update(id, libro);
            return ResponseEntity.ok(updatedLibro);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }



}
