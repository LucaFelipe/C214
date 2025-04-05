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
        JsonNode jsonNode;

        try {
            jsonNode = objectMapper.readTree(jsonResponse);
        } catch (Exception e) {
            throw new Exception("Erro ao tentar ler o JSON", e);
        }

        JsonNode nomeNode = jsonNode.get("nomeDoProfessor");
        JsonNode horarioNode = jsonNode.get("horarioDeAtendimento");
        JsonNode salaNode = jsonNode.get("sala");

        if (nomeNode == null || nomeNode.isNull() || !nomeNode.isTextual()) {
            throw new Exception("Campo 'nomeDoProfessor' inválido ou ausente");
        }

        String nome = nomeNode.asText().trim();
        if (nome.isEmpty()) {
            throw new Exception("Campo 'nomeDoProfessor' está vazio");
        }

        if (horarioNode == null || horarioNode.isNull() || !horarioNode.isTextual()) {
            throw new Exception("Campo 'horarioDeAtendimento' inválido ou ausente");
        }

        if (salaNode == null || salaNode.isNull() || !salaNode.isInt()) {
            throw new Exception("Campo 'sala' inválido ou ausente");
        }

        int sala = salaNode.asInt();
        if (sala <= 0) {
            throw new Exception("Valor de sala deve ser maior que zero");
        }

        int predio = calcularPredio(sala);
        if (predio <= 0 || predio > 20) { // ajusta esse limite como achar melhor
            throw new Exception("Prédio calculado está fora dos limites válidos");
        }

        String horario = horarioNode.asText();

        return String.format("Professor: %s | Horário: %s | Sala: %d | Prédio: %d", nome, horario, sala, predio);
    }

    private int calcularPredio(int sala) {
        return (sala - 1) / 5 + 1;
    }
}

