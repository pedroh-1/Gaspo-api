package com.gaspo.api.controller;

import com.gaspo.api.model.gaspo.FuncionarioModel;
import com.gaspo.api.model.gaspo.UsuarioModel;
import com.gaspo.api.security.TokenService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web")
public class AuthWebController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthWebController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @GetMapping({"/", "/login"})
    public String exibirLogin(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String senha,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {
        try {
            var authRequest = new UsernamePasswordAuthenticationToken(email, senha);
            var auth = authenticationManager.authenticate(authRequest);
            UserDetails principal = (UserDetails) auth.getPrincipal();

            session.setAttribute("token", tokenService.generateToken(principal));
            session.setAttribute("usuarioEmail", principal.getUsername());
            session.setAttribute("usuarioNome", nomeDoPrincipal(principal));
            session.setAttribute("usuarioTipo", tipoDoPrincipal(principal));

            redirectAttributes.addFlashAttribute("mensagem", "Login realizado com sucesso!");
            return "redirect:/web/avisos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "E-mail ou senha inválidos.");
            return "redirect:/web/login";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("mensagem", "Sessão encerrada.");
        return "redirect:/web/login";
    }

    private String nomeDoPrincipal(UserDetails principal) {
        if (principal instanceof UsuarioModel usuario) {
            return usuario.getNome();
        }
        if (principal instanceof FuncionarioModel funcionario) {
            return funcionario.getNome();
        }
        return principal.getUsername();
    }

    private String tipoDoPrincipal(UserDetails principal) {
        if (principal instanceof FuncionarioModel) {
            return "FUNCIONARIO";
        }
        return "USUARIO";
    }
}
