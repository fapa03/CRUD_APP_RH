package com.rh.empleados.controller;

import com.rh.empleados.dto.EmpleadoDTO;
import com.rh.empleados.dto.EmpleadoDetalleDTO;
import com.rh.empleados.model.Empleado;
import com.rh.empleados.service.EmpleadoService;
import com.rh.empleados.service.PrestacionesService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/empleados")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EmpleadoController {

    private final PrestacionesService prestacionesService;

    private final EmpleadoService empleadoService;

    @GetMapping
    public ResponseEntity<List<Empleado>> listar() {
        return ResponseEntity.ok(empleadoService.listarActivos());
    }

    @PostMapping
    public ResponseEntity<Empleado> crear(@Valid @RequestBody EmpleadoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizar(@PathVariable Long id, @Valid @RequestBody EmpleadoDTO dto) {
        return ResponseEntity.ok(empleadoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        empleadoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/prestaciones")
    public ResponseEntity<EmpleadoDetalleDTO> prestaciones(@PathVariable Long id) {
    return ResponseEntity.ok(prestacionesService.calcular(id));
}
}