package com.gabriel.gerenciadordetarefas.repository;

import com.gabriel.gerenciadordetarefas.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TarefaRepository extends JpaRepository<Tarefa, UUID> {
    @Override
    Optional<Tarefa> findById(UUID uuid);

    List<Tarefa> findByUsuarioId(UUID userId);
}
