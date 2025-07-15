package com.gabriel.gerenciadordetarefas.service;

import com.gabriel.gerenciadordetarefas.dto.TarefaDTO;
import com.gabriel.gerenciadordetarefas.dto.UsuarioDTO;
import com.gabriel.gerenciadordetarefas.dto.UsuarioRespostaDTO;
import com.gabriel.gerenciadordetarefas.entity.Tarefa;
import com.gabriel.gerenciadordetarefas.entity.Usuario;
import com.gabriel.gerenciadordetarefas.enums.EstadoTarefa;
import com.gabriel.gerenciadordetarefas.repository.TarefaRepository;
import com.gabriel.gerenciadordetarefas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public TarefaDTO buscarPorId (UUID id){
        Tarefa tarefa =  tarefaRepository.findById(id).orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        return tarefa.toDTO();
    }

    public Optional<Tarefa> buscarPorIdInterno (UUID id){
        return tarefaRepository.findById(id);
    }

    public void deletarTarefa(UUID id) {
        Tarefa tarefa = buscarPorIdInterno(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        tarefaRepository.delete(tarefa);
    }

    public TarefaDTO atualizarTarefa(UUID id, TarefaDTO dto) {
        Tarefa tarefa = buscarPorIdInterno(id).orElseThrow(() -> new RuntimeException(("Tarefa não encontrada")));

        tarefa.setDescricao(dto.getDescricao());
        tarefa.setEstado(dto.getEstado());
        tarefa.setNome(dto.getNome());

        Tarefa atualizada = tarefaRepository.save(tarefa);
        return atualizada.toDTO();
    }

    public void criarTarefaParaUsuario(UUID idUsuario, TarefaDTO dto) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Tarefa tarefa = new Tarefa(dto, usuario);
        tarefaRepository.save(tarefa);
    }

    public List<Tarefa> listarTarefasUsuario(UUID idUsuario){
        return tarefaRepository.findByUsuarioId(idUsuario);
    }

    public List<TarefaDTO> listarTarefasPorEstado(UUID idUsuario, EstadoTarefa estado) {
        return tarefaRepository.findByUsuarioIdAndEstado(idUsuario, estado)
                .stream()
                .map(Tarefa::toDTO)
                .toList();
    }
}
