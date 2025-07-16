package com.gabriel.gerenciadordetarefas.dto;

import com.gabriel.gerenciadordetarefas.enums.EstadoTarefa;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class TarefaDTO {
    private UUID id;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    private String descricao;

    private EstadoTarefa estado;

    public TarefaDTO(){}

    public TarefaDTO(UUID id, String nome, String descricao, EstadoTarefa estado){
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.estado = estado;
    }

    public UUID getId(){ return this.id; }

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
