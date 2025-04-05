import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestesProfessor{

    private ProfessorRepositorio repositorioMock;
    private MockProfessorService service;

    @BeforeEach
    public void setUp() {
        repositorioMock = mock(ProfessorRepositorio.class);
        service = new MockProfessorService(repositorioMock);
    }

    /*
        -------------------------
        TESTES POSITIVOS
        -------------------------
    */

    @Test
    public void deveRetornarInformacoesCorretas() throws Exception {
        String json = "{\"nomeDoProfessor\":\"João\",\"horarioDeAtendimento\":\"10h\",\"sala\":7}";
        when(repositorioMock.buscarHorarioProfessor("João")).thenReturn(json);

        String resultado = service.obterHorarioAtendimento("João");
        assertEquals("Professor: João | Horário: 10h | Sala: 7 | Prédio: 2", resultado);
    }

    @Test
    public void deveCalcularPredioCorretamente() throws Exception {
        String json = "{\"nomeDoProfessor\":\"Maria\",\"horarioDeAtendimento\":\"14h\",\"sala\":4}";
        when(repositorioMock.buscarHorarioProfessor("Maria")).thenReturn(json);

        String resultado = service.obterHorarioAtendimento("Maria");
        assertTrue(resultado.contains("Prédio: 1"));
    }

    @Test
    public void deveCalcularPredioCorretamente2() throws Exception {
        String json = "{\"nomeDoProfessor\":\"Maria\",\"horarioDeAtendimento\":\"14h\",\"sala\":7}";
        when(repositorioMock.buscarHorarioProfessor("Maria")).thenReturn(json);

        String resultado = service.obterHorarioAtendimento("Maria");
        assertTrue(resultado.contains("Prédio: 2"));
    }

    @Test
    public void deveCalcularPredioCorretamente3() throws Exception {
        String json = "{\"nomeDoProfessor\":\"Maria\",\"horarioDeAtendimento\":\"14h\",\"sala\":12}";
        when(repositorioMock.buscarHorarioProfessor("Maria")).thenReturn(json);

        String resultado = service.obterHorarioAtendimento("Maria");
        assertTrue(resultado.contains("Prédio: 3"));
    }

    @Test
    public void deveCalcularPredioCorretamente4() throws Exception {
        String json = "{\"nomeDoProfessor\":\"Maria\",\"horarioDeAtendimento\":\"14h\",\"sala\":18}";
        when(repositorioMock.buscarHorarioProfessor("Maria")).thenReturn(json);

        String resultado = service.obterHorarioAtendimento("Maria");
        assertTrue(resultado.contains("Prédio: 4"));
    }

    @Test
    public void deveCalcularPredioCorretamente5() throws Exception {
        String json = "{\"nomeDoProfessor\":\"Maria\",\"horarioDeAtendimento\":\"14h\",\"sala\":21}";
        when(repositorioMock.buscarHorarioProfessor("Maria")).thenReturn(json);

        String resultado = service.obterHorarioAtendimento("Maria");
        assertTrue(resultado.contains("Prédio: 5"));
    }

    @Test
    public void deveCalcularPredioCorretamente6() throws Exception {
        String json = "{\"nomeDoProfessor\":\"Maria\",\"horarioDeAtendimento\":\"14h\",\"sala\":27}";
        when(repositorioMock.buscarHorarioProfessor("Maria")).thenReturn(json);

        String resultado = service.obterHorarioAtendimento("Maria");
        assertTrue(resultado.contains("Prédio: 6"));
    }


    @Test
    public void deveManterNomeProfessor() throws Exception {
        String json = "{\"nomeDoProfessor\":\"Carlos\",\"horarioDeAtendimento\":\"9h\",\"sala\":3}";
        when(repositorioMock.buscarHorarioProfessor("Carlos")).thenReturn(json);

        String resultado = service.obterHorarioAtendimento("Carlos");
        assertTrue(resultado.contains("Professor: Carlos"));
    }

    @Test
    public void deveFormatarCorretamente() throws Exception {
        String json = "{\"nomeDoProfessor\":\"Ana\",\"horarioDeAtendimento\":\"13h\",\"sala\":1}";
        when(repositorioMock.buscarHorarioProfessor("Ana")).thenReturn(json);

        String resultado = service.obterHorarioAtendimento("Ana");
        assertEquals("Professor: Ana | Horário: 13h | Sala: 1 | Prédio: 1", resultado);
    }

    @Test
    public void deveAceitarSalaMaiorQue5() throws Exception {
        String json = "{\"nomeDoProfessor\":\"Pedro\",\"horarioDeAtendimento\":\"16h\",\"sala\":15}";
        when(repositorioMock.buscarHorarioProfessor("Pedro")).thenReturn(json);

        String resultado = service.obterHorarioAtendimento("Pedro");
        assertTrue(resultado.contains("Prédio: 3"));
    }

    /*
        -------------------------
        TESTES NEGATIVOS
        -------------------------
    */

    @Test
    public void deveLancarExcecaoSeJsonForNulo() {
        when(repositorioMock.buscarHorarioProfessor("Joana")).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            service.obterHorarioAtendimento("Joana");
        });
    }

    @Test
    public void deveLancarExcecaoSeJsonForInvalido() {
        when(repositorioMock.buscarHorarioProfessor("Lucas")).thenReturn("json invalido");

        assertThrows(Exception.class, () -> {
            service.obterHorarioAtendimento("Lucas");
        });
    }

    @Test
    public void deveLancarExcecaoSeCampoEstiverAusente() {
        String jsonIncompleto = "{\"nomeDoProfessor\":\"Lucas\"}";
        when(repositorioMock.buscarHorarioProfessor("Lucas")).thenReturn(jsonIncompleto);

        assertThrows(Exception.class, () -> {
            service.obterHorarioAtendimento("Lucas");
        });
    }

    @Test
    public void deveLancarExcecaoSeSalaForTexto() {
        String json = "{\"nomeDoProfessor\":\"Sofia\",\"horarioDeAtendimento\":\"11h\",\"sala\":\"Sala 3\"}";
        when(repositorioMock.buscarHorarioProfessor("Sofia")).thenReturn(json);

        assertThrows(Exception.class, () -> {
            service.obterHorarioAtendimento("Sofia");
        });
    }

    @Test
    public void deveLancarExcecaoSeJsonForVazio() {
        when(repositorioMock.buscarHorarioProfessor("Julia")).thenReturn("");

        assertThrows(Exception.class, () -> {
            service.obterHorarioAtendimento("Julia");
        });
    }

    @Test
    public void deveLancarExcecaoSeNomeDoProfessorEstiverVazio() {
        String json = "{\"nomeDoProfessor\":\"\",\"horarioDeAtendimento\":\"11h\",\"sala\":3}";
        when(repositorioMock.buscarHorarioProfessor("")).thenReturn(json);

        assertThrows(Exception.class, () -> {
            service.obterHorarioAtendimento("");
        });
    }

    @Test
    public void deveLancarExcecaoSeSalaForNegativa() {
        String json = "{\"nomeDoProfessor\":\"Bruno\",\"horarioDeAtendimento\":\"12h\",\"sala\":-1}";
        when(repositorioMock.buscarHorarioProfessor("Bruno")).thenReturn(json);

        assertThrows(Exception.class, () -> {
            service.obterHorarioAtendimento("Bruno");
        });
    }

    @Test
    public void deveLancarExcecaoSeSalaForNula() {
        String json = "{\"nomeDoProfessor\":\"Fernanda\",\"horarioDeAtendimento\":\"15h\",\"sala\":null}";
        when(repositorioMock.buscarHorarioProfessor("Fernanda")).thenReturn(json);

        assertThrows(Exception.class, () -> {
            service.obterHorarioAtendimento("Fernanda");
        });
    }

    @Test
    public void deveLancarExcecaoSeHorarioForNulo() {
        String json = "{\"nomeDoProfessor\":\"Thiago\",\"horarioDeAtendimento\":null,\"sala\":5}";
        when(repositorioMock.buscarHorarioProfessor("Thiago")).thenReturn(json);

        assertThrows(Exception.class, () -> {
            service.obterHorarioAtendimento("Thiago");
        });
    }

    @Test
    public void deveLancarExcecaoSeNomeSalaEstiverErrada() {
        String json = "{\"nomeDoProfessor\":\"Daniel\",\"horarioDeAtendimento\":\"10h\",\"salinha\":2}";
        when(repositorioMock.buscarHorarioProfessor("Daniel")).thenReturn(json);

        assertThrows(Exception.class, () -> {
            service.obterHorarioAtendimento("Daniel");
        });
    }

}