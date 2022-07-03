package com.egg.libreria.libreria.controladores;

import com.egg.libreria.libreria.entidades.Editorial;
import com.egg.libreria.libreria.excepciones.MiExcepcion;
import com.egg.libreria.libreria.servicios.EditorialServicio;
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
@RequestMapping("/editorial")
public class EditorialControlador {
    
    @Autowired
    private EditorialServicio editorialServicio;
    
    @GetMapping("/registrar")
    public String registrar(Editorial editorial, Model model){
        model.addAttribute("year", fechaActual());
        return "editorial_form";
    }
    
    @PostMapping("/registro")
    public String registro(Editorial editorial, Model model){
        try {
            editorialServicio.crearEditorial(editorial);
            model.addAttribute("exito", "La editorial se registro exitosamente");
            model.addAttribute("year", fechaActual());
            return "editorial_form";
        } catch (MiExcepcion ex) {
           model.addAttribute("error", ex.getMessage());
           model.addAttribute("year", fechaActual());
           return "editorial_form";
        }
    }
    
    @GetMapping("/listar")
    public String listar(Model model){
        model.addAttribute("editoriales", editorialServicio.listarEditoriales());
        model.addAttribute("year", fechaActual());
        return "lista_editoriales";
    }
    
    @GetMapping("/editar/{id}")
    public String editar(Editorial editorial, Model model){
        model.addAttribute("editorial", editorialServicio.buscarEditorial(editorial));
        model.addAttribute("year", fechaActual());
        return "editorial_form";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(Editorial editorial, Model model){
        editorialServicio.eliminarEditorial(editorial);
        model.addAttribute("editoriales", editorialServicio.listarEditoriales());
        model.addAttribute("year", fechaActual());
        return "lista_editoriales";
    }
    
    public String fechaActual() {
        SimpleDateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();

        Date dateObj = calendar.getTime();
        String formattedDate = dtf.format(dateObj);
        return formattedDate;
    }
    
    
}
