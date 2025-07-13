package com.gabriel.gerenciadordetarefas.service;

import com.gabriel.gerenciadordetarefas.dto.UsuarioDTO;
import com.gabriel.gerenciadordetarefas.dto.UsuarioRespostaDTO;
import com.gabriel.gerenciadordetarefas.entity.Usuario;
import com.gabriel.gerenciadordetarefas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Optional<Usuario> buscarPorId(UUID id){
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> buscarPorEmail(String email){
        return usuarioRepository.findByEmail(email);
    }

    public UsuarioRespostaDTO cadastrar(UsuarioDTO dto){
        String senhaCriptografada = bCryptPasswordEncoder.encode(dto.getSenha());
        Usuario usuario = Usuario.fromDTO(dto);
        usuario.setSenha(senhaCriptografada);
        Usuario salvo = usuarioRepository.save(usuario);

        return salvo.toRespostaDTO();
    }

    public UsuarioRespostaDTO login(String email, String senha) {
        Usuario usuario = buscarPorEmail(email).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if(!conferirSenha(usuario.getSenha(), senha)){
            throw new RuntimeException("Senha inválida");
        }

        return usuario.toRespostaDTO();
    }

    public boolean conferirSenha(String senhaCriptografada, String senhaCrua){
        return bCryptPasswordEncoder.matches(senhaCrua, senhaCriptografada);
    }
}
