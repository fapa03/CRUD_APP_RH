package com.rh.empleados.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class EmpleadoDetalleDTO {

    // Datos del empleado
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String puesto;
    private BigDecimal salarioMensual;
    private LocalDate fechaIngreso;

    // Prestaciones calculadas
    private BigDecimal salarioDiario;
    private Integer antiguedadAnios;
    private Integer diasVacaciones;
    private BigDecimal aguinaldo;
    private BigDecimal primaVacacional;
}