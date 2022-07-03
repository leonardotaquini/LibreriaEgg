package com.egg.libreria.libreria.controladores;

import com.egg.libreria.libreria.entidades.Autor;
import com.egg.libreria.libreria.excepciones.MiExcepcion;
import com.egg.libreria.libreria.servicios.AutorServicio;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/autor")
public class AutorControlador {

    @Autowired
    private AutorServicio autorServicio;

    @GetMapping("/registrar")
    public String registrar(Autor autor, Model model) {
        model.addAttribute("year", fechaActual());
        return "autor_form";
    }

    @PostMapping("/registro")
    public String registro(Autor autor, Model model) {
        try {
            autorServicio.crearAutor(autor);
            model.addAttribute("exito", "El autor se registro exitosamente");
            return "autor_form";
        } catch (MiExcepcion ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("year", fechaActual());
            return "autor_form";
        }
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("autores", autorServicio.listarAutores());
        model.addAttribute("year", fechaActual());
        return "lista_autores";
    }

    @GetMapping("/editar/{id}")
    public String editar(Model model, Autor autor) {
        model.addAttribute("autor", autorServicio.buscarAutor(autor));
        model.addAttribute("year", fechaActual());
        return "autor_form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(Autor autor, Model model) {
        autorServicio.eliminar(autor);
        model.addAttribute("autores", autorServicio.listarAutores());
        model.addAttribute("year", fechaActual());
        return "lista_autores";
    }

    public String fechaActual() {
        SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();

        Date dateObj = calendar.getTime();
        String formattedDate = dtf.format(dateObj);
        return formattedDate;
    }

}
