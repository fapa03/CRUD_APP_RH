package com.rh.empleados.service;

import com.rh.empleados.dto.EmpleadoDetalleDTO;
import com.rh.empleados.model.Empleado;
import com.rh.empleados.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class PrestacionesService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoDetalleDTO calcular(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + id));

        LocalDate hoy = LocalDate.now();
        LocalDate fechaIngreso = empleado.getFechaIngreso();

        // Antigüedad en años completos
        int antiguedadAnios = (int) ChronoUnit.YEARS.between(fechaIngreso, hoy);

        // Salario diario
        BigDecimal salarioDiario = empleado.getSalarioMensual()
                .divide(BigDecimal.valueOf(30), 2, RoundingMode.HALF_UP);

        // Aguinaldo
        BigDecimal aguinaldo = calcularAguinaldo(salarioDiario, fechaIngreso, hoy);

        // Días de vacaciones según antigüedad
        int diasVacaciones = calcularDiasVacaciones(antiguedadAnios);

        // Prima vacacional
        BigDecimal primaVacacional = salarioDiario
                .multiply(BigDecimal.valueOf(diasVacaciones))
                .multiply(BigDecimal.valueOf(0.25))
                .setScale(2, RoundingMode.HALF_UP);

        // Armar respuesta
        EmpleadoDetalleDTO detalle = new EmpleadoDetalleDTO();
        detalle.setId(empleado.getId());
        detalle.setNombre(empleado.getNombre());
        detalle.setApellido(empleado.getApellido());
        detalle.setEmail(empleado.getEmail());
        detalle.setPuesto(empleado.getPuesto());
        detalle.setSalarioMensual(empleado.getSalarioMensual());
        detalle.setFechaIngreso(fechaIngreso);
        detalle.setSalarioDiario(salarioDiario);
        detalle.setAntiguedadAnios(antiguedadAnios);
        detalle.setDiasVacaciones(diasVacaciones);
        detalle.setAguinaldo(aguinaldo);
        detalle.setPrimaVacacional(primaVacacional);

        return detalle;
    }

    private BigDecimal calcularAguinaldo(BigDecimal salarioDiario, LocalDate fechaIngreso, LocalDate hoy) {
        // Inicio del año en curso
        LocalDate inicioAnio = LocalDate.of(hoy.getYear(), 1, 1);

        // Fecha desde la que se cuenta (la mayor entre fecha ingreso e inicio de año)
        LocalDate desdeWhen = fechaIngreso.isAfter(inicioAnio) ? fechaIngreso : inicioAnio;

        // Días trabajados en el año en curso
        long diasTrabajados = ChronoUnit.DAYS.between(desdeWhen, hoy);

        // Aguinaldo proporcional: (salarioDiario * 15 / 365) * diasTrabajados
        return salarioDiario
                .multiply(BigDecimal.valueOf(15))
                .divide(BigDecimal.valueOf(365), 10, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(diasTrabajados))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private int calcularDiasVacaciones(int antiguedadAnios) {
        if (antiguedadAnios <= 0) return 12;
        if (antiguedadAnios == 1) return 12;
        if (antiguedadAnios == 2) return 14;
        if (antiguedadAnios == 3) return 16;
        if (antiguedadAnios == 4) return 18;
        return 20; // 5 años en adelante
    }
}