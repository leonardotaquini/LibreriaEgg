package com.egg.libreria.libreria.controladores;

import com.egg.libreria.libreria.entidades.Autor;
import com.egg.libreria.libreria.entidades.Editorial;
import com.egg.libreria.libreria.entidades.Libro;
import com.egg.libreria.libreria.excepciones.MiExcepcion;
import com.egg.libreria.libreria.servicios.AutorServicio;
import com.egg.libreria.libreria.servicios.EditorialServicio;
import com.egg.libreria.libreria.servicios.LibroServicio;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    private LibroServicio libroServicio;

    @Autowired
    private EditorialServicio editorialServicio;

    @Autowired
    private AutorServicio autorServicio;

    @GetMapping("/registrar")
    public String registrar(Model model, Libro libro) {
        mostrarAutoresYEditoriales(model);
        model.addAttribute("year", fechaActual());
        return "libro_form";
    }

    @PostMapping("/registro")
    public String registro(Libro libro, Model model) {
        try {
            libro.setAlta(new Date());
            libroServicio.crearLibro(libro);
            model.addAttribute("exito", "Libro registrado exitosamente");
            model.addAttribute("year", fechaActual());
            return "libro_form";
        } catch (MiExcepcion ex) {
            mostrarAutoresYEditoriales(model);
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("year", fechaActual());
            return "libro_form";
        }
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("libros", libroServicio.listarLibros());
        model.addAttribute("year", fechaActual());
        return "lista_libros";
    }

    @GetMapping("/editar/{id}")
    public String editar(Libro libro, Model model) {
        libro = libroServicio.buscarLibro(libro);
        mostrarAutoresYEditoriales(model);
        model.addAttribute("libro", libro);
        model.addAttribute("year", fechaActual());

        return "libro_form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(Libro libro, Model model) {
        libroServicio.eliminarLibro(libro);
        model.addAttribute("libros", libroServicio.listarLibros());
        model.addAttribute("year", fechaActual());
        return "lista_libros";
    }
    
    @GetMapping("/buscar")
    public String buscar(@RequestParam("palabraClave")String palabraClave, Model model){
        
        model.addAttribute("libros",libroServicio.buscarLibroPorClave(palabraClave));
        model.addAttribute("year", fechaActual());
        return"lista_libros";
    }

    public void mostrarAutoresYEditoriales(Model model) {
        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        model.addAttribute("autores", autores);
        model.addAttribute("editoriales", editoriales);
    }

    public String fechaActual() {
        SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();

        Date dateObj = calendar.getTime();
        String formattedDate = dtf.format(dateObj);
        return formattedDate;
    }

}
