package com.project_giia.proveedores_service.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {
    private Boolean signIn;
    private int rol;
    private Long idUsuario;
}
