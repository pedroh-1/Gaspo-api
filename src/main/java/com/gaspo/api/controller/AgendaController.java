import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/agendas")
public class AgendaController {

    @Autowired
    private AgendaService service;

    /*@PostMapping("/gerenciar")
    public ResponseEntity<String> definirAgenda(@RequestBody AgendaRequestDTO dto) {
        service.processarAgenda(dto);
        return ResponseEntity.ok("Agenda processada e sincronizada com sucesso!");
    }*/

}