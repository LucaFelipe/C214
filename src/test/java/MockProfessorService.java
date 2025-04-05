import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MockProfessorService {
    private final ProfessorRepositorio repository;

    public MockProfessorService(ProfessorRepositorio repository) {
        this.repository = repository;
    }

    public String obterHorarioAtendimento(String nomeProfessor) throws Exception {
        String jsonResponse = repository.buscarHorarioProfessor(nomeProfessor);

        if (jsonResponse == null || jsonResponse.trim().isEmpty()) {
            throw new IllegalArgumentException("Resposta do repositório está nula ou vazia");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);

        JsonNode nomeNode = jsonNode.get("nomeDoProfessor");
        JsonNode horarioNode = jsonNode.get("horarioDeAtendimento");
        JsonNode salaNode = jsonNode.get("sala");

        if (nomeNode == null || horarioNode == null || salaNode == null || !salaNode.isInt()) {
            throw new Exception("JSON inválido ou campos obrigatórios ausentes ou mal formatados.");
        }

        String nome = nomeNode.asText();
        String horario = horarioNode.asText();
        int sala = salaNode.asInt();
        int predio = calcularPredio(sala);

        return String.format("Professor: %s | Horário: %s | Sala: %d | Prédio: %d", nome, horario, sala, predio);
    }

    private int calcularPredio(int sala) {
        return (sala - 1) / 5 + 1;
    }
}