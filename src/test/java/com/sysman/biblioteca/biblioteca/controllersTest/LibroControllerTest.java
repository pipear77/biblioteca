package com.sysman.biblioteca.biblioteca.controllersTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sysman.biblioteca.biblioteca.controllers.LibroController;
import com.sysman.biblioteca.biblioteca.entities.Autor;
import com.sysman.biblioteca.biblioteca.entities.Libro;
import com.sysman.biblioteca.biblioteca.services.LibroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class LibroControllerTest {
    @Mock
    private LibroService libroService;

    @InjectMocks
    private LibroController libroController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(libroController).build();
    }

    @Test
    public void testCreateLibro() throws Exception {
        Autor autor = new Autor(1L, "John", "Doe", Collections.emptySet());
        Libro libro = new Libro(null, "Caballo de Troya", "Novela de ficción", LocalDate.of(1985, 5, 12), autor);
        Libro savedLibro = new Libro(1L, "Caballo de Troya", "Novela de ficción", LocalDate.of(1985, 5, 12), autor);

        when(libroService.save(any(Libro.class))).thenReturn(savedLibro);

        mockMvc.perform(post("/api/libros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(libro)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.titulo").value("Caballo de Troya"))
                .andExpect(jsonPath("$.descripcion").value("Novela de ficción"));
    }

    @Test
    public void testUpdateLibro() throws Exception {
        Autor autor = new Autor(1L, "Juan", "Alvarez", null);
        Libro libro = new Libro(1L, "Libro Actualizado", "Descripción Actualizada", LocalDate.now(), autor);

        when(libroService.update(anyLong(), any(Libro.class))).thenReturn(libro);

        mockMvc.perform(put("/api/libros/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(libro)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Libro Actualizado"))
                .andExpect(jsonPath("$.descripcion").value("Descripción Actualizada"));

        verify(libroService, times(1)).update(anyLong(), any(Libro.class));
    }

    @Test
    public void testDeleteLibro() throws Exception {
        doNothing().when(libroService).deleteById(anyLong());

        mockMvc.perform(delete("/api/libros/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(libroService, times(1)).deleteById(anyLong());
    }

    @Test
    public void testFindById() throws Exception {
        Autor autor = new Autor(1L, "Juan", "Alvarez", null);
        Libro libro = new Libro(1L, "Libro de Prueba", "Descripción", LocalDate.now(), autor);

        when(libroService.findById(anyLong())).thenReturn(Optional.of(libro));

        mockMvc.perform(get("/api/libros/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Libro de Prueba"))
                .andExpect(jsonPath("$.descripcion").value("Descripción"));

        verify(libroService, times(1)).findById(anyLong());
    }

    @Test
    public void testFindAllLibros() throws Exception {
        // Mock de datos
        Libro libro1 = new Libro(1L, "Libro 1", "Descripción 1", LocalDate.now(), null);
        Libro libro2 = new Libro(2L, "Libro 2", "Descripción 2", LocalDate.now(), null);
        List<Libro> libros = Arrays.asList(libro1, libro2);

        // Configuración del servicio mock
        when(libroService.findAll()).thenReturn(libros);

        // Realizar la petición GET
        mockMvc.perform(get("/api/libros")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].titulo").value("Libro 1"))
                .andExpect(jsonPath("$[0].descripcion").value("Descripción 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].titulo").value("Libro 2"))
                .andExpect(jsonPath("$[1].descripcion").value("Descripción 2"));
    }

    private static String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
