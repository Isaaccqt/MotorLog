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

    // --- RUSTAS DE VEHÍCULOS ---

    @GetMapping("/garaje")
    public String mostrarGaraje(Model model) {
        model.addAttribute("vehiculos", vehiculoRepository.findAll());
        model.addAttribute("nuevoVehiculo", new Vehiculo());
        return "garaje";
    }

    @PostMapping("/guardarVehiculo")
    public String guardarVehiculo(@ModelAttribute Vehiculo vehiculo) {
        vehiculoRepository.save(vehiculo);
        return "redirect:/garaje";
    }

    // HU 3.1: Borrar vehículo en cascada
    @GetMapping("/vehiculo/borrar/{id}")
    public String borrarVehiculo(@PathVariable Long id) {
        vehiculoRepository.deleteById(id);
        return "redirect:/garaje";
    }

    // HU 3.2: Cargar vista de edición
    @GetMapping("/vehiculo/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Vehiculo vehiculo = vehiculoRepository.findById(id).orElseThrow();
        model.addAttribute("vehiculo", vehiculo);
        return "editar-vehiculo";
    }

    // HU 3.2: Guardar cambios de edición
    @PostMapping("/vehiculo/actualizar")
    public String actualizarVehiculo(@ModelAttribute Vehiculo vehiculo) {
        vehiculoRepository.save(vehiculo); // Si el objeto lleva ID, Spring Boot hace UPDATE en lugar de INSERT
        return "redirect:/garaje";
    }

    // --- RUTAS DE INTERVENCIONES ---

    @GetMapping("/vehiculo/{id}")
    public String verHistorial(@PathVariable Long id, Model model) {
        Vehiculo vehiculo = vehiculoRepository.findById(id).orElseThrow();
        model.addAttribute("vehiculo", vehiculo);
        model.addAttribute("intervenciones", intervencionRepository.findByVehiculoIdOrderByFechaDesc(id));
        model.addAttribute("nuevaIntervencion", new Intervencion());
        return "historial";
    }

    @PostMapping("/guardarIntervencion/{vehiculoId}")
    public String guardarIntervencion(@PathVariable Long vehiculoId, @ModelAttribute Intervencion intervencion) {
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId).orElseThrow();
        intervencion.setVehiculo(vehiculo);
        intervencionRepository.save(intervencion);
        return "redirect:/vehiculo/" + vehiculoId;
    }

    // HU 3.3: Borrar una intervención individual
    @GetMapping("/intervencion/borrar/{id}/{vehiculoId}")
    public String borrarIntervencion(@PathVariable Long id, @PathVariable Long vehiculoId) {
        intervencionRepository.deleteById(id);
        return "redirect:/vehiculo/" + vehiculoId;
    }
}