package com.gabriel.gerenciadordetarefas.controller;

import com.gabriel.gerenciadordetarefas.dto.LoginDTO;
import com.gabriel.gerenciadordetarefas.dto.RecuperarSenhaDTO;
import com.gabriel.gerenciadordetarefas.dto.UsuarioDTO;
import com.gabriel.gerenciadordetarefas.dto.UsuarioRespostaDTO;
import com.gabriel.gerenciadordetarefas.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
@Tag(name="Usuários", description = "Operações relacionadas ao gerenciamento de usuários")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário específico com base no ID informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioRespostaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    public ResponseEntity<UsuarioRespostaDTO> buscarUsuarioPorId(
            @Parameter(description = "ID do usuário",
                    required = true)
            @PathVariable UUID id) {
        return ResponseEntity.ok().body(usuarioService.buscarPorId(id));
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Excluir usuário", description = "Exclui um usuário do sistema a partir do ID fornecido")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "204", description = "Usuario deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> deletarUsuario(
            @Parameter(description = "ID do usuário a ser deletado", required = true)
            @PathVariable UUID id){
        usuarioService.deletarUsuario(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados do usuário, identificado pelo ID, a partir dos dados fornecidos")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioRespostaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Email já cadastrado", content = @Content)
    })
    public ResponseEntity<UsuarioRespostaDTO> atualizarUsuario(@PathVariable UUID id, @Valid @RequestBody UsuarioDTO dto) {
        return ResponseEntity.ok().body(usuarioService.atualizarUsuario(id, dto));
    }

    @PutMapping("/atualizarSenha/{id}")
    @Operation(summary = "Atualizar senha do usuário", description = "Atualiza a senha do usuário com base na senha atual")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content),
            @ApiResponse(responseCode = "401", description = "Senha incorreta", content = @Content)
    })
    public ResponseEntity<Void> atualizarSenha(@PathVariable UUID id, @Valid @RequestBody RecuperarSenhaDTO dto){
        usuarioService.atualizarSenha(id, dto.getSenhaAtual(), dto.getSenhaNova());
        return ResponseEntity.noContent().build();
    }
}
