package com.isaac.motorlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.isaac.motorlog.model.Intervencion;
import com.isaac.motorlog.model.Vehiculo;
import com.isaac.motorlog.repository.IntervencionRepository;
import com.isaac.motorlog.repository.VehiculoRepository;

@Controller
public class VehiculoController {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private IntervencionRepository intervencionRepository;

    // Esta ruta mostrará la pantalla principal (El Garaje) con la lista de coches
    @GetMapping("/garaje")
    public String mostrarGaraje(Model model) {
        model.addAttribute("vehiculos", vehiculoRepository.findAll());
        model.addAttribute("nuevoVehiculo", new Vehiculo()); // Preparamos un coche vacío para el formulario
        return "garaje"; // Esto buscará un archivo llamado garaje.html
    }

    // Esta ruta recibe los datos del formulario y guarda el coche en MySQL
    @PostMapping("/guardarVehiculo")
    public String guardarVehiculo(Vehiculo vehiculo) {
        vehiculoRepository.save(vehiculo);
        return "redirect:/garaje"; // Recarga la página para ver el coche nuevo
    }

    // HU 2.2: Ver el historial de un coche específico
    @GetMapping("/vehiculo/{id}")
    public String verHistorial(@PathVariable Long id, Model model) {
        // Buscamos el coche por su ID
        Vehiculo vehiculo = vehiculoRepository.findById(id).orElseThrow();
        
        model.addAttribute("vehiculo", vehiculo);
        model.addAttribute("intervenciones", intervencionRepository.findByVehiculoIdOrderByFechaDesc(id));
        model.addAttribute("nuevaIntervencion", new Intervencion()); // Para el formulario
        
        return "historial"; // Crearemos historial.html a continuación
    }

    // HU 2.1: Guardar una nueva intervención (ej. cambio de ruedas)
    @PostMapping("/guardarIntervencion/{vehiculoId}")
    public String guardarIntervencion(@PathVariable Long vehiculoId, @ModelAttribute Intervencion intervencion) {
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId).orElseThrow();
        intervencion.setVehiculo(vehiculo); // Le asignamos el coche a la intervención
        intervencionRepository.save(intervencion);
        
        return "redirect:/vehiculo/" + vehiculoId; // Recargamos la vista del historial
    }
}
