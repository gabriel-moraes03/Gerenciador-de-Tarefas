package com.gabriel.gerenciadordetarefas.dto;

import com.gabriel.gerenciadordetarefas.enums.EstadoTarefa;
import jakarta.validation.constraints.NotBlank;

public class TarefaDTO {
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    private String descricao;

    private EstadoTarefa estado;

    public TarefaDTO(){}

    public TarefaDTO(String nome, String descricao, EstadoTarefa estado){
        this.nome = nome;
        this.descricao = descricao;
        this.estado = estado;
    }

    public String getNome(){
        return this.nome;
    }

    public String getDescricao(){
        return this.descricao;
    }

    public EstadoTarefa getEstado(){
        return this.estado;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public void setEstado(EstadoTarefa estado){
        this.estado = estado;
    }
}
