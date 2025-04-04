import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MockProfessorService {
    private final ProfessorRepositorio repository;

    public MockProfessorService(ProfessorRepositorio repository) {
        this.repository = repository;
    }

    public String obterHorarioAtendimento(String nomeProfessor) throws Exception {
        String jsonResponse = repository.buscarHorarioProfessor(nomeProfessor);

        if (jsonResponse == null) {
            throw new IllegalArgumentException("Resposta do repositório está nula");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);

        String nome = jsonNode.get("nomeDoProfessor").asText();
        String horario = jsonNode.get("horarioDeAtendimento").asText();
        int sala = jsonNode.get("sala").asInt();
        int predio = calcularPredio(sala);

        return String.format("Professor: %s | Horário: %s | Sala: %d | Prédio: %d", nome, horario, sala, predio);
    }

    private int calcularPredio(int sala) {
        return (sala - 1) / 5 + 1;
    }
}