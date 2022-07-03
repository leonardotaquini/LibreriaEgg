package com.egg.libreria.libreria.servicios;

import com.egg.libreria.libreria.entidades.Editorial;
import com.egg.libreria.libreria.excepciones.MiExcepcion;
import com.egg.libreria.libreria.repositorios.EditorialRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio {

    @Autowired
    EditorialRepositorio editorialRepositorio;

    //Metodos
    @Transactional
    public void crearEditorial(Editorial editorial) throws MiExcepcion {
        validar(editorial);
        editorialRepositorio.save(editorial);
    }

    @Transactional(readOnly = true)
    public List<Editorial> listarEditoriales() {
        return editorialRepositorio.findAll();
    }

    @Transactional
    public void modificarEditorial(Editorial editorial) {
        editorialRepositorio.save(editorial);
    }

    @Transactional(readOnly = true)
    public Editorial buscarEditorial(Editorial editorial){
        return editorialRepositorio.findById(editorial.getId()).orElse(null);
    }
    
    @Transactional
    public void eliminarEditorial(Editorial editorial){
        editorialRepositorio.delete(editorial);
    }
    
    public void validar(Editorial editorial)throws MiExcepcion{
        if(editorial.getNombre().isEmpty() || editorial.getNombre() == null){
            throw new MiExcepcion("El nombre no puede estar vacio o ser nulo.");
        }
    }
}
