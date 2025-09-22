package com.project_giia.proveedores_service.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("proveedores")
public class Proveedor {
    @Id
    private Long id;

    private String nit;
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
    private Boolean activo = true;

  
    private LocalDateTime fechaRegistro = LocalDateTime.now();
    
    
    // Getter
    public Long getId() {
		return id;
	}
    
	public String getNit() {
		return nit;
	}

	public String getNombre() {
		return nombre;
	}
	
	public String getEmail() {
		return email;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getDireccion() {
		return direccion;
	}
	
	public Boolean getActivo() {
		return activo;
	}
	
	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}
	
    // Setter
	public void setNit(String nit) {
	    this.nit = nit;
	}

	public void setNombre(String nombre) {
	    this.nombre = nombre;
	}
	
	public void setEmail(String email) {
	    this.email = email;
	}

	public void setTelefono(String telefono) {
	    this.telefono = telefono;
	}

	public void setDireccion(String direccion) {
	    this.direccion = direccion;
	}

	public void setActivo(Boolean activo) {
	    this.activo = activo;
	}

	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}


	
}
