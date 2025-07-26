package com.gabriel.gerenciadordetarefas.entity;

import com.gabriel.gerenciadordetarefas.dto.UsuarioDTO;
import com.gabriel.gerenciadordetarefas.dto.UsuarioRespostaDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Entity
@Table(name = "Usuario")
public class Usuario implements UserDetails {

    @Id //anotação que marca sendo o ID
    @GeneratedValue(strategy = GenerationType.AUTO) //anotação que indica que o valor da chave primária será gerado automaticamente pelo provedor de persistência
    @Column(nullable = false, unique = true, updatable = false)
    @Setter(AccessLevel.NONE) //anotação que indica que esse atributo não pode ser alterado por setters
    private UUID idUsuario;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    // Construtor padrão (necessário para JPA)
    public Usuario() {
    }

    public Usuario(UsuarioDTO dto){
        this.email = dto.getEmail();
        this.nome = dto.getNome();
        this.senha = dto.getSenha();
    }

    public UsuarioRespostaDTO toRespostaDTO() {
        return new UsuarioRespostaDTO(this.idUsuario, this.nome, this.email);
    }

    public String getNome(){
        return this.nome;
    }

    public UUID getIdUsuario() {
        return idUsuario;
    }

    public String getEmail() {
        return this.email;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Aqui você pode adicionar lógica para roles/permissões.
        return Collections.emptyList(); // Sem roles por enquanto
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // conta nunca expira
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // conta nunca bloqueia
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // credenciais nunca expiram
    }

    @Override
    public boolean isEnabled() {
        return true; // usuário está ativo
    }
}
