import com.gaspo.api.model.AgendaModel;
import com.gaspo.api.model.Disponibilidade;
import com.gaspo.api.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class AgendaService {
    @Autowired
    private AgendaRepository repository;

    // Essa é a implementação prática do 'loop' do seu diagrama [cite: 342]
    public void criarGradeHoraria(Profissional profissional, List<LocalTime> horariosParaCriar) {

        for (LocalTime hora : horariosParaCriar) {
            // Criando cada "vaga" individual (adicionarHorario) [cite: 344]
            AgendaModel slot = new AgendaModel();
            slot.setProfissional(profissional);
            slot.setHorario(hora);
            slot.setDisponibilidade(Disponibilidade.DISPONIVEL); // Seu Enum!

            // Salvando no banco de dados
            repository.save(slot);
        }

        // Após o loop, faz a sincronização com o PEC e-SUS 
       // sincronizarComPecESus();
    }
}