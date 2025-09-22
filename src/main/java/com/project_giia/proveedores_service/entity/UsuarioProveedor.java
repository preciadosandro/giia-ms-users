package com.project_giia.proveedores_service.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("usuario_proveedores")
public class UsuarioProveedor {
    @Id
    private Long id;

    private Long usuarioId;   // ID desde auth-service
    private Long proveedorId; // FK hacia Proveedor
    private LocalDateTime fechaAsignacion;
    private Boolean esPrincipal;
    
    
 // Getter y Setter para id
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    // Getter y Setter para usuarioId
    public Long getUsuarioId() {
        return usuarioId;
    }
    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    // Getter y Setter para proveedorId
    public Long getProveedorId() {
        return proveedorId;
    }
    public void setProveedorId(Long proveedorId) {
        this.proveedorId = proveedorId;
    }

    // Getter y Setter para fechaAsignacion
    public LocalDateTime getFechaAsignacion() {
        return fechaAsignacion;
    }
    public void setFechaAsignacion(LocalDateTime fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    // Getter y Setter para esPrincipal
    public Boolean getEsPrincipal() {
        return esPrincipal;
    }
    public void setEsPrincipal(Boolean esPrincipal) {
        this.esPrincipal = esPrincipal;
    }
}