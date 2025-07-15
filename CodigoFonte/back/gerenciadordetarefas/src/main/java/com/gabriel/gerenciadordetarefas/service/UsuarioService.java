package com.gabriel.gerenciadordetarefas.service;

import com.gabriel.gerenciadordetarefas.dto.LoginDTO;
import com.gabriel.gerenciadordetarefas.dto.TarefaDTO;
import com.gabriel.gerenciadordetarefas.dto.UsuarioDTO;
import com.gabriel.gerenciadordetarefas.dto.UsuarioRespostaDTO;
import com.gabriel.gerenciadordetarefas.entity.Tarefa;
import com.gabriel.gerenciadordetarefas.entity.Usuario;
import com.gabriel.gerenciadordetarefas.repository.TarefaRepository;
import com.gabriel.gerenciadordetarefas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TarefaRepository tarefaRepository;

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

    public UsuarioRespostaDTO login(LoginDTO dto) {
        Usuario usuario = buscarPorEmail(dto.getEmail()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if(!conferirSenha(usuario.getSenha(), dto.getSenha())){
            throw new RuntimeException("Senha inválida");
        }

        return usuario.toRespostaDTO();
    }

    public boolean conferirSenha(String senhaCriptografada, String senhaCrua){
        return bCryptPasswordEncoder.matches(senhaCrua, senhaCriptografada);
    }

    public void deletarUsuario(UUID id){
        Usuario usuario = buscarPorId(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuarioRepository.delete(usuario);
    }

    public UsuarioRespostaDTO atualizarUsuario(UUID id, UsuarioDTO dto) {
        Usuario usuario = buscarPorId(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if(!usuario.getEmail().equals(dto.getEmail())){
            Optional<Usuario> existente = buscarPorEmail(dto.getEmail());
            if(existente.isPresent() && existente.get().getIdUsuario().equals(id)){
                throw new RuntimeException("E-mail já cadastrado");
            }
            usuario.setEmail(dto.getEmail());
        }
        usuario.setNome(dto.getNome());

        return usuarioRepository.save(usuario).toRespostaDTO();
    }

    public void atualizarSenha(UUID id, String senhaAtual, String novaSenha) {
        Usuario usuario = buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!bCryptPasswordEncoder.matches(senhaAtual, usuario.getSenha())) {
            throw new RuntimeException("Senha atual incorreta");
        }

        String senhaCriptografada = bCryptPasswordEncoder.encode(novaSenha);
        usuario.setSenha(senhaCriptografada);
        usuarioRepository.save(usuario);
    }
}
