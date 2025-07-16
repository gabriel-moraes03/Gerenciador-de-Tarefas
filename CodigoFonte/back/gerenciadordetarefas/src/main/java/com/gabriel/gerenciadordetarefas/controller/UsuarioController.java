package com.gabriel.gerenciadordetarefas.controller;

import com.gabriel.gerenciadordetarefas.dto.LoginDTO;
import com.gabriel.gerenciadordetarefas.dto.RecuperarSenhaDTO;
import com.gabriel.gerenciadordetarefas.dto.UsuarioDTO;
import com.gabriel.gerenciadordetarefas.dto.UsuarioRespostaDTO;
import com.gabriel.gerenciadordetarefas.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRespostaDTO> buscarUsuarioPorId(@PathVariable UUID id){
        return ResponseEntity.ok().body(usuarioService.buscarPorId(id));
    }

    @PostMapping("/registrar")
    public ResponseEntity<UsuarioRespostaDTO> registrar(@Valid @RequestBody UsuarioDTO dto){
        return ResponseEntity.ok().body(usuarioService.cadastrar(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioRespostaDTO> login(@Valid @RequestBody LoginDTO dto){
        return ResponseEntity.ok().body(usuarioService.login(dto));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable UUID id){
        usuarioService.deletarUsuario(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<UsuarioRespostaDTO> atualizarUsuario(@PathVariable UUID id, @Valid @RequestBody UsuarioDTO dto) {
        return ResponseEntity.ok().body(usuarioService.atualizarUsuario(id, dto));
    }

    @PutMapping("/atualizarSenha/{id}")
    public ResponseEntity<Void> atualizarSenha(@PathVariable UUID id, @Valid @RequestBody RecuperarSenhaDTO dto){
        usuarioService.atualizarSenha(id, dto.getSenhaAtual(), dto.getSenhaNova());
        return ResponseEntity.noContent().build();
    }
}
