package com.sysman.biblioteca.biblioteca.servicesTest;

import com.sysman.biblioteca.biblioteca.entities.Autor;
import com.sysman.biblioteca.biblioteca.entities.Libro;
import com.sysman.biblioteca.biblioteca.repositories.AutorRepository;
import com.sysman.biblioteca.biblioteca.repositories.LibroRepository;
import com.sysman.biblioteca.biblioteca.services.LibroServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LibroServiceTest {
    @Mock
    private LibroRepository libroRepository;

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private LibroServiceImpl libroService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveLibro() {
        Autor autor = new Autor(1L, "Juan", "Alvarez", null);
        Libro libro = new Libro(null, "Libro de Prueba", "Descripción", LocalDate.now(), autor);
        Libro savedLibro = new Libro(1L, "Libro de Prueba", "Descripción", LocalDate.now(), autor);

        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(libroRepository.save(any(Libro.class))).thenReturn(savedLibro);

        Libro result = libroService.save(libro);

        assertNotNull(result.getId());
        assertEquals("Libro de Prueba", result.getTitulo());
        assertEquals("Descripción", result.getDescripcion());
        assertEquals(LocalDate.now(), result.getFechaPublicacion());
        assertEquals(autor, result.getAutor());

        verify(autorRepository, times(1)).findById(1L);
        verify(libroRepository, times(1)).save(libro);
    }

    @Test
    public void testUpdateLibro() {
        Autor autor = new Autor(1L, "Juan", "Alvarez", null);
        Libro libro = new Libro(1L, "Libro de Prueba", "Descripción", LocalDate.now(), autor);

        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(libroRepository.save(libro)).thenReturn(libro);

        Libro updatedLibro = libroService.update(1L, libro);

        assertEquals("Libro de Prueba", updatedLibro.getTitulo());
        assertEquals("Descripción", updatedLibro.getDescripcion());

        verify(libroRepository, times(1)).findById(1L);
        verify(autorRepository, times(1)).findById(1L);
        verify(libroRepository, times(1)).save(libro);
    }

    @Test
    public void testDeleteLibro() {
        Autor autor = new Autor(1L, "Juan", "Alvarez", null);
        Libro libro = new Libro(1L, "Libro de Prueba", "Descripción", LocalDate.now(), autor);

        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));

        libroService.deleteById(1L);

        verify(libroRepository, times(1)).findById(1L);
        verify(libroRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindById() {
        Autor autor = new Autor(1L, "Juan", "Alvarez", null);
        Libro libro = new Libro(1L, "Libro de Prueba", "Descripción", LocalDate.now(), autor);

        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));

        Optional<Libro> foundLibro = libroService.findById(1L);

        assertEquals("Libro de Prueba", foundLibro.get().getTitulo());

        verify(libroRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindAll() {
        Autor autor = new Autor(1L, "Juan", "Alvarez", null);
        Libro libro1 = new Libro(1L, "Libro de Prueba 1", "Descripción 1", LocalDate.now(), autor);
        Libro libro2 = new Libro(2L, "Libro de Prueba 2", "Descripción 2", LocalDate.now(), autor);

        when(libroRepository.findAll()).thenReturn(Arrays.asList(libro1, libro2));

        List<Libro> libros = libroService.findAll();

        assertEquals(2, libros.size());
        assertEquals("Libro de Prueba 1", libros.get(0).getTitulo());
        assertEquals("Libro de Prueba 2", libros.get(1).getTitulo());

        verify(libroRepository, times(1)).findAll();
    }

}
