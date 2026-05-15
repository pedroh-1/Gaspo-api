package com.gaspo.api.controller;

import com.gaspo.api.dto.request.UsuarioCadastroDTO;
import com.gaspo.api.model.gaspo.FuncionarioModel;
import com.gaspo.api.model.gaspo.UsuarioModel;
import com.gaspo.api.repository.gaspo.FuncionarioRepository;
import com.gaspo.api.repository.gaspo.UsuarioRepository;
import com.gaspo.api.security.TokenService;
import com.gaspo.api.service.PacienteService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web")
public class AuthWebController {

    private final TokenService tokenService;
    private final PacienteService pacienteService;
    private final UsuarioRepository usuarioRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthWebController(TokenService tokenService,
                             PacienteService pacienteService,
                             UsuarioRepository usuarioRepository,
                             FuncionarioRepository funcionarioRepository,
                             PasswordEncoder passwordEncoder) {
        this.tokenService = tokenService;
        this.pacienteService = pacienteService;
        this.usuarioRepository = usuarioRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping({"/", "/login"})
    public String exibirLogin(Model model) {
        return "login";
    }

    @GetMapping("/cadastro")
    public String exibirCadastro(Model model) {
        if (!model.containsAttribute("cadastro")) {
            model.addAttribute("cadastro", new UsuarioCadastroDTO("", "", null, "", "", ""));
        }
        return "cadastro-paciente";
    }

    @PostMapping("/cadastro")
    public String cadastrarPaciente(@Valid @ModelAttribute("cadastro") UsuarioCadastroDTO cadastro,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("erro", "Preencha CPF, e-mail e senha corretamente.");
            redirectAttributes.addFlashAttribute("cadastro", cadastro);
            return "redirect:/web/cadastro";
        }

        try {
            pacienteService.cadastrarUsuario(cadastro);
            redirectAttributes.addFlashAttribute("mensagem", "Cadastro realizado com sucesso. Faça login para continuar.");
            return "redirect:/web/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao cadastrar paciente: " + e.getMessage());
            redirectAttributes.addFlashAttribute("cadastro", cadastro);
            return "redirect:/web/cadastro";
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String senha,
                        @RequestParam(defaultValue = "USUARIO") String tipoAcesso,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {
        try {
            UserDetails principal = buscarPrincipal(email, senha, tipoAcesso);

            session.setAttribute("token", tokenService.generateToken(principal));
            session.setAttribute("usuarioEmail", principal.getUsername());
            session.setAttribute("usuarioNome", nomeDoPrincipal(principal));
            session.setAttribute("usuarioTipo", tipoDoPrincipal(principal));
            preencherIdsDaSessao(session, principal);

            redirectAttributes.addFlashAttribute("mensagem", "Login realizado com sucesso!");
            return "redirect:/web/avisos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "E-mail, senha ou tipo de acesso inválidos.");
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

    private UserDetails buscarPrincipal(String email, String senha, String tipoAcesso) {
        UserDetails principal = "FUNCIONARIO".equalsIgnoreCase(tipoAcesso)
                ? funcionarioRepository.findByEmail(email)
                    .map(UserDetails.class::cast)
                    .orElseThrow(() -> new BadCredentialsException("Funcionário não encontrado."))
                : usuarioRepository.findByEmail(email)
                    .map(UserDetails.class::cast)
                    .orElseThrow(() -> new BadCredentialsException("Usuário não encontrado."));

        if (!passwordEncoder.matches(senha, principal.getPassword())) {
            throw new BadCredentialsException("Credenciais inválidas.");
        }

        return principal;
    }

    private void preencherIdsDaSessao(HttpSession session, UserDetails principal) {
        if (principal instanceof UsuarioModel usuario) {
            session.setAttribute("usuarioId", usuario.getId());
        }
        if (principal instanceof FuncionarioModel funcionario) {
            session.setAttribute("funcionarioId", funcionario.getId());
        }
    }
}
