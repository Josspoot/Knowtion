package com.mx.tecdesoftware.knowtion.dto;

/** Respuesta del login: el token JWT y el esquema con el que debe enviarse. */
public class LoginResponse {

    private String token;
    private String tipo = "Bearer";

    public LoginResponse() {}

    public LoginResponse(String token) {
        this.token = token;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
