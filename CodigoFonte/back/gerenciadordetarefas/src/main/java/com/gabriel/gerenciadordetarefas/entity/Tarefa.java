package com.gabriel.gerenciadordetarefas.entity;

import com.gabriel.gerenciadordetarefas.dto.TarefaDTO;
import com.gabriel.gerenciadordetarefas.enums.EstadoTarefa;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Tarefa")
public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true, updatable = false)
    @Setter(AccessLevel.NONE)
    private UUID idTarefa;

    @Column(nullable = false)
    private String nome;

    private String descricao;

    @Column(nullable = false)
    private EstadoTarefa estado;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    public Tarefa(){
    }

    public Tarefa(TarefaDTO dto, Usuario usuario){
        this.estado = dto.getEstado();
        this.nome = dto.getNome();
        this.descricao = dto.getDescricao();
        this.usuario = usuario;
    }

    public static Tarefa fromDTO(TarefaDTO dto, Usuario usuario){
        return new Tarefa(dto, usuario);
    }

    public TarefaDTO toDTO() {
        return new TarefaDTO(this.getIdTarefa(), this.getNome(), this.getDescricao(), this.getEstado());
    }

    public UUID getIdTarefa(){
        return this.idTarefa;
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
