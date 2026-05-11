package com.gaspo.api.service;

import com.gaspo.api.repository.gaspo.FuncionarioRepository;
import com.gaspo.api.repository.gaspo.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(username)
                .map(UserDetails.class::cast)
                .or(() -> funcionarioRepository.findByEmail(username).map(UserDetails.class::cast))
                .orElseThrow(() -> new UsernameNotFoundException("Usuário ou funcionário não encontrado com este email."));
    }
}
