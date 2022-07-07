package com.egg.libreria.libreria.servicios;

import com.egg.libreria.libreria.entidades.Libro;
import com.egg.libreria.libreria.excepciones.MiExcepcion;
import com.egg.libreria.libreria.repositorios.AutorRepositorio;
import com.egg.libreria.libreria.repositorios.EditorialRepositorio;
import com.egg.libreria.libreria.repositorios.LibroRepositorio;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    

    //Metodos
    @Transactional
    public void crearLibro(Libro libro) throws MiExcepcion {
        validar(libro);
        libroRepositorio.save(libro);
    }

    @Transactional(readOnly = true)
    public List<Libro> listarLibros() {
        return libroRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public Libro buscarLibro(Libro libro) {
        libro.setAlta(new Date());
        return libroRepositorio.findById(libro.getId()).orElse(null);
    }
    
    @Transactional
    public void eliminarLibro(Libro libro){
        libroRepositorio.delete(libro);
    }
    
    public void validar(Libro libro)throws MiExcepcion{
        if(libro.getIsbn() == null){
            throw new MiExcepcion("El isbn no puede ser nulo.");
        }
        if(libro.getTitulo() == null || libro.getTitulo().isEmpty()){
            throw new MiExcepcion("El titulo no puede ser nulo o estar vacio.");
        }
        if(libro.getEjemplares() == null){
            throw new MiExcepcion("La cantidad de ejemplares no debe ser nulo.");
        }
        if(libro.getAutor() == null){
            throw new MiExcepcion("El autor no debe ser nulo.");
        }
        if(libro.getEditorial() == null){
            throw new MiExcepcion("La editorial no debe ser nula.");
        }
    }
    
    @Transactional(readOnly = true)
    public List<Libro>buscarLibroPorClave(String palabraClave){
        return libroRepositorio.findAll(palabraClave);
    }

}
