package com.egg.libreria.libreria.servicios;

import com.egg.libreria.libreria.entidades.Autor;
import com.egg.libreria.libreria.excepciones.MiExcepcion;
import com.egg.libreria.libreria.repositorios.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicio {
    
    @Autowired
    AutorRepositorio autorRepositorio;
    
    //Metodos
    
    @Transactional
    public void crearAutor(Autor autor) throws MiExcepcion{
        validar(autor);
        autorRepositorio.save(autor);
    }
    
    @Transactional(readOnly = true)
    public List<Autor> listarAutores(){
        return autorRepositorio.findAll();
    }
    
    @Transactional
    public void modificarAutor(Autor autor){   
        autorRepositorio.save(autor);
    }
    
    @Transactional(readOnly = true)
    public Autor buscarAutor(Autor autor){
        return autorRepositorio.findById(autor.getId()).orElse(null);
    }
    
    @Transactional
    public void eliminar(Autor autor){
        autorRepositorio.delete(autor);
    }
    
    public void validar(Autor autor)throws MiExcepcion{
        if(autor.getNombre().isEmpty() || autor.getNombre() == null){
            throw new MiExcepcion("El nombre no puede estar vacio o ser nulo.");
        }
    }
    
    
}
