package com.gabriel.gerenciadordetarefas.config;

import com.gabriel.gerenciadordetarefas.entity.Usuario;
import com.gabriel.gerenciadordetarefas.repository.UsuarioRepository;
import com.gabriel.gerenciadordetarefas.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = recoverToken(request);

        if (token != null && StringUtils.hasText(token)) {
            try {
                // Extrai email do token
                String email = tokenService.getEmailFromToken(token);

                // Verifica se ainda não há autenticação no contexto
                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    // Busca usuário no banco
                    Usuario user = userRepository.findByEmail(email).orElse(null);

                    if (user != null && tokenService.isTokenValid(token, user)) {
                        // Cria objeto de autenticação
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                        // Adiciona detalhes da requisição
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        // Seta no contexto de segurança do Spring
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }

            } catch (Exception e) {
                // Aqui você pode logar erros ou simplesmente ignorar
                // para continuar o filtro e não autenticar o usuário
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.substring(7);
    }
}
