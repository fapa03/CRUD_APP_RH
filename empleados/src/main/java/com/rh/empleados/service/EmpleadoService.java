package com.rh.empleados.service;

import com.rh.empleados.dto.EmpleadoDTO;
import com.rh.empleados.model.Empleado;
import com.rh.empleados.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public List<Empleado> listarActivos() {
        return empleadoRepository.findByActivoTrue();
    }

    public Empleado crear(EmpleadoDTO dto) {
        Empleado empleado = new Empleado();
        empleado.setNombre(dto.getNombre());
        empleado.setApellido(dto.getApellido());
        empleado.setEmail(dto.getEmail());
        empleado.setPuesto(dto.getPuesto());
        empleado.setSalarioMensual(dto.getSalarioMensual());
        empleado.setFechaIngreso(dto.getFechaIngreso());
        return empleadoRepository.save(empleado);
    }

    public Empleado actualizar(Long id, EmpleadoDTO dto) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + id));

        empleado.setNombre(dto.getNombre());
        empleado.setApellido(dto.getApellido());
        empleado.setEmail(dto.getEmail());
        empleado.setPuesto(dto.getPuesto());
        empleado.setSalarioMensual(dto.getSalarioMensual());
        empleado.setFechaIngreso(dto.getFechaIngreso());
        return empleadoRepository.save(empleado);
    }

    public void eliminar(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + id));
        empleado.setActivo(false);
        empleadoRepository.save(empleado);
    }
}