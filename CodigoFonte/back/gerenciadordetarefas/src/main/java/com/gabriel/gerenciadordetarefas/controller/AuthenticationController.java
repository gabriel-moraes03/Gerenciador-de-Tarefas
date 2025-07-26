package com.gabriel.gerenciadordetarefas.controller;

import com.gabriel.gerenciadordetarefas.dto.LoginDTO;
import com.gabriel.gerenciadordetarefas.dto.UsuarioDTO;
import com.gabriel.gerenciadordetarefas.dto.UsuarioRespostaDTO;
import com.gabriel.gerenciadordetarefas.service.TokenService;
import com.gabriel.gerenciadordetarefas.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TokenService tokenService;

    @Operation(summary = "Fazer login no sistema", description = "Autentica um usuário com email e senha")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioRespostaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content),
            @ApiResponse(responseCode = "401", description = "Senha incorreta", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO dto) {
        UsuarioRespostaDTO usuario = usuarioService.login(dto); // lança erros 404 e 401 via ResponseStatusException

        var userDetails = usuarioService.buscarPorEmail(dto.getEmail()).get();
        String token = tokenService.gerarToken(userDetails);

        return ResponseEntity.ok(Map.of(
                "token", token,
                "usuario", usuario
        ));
    }

    @Operation(summary = "Registrar novo usuário", description = "Cria um novo usuário com email, nome e senha")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioRespostaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Email já cadastrado", content = @Content)
    })
    @PostMapping("/registrar")
    public ResponseEntity<UsuarioRespostaDTO> registrar(@RequestBody @Valid UsuarioDTO dto) {
        return ResponseEntity.ok().body(usuarioService.cadastrar(dto));
    }
}
