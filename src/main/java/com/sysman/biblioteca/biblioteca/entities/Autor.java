package com.sysman.biblioteca.biblioteca.entities;

import java.util.Set;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "AUTORES")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 20)
    private String nombre;

    @Column(name = "APELLIDO", nullable = false, length = 20)
    private String apellido;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Libro> libros = new HashSet<>();

    public void addLibro(Libro libro) {
        libros.add(libro);
        libro.setAutor(this);
    }

    public void removeLibro(Libro libro) {
        libros.remove(libro);
        libro.setAutor(null);
    }
}
