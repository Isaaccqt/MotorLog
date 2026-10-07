package com.isaac.motorlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.isaac.motorlog.model.Vehiculo;
import com.isaac.motorlog.repository.VehiculoRepository;

@Controller
public class VehiculoController {

    @Autowired
    private VehiculoRepository vehiculoRepository;

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
}
