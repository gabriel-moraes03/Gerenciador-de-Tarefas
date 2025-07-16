package com.gabriel.gerenciadordetarefas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RecuperarSenhaDTO {
    @NotBlank(message = "A senha atual é obrigatória")
    private String senhaAtual;

    @NotBlank(message = "A nova senha é obrigatória")
    private String senhaNova;

    public RecuperarSenhaDTO() {
    }

    public RecuperarSenhaDTO(String senhaAtual, String senhaNova){
        this.senhaAtual = senhaAtual;
        this.senhaNova = senhaNova;
    }

    public String getSenhaNova(){
        return this.senhaNova;
    }

    public String getSenhaAtual(){
        return this.senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual){
        this.senhaAtual = senhaAtual;
    }

    public void setSenhaNova(String senhaNova){
        this.senhaNova = senhaNova;
    }
}
