package com.sysman.biblioteca.biblioteca.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "LIBROS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, insertable = false)
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    @Column(name = "TITULO", nullable = false, length = 40)
    private String titulo;

    @Column(name = "DESCRIPCION", length = 200)
    private String descripcion;

    @Column(name = "FECHA_PUBLICACION")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Past(message = "La fecha de publicación debe ser en el pasado")
    private LocalDate fechaPublicacion;

    @NotNull(message = "El autor es obligatorio")
    @ManyToOne
    @JoinColumn(name = "ID_AUTOR", referencedColumnName = "ID", nullable = false)
    private Autor autor;
}
