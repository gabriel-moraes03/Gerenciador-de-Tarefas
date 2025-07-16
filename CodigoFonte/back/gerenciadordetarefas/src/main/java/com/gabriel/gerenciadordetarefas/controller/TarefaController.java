package com.gabriel.gerenciadordetarefas.controller;

import com.gabriel.gerenciadordetarefas.dto.TarefaDTO;
import com.gabriel.gerenciadordetarefas.enums.EstadoTarefa;
import com.gabriel.gerenciadordetarefas.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {
    @Autowired
    private TarefaService tarefaService;

    @GetMapping("/{id}")
    private ResponseEntity<TarefaDTO> buscarPorId(@PathVariable UUID id){
        return ResponseEntity.ok().body(tarefaService.buscarPorId(id));
    }

    @GetMapping("/tarefasUsuario/{idUsuario}")
    private ResponseEntity<List<TarefaDTO>> listarTarefasUsuario(@PathVariable UUID idUsuario){
        return ResponseEntity.ok().body(tarefaService.listarTarefasUsuario(idUsuario));
    }

    @GetMapping("/tarefasUsuario/{idUsuario}/{estado}")
    private ResponseEntity<List<TarefaDTO>> listarTarefasUsuarioPorEstado(@PathVariable UUID idUsuario, @PathVariable EstadoTarefa estado){
        return ResponseEntity.ok().body(tarefaService.listarTarefasPorEstado(idUsuario, estado));
    }

    @PostMapping("/criar/{idUsuario}")
    private ResponseEntity<TarefaDTO> criarTarefa(@PathVariable UUID idUsuario, @RequestBody TarefaDTO dto){
        return ResponseEntity.ok().body(tarefaService.criarTarefaParaUsuario(idUsuario, dto));
    }

    @PutMapping("/atualizar/{id}")
    private ResponseEntity<TarefaDTO> atualizarTarefa(@PathVariable UUID id, @RequestBody TarefaDTO atualizada){
        return ResponseEntity.ok().body(tarefaService.atualizarTarefa(id, atualizada));
    }

    @DeleteMapping("/deletar/{id}")
    private ResponseEntity<Void> deletarTarefa(@PathVariable UUID id){
        tarefaService.deletarTarefa(id);

        return ResponseEntity.noContent().build();
    }
}
