package wefit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import wefit.config.TestKafkaConfig;
import wefit.dto.EnderecoDTO;
import wefit.dto.PessoaFisicaDTO;
import wefit.dto.PessoaJuridicaDTO;
import wefit.entity.PessoaFisica;
import wefit.entity.PessoaJuridica;
import wefit.service.PessoaFisicaCreationService;
import wefit.service.PessoaJuridicaCreationService;
import wefit.repository.UsuarioRepository;
import wefit.factory.EnderecoTestDataFactory;
import wefit.factory.PessoaFisicaTestDataFactory;
import wefit.factory.PessoaJuridicaTestDataFactory;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Import(TestKafkaConfig.class)
class UsuarioQueryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PessoaFisicaCreationService pessoaFisicaCreationService;

    @Autowired
    private PessoaJuridicaCreationService pessoaJuridicaCreationService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private PessoaFisica pessoaFisicaSalva;
    private PessoaJuridica pessoaJuridicaSalva;

    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll();

        EnderecoDTO enderecoPF = EnderecoTestDataFactory.createValidEnderecoDTO(
                "11111111", "Rua PF", "1", null, "Cidade PF", "Bairro PF", "PF");
        PessoaFisicaDTO dtoPF = PessoaFisicaTestDataFactory.createValidPessoaFisicaDTO(
                "PF Teste", "pf.teste@example.com", "01097058077", "11111111111", enderecoPF);
        pessoaFisicaSalva = pessoaFisicaCreationService.cadastrarPessoaFisica(dtoPF);

        EnderecoDTO enderecoPJ = EnderecoTestDataFactory.createValidEnderecoDTO(
                "22222222", "Rua PJ", "2", null, "Cidade PJ", "Bairro PJ", "PJ");
        PessoaJuridicaDTO dtoPJ = PessoaJuridicaTestDataFactory.createValidPessoaJuridicaDTO(
                "PJ Teste", "pj.teste@example.com", "88508547000143", "71674004087", "22222222222", enderecoPJ);
        pessoaJuridicaSalva = pessoaJuridicaCreationService.cadastrarPessoaJuridica(dtoPJ);
    }

    @Test
    void listarTodosUsuarios_returnsAllUsers() throws Exception {
        mockMvc.perform(get("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is("PF Teste")))
                .andExpect(jsonPath("$[1].nome", is("PJ Teste")));
    }

    @Test
    void listarTodasPessoasFisicas_returnsOnlyPessoaFisica() throws Exception {
        mockMvc.perform(get("/usuarios/pessoas-fisicas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is("PF Teste")));
    }

    @Test
    void listarTodasPessoasJuridicas_returnsOnlyPessoaJuridica() throws Exception {
        mockMvc.perform(get("/usuarios/pessoas-juridicas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is("PJ Teste")));
    }

    @Test
    void buscarUsuarioPorId_existingId_returnsUser() throws Exception {
        mockMvc.perform(get("/usuarios/{id}", pessoaFisicaSalva.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("PF Teste")));
    }

    @Test
    void buscarUsuarioPorId_nonExistingId_returnsNotFound() throws Exception {
        mockMvc.perform(get("/usuarios/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", is("Ocorreu um erro inesperado. Tente novamente mais tarde.")));
    }

    @Test
    void deletarUsuario_existingId_returnsOk() throws Exception {
        mockMvc.perform(delete("/usuarios/{id}", pessoaFisicaSalva.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuário deletado com sucesso!"));
    }

    @Test
    void deletarUsuario_nonExistingId_returnsInternalServerError() throws Exception {
        mockMvc.perform(delete("/usuarios/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("Ocorreu um erro inesperado. Tente novamente mais tarde.")));
    }
}