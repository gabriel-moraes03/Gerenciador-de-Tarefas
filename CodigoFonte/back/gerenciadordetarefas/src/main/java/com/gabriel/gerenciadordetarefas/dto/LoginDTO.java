package com.gabriel.gerenciadordetarefas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginDTO{

    @NotBlank(message = "O email é obrigatório")
    @Email (message = "Email inválido")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    private String senha;

    public LoginDTO() {
    }

    public LoginDTO(String senha, String email){
        this.senha = senha;
        this.email = email;
    }

    public String getEmail(){
        return this.email;
    }

    public String getSenha(){
        return this.senha;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }
}