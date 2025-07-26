package com.gabriel.gerenciadordetarefas.controller;

import com.gabriel.gerenciadordetarefas.dto.TarefaDTO;
import com.gabriel.gerenciadordetarefas.enums.EstadoTarefa;
import com.gabriel.gerenciadordetarefas.service.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tarefas")
@Tag(name="Tarefas", description = "Operações relacionadas ao gerenciamento de tarefas")
public class TarefaController {
    @Autowired
    private TarefaService tarefaService;

    @Operation(summary = "Buscar tarefa por ID", description = "Retorna os dados de uma tarefa pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa encontrada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TarefaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<TarefaDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok().body(tarefaService.buscarPorId(id));
    }

    @Operation(summary = "Listar tarefas de um usuário", description = "Retorna todas as tarefas de um usuário, podendo filtrar por estado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefas listadas com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TarefaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @GetMapping("/tarefasUsuario/{idUsuario}")
    private ResponseEntity<List<TarefaDTO>> listarTarefasUsuario(@PathVariable UUID idUsuario, @RequestParam(required = false) EstadoTarefa estado) {
        if (estado != null) {
            return ResponseEntity.ok().body(tarefaService.listarTarefasPorEstado(idUsuario, estado));
        } else {
            return ResponseEntity.ok().body(tarefaService.listarTarefasUsuario(idUsuario));
        }
    }

    @Operation(summary = "Criar tarefa", description = "Cria uma nova tarefa para o usuário especificado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa criada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TarefaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @PostMapping("/criar/{idUsuario}")
    private ResponseEntity<TarefaDTO> criarTarefa(@PathVariable UUID idUsuario, @RequestBody TarefaDTO dto){
        return ResponseEntity.ok().body(tarefaService.criarTarefaParaUsuario(idUsuario, dto));
    }

    @Operation(summary = "Atualizar tarefa", description = "Atualiza os dados de uma tarefa existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TarefaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada", content = @Content)
    })
    @PutMapping("/atualizar/{id}")
    private ResponseEntity<TarefaDTO> atualizarTarefa(@PathVariable UUID id, @RequestBody TarefaDTO atualizada){
        return ResponseEntity.ok().body(tarefaService.atualizarTarefa(id, atualizada));
    }

    @Operation(summary = "Deletar tarefa", description = "Remove uma tarefa pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tarefa deletada com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada", content = @Content)
    })
    @DeleteMapping("/deletar/{id}")
    private ResponseEntity<Void> deletarTarefa(@PathVariable UUID id){
        tarefaService.deletarTarefa(id);

        return ResponseEntity.noContent().build();
    }
}
