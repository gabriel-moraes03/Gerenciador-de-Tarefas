package com.gabriel.gerenciadordetarefas.service;

import com.gabriel.gerenciadordetarefas.dto.LoginDTO;
import com.gabriel.gerenciadordetarefas.dto.UsuarioDTO;
import com.gabriel.gerenciadordetarefas.dto.UsuarioRespostaDTO;
import com.gabriel.gerenciadordetarefas.entity.Usuario;
import com.gabriel.gerenciadordetarefas.repository.TarefaRepository;
import com.gabriel.gerenciadordetarefas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public Optional<Usuario> buscarPorIdInterno(UUID id){
        return usuarioRepository.findById(id);
    }

    public UsuarioRespostaDTO buscarPorId(UUID id){
        Usuario usuario =  usuarioRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        return usuario.toRespostaDTO();
    }

    public Optional<Usuario> buscarPorEmail(String email){
        return usuarioRepository.findByEmail(email);
    }

    public UsuarioRespostaDTO cadastrar(UsuarioDTO dto){
        if(buscarPorEmail(dto.getEmail()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já cadastrado");
        }
        String senhaCriptografada = bCryptPasswordEncoder.encode(dto.getSenha());
        Usuario usuario = new Usuario(dto);
        usuario.setSenha(senhaCriptografada);
        Usuario salvo = usuarioRepository.save(usuario);

        return salvo.toRespostaDTO();
    }

    public UsuarioRespostaDTO login(LoginDTO dto) {
        Usuario usuario = buscarPorEmail(dto.getEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if(!conferirSenha(usuario.getSenha(), dto.getSenha())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED , "Senha incorreta");
        }

        return usuario.toRespostaDTO();
    }

    public boolean conferirSenha(String senhaCriptografada, String senhaCrua){
        return bCryptPasswordEncoder.matches(senhaCrua, senhaCriptografada);
    }

    public void deletarUsuario(UUID id){
        Usuario usuario = buscarPorIdInterno(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        usuarioRepository.delete(usuario);
    }

    public UsuarioRespostaDTO atualizarUsuario(UUID id, UsuarioDTO dto) {
        Usuario usuario = buscarPorIdInterno(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if(!usuario.getEmail().equals(dto.getEmail())){
            Optional<Usuario> existente = buscarPorEmail(dto.getEmail());
            if(existente.isPresent() && !existente.get().getIdUsuario().equals(id)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já cadastrado");
            }
            usuario.setEmail(dto.getEmail());
        }
        usuario.setNome(dto.getNome());

        return usuarioRepository.save(usuario).toRespostaDTO();
    }

    public void atualizarSenha(UUID id, String senhaAtual, String novaSenha) {
        Usuario usuario = buscarPorIdInterno(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if (!bCryptPasswordEncoder.matches(senhaAtual, usuario.getSenha())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED , "Senha incorreta");
        }

        String senhaCriptografada = bCryptPasswordEncoder.encode(novaSenha);
        usuario.setSenha(senhaCriptografada);
        usuarioRepository.save(usuario);
    }
}
