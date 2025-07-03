package wefit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import wefit.entity.PessoaFisica;
import wefit.repository.PessoaFisicaRepository;
import wefit.service.PessoaFisicaCreationService;
import wefit.factory.EnderecoTestDataFactory;
import wefit.factory.PessoaFisicaTestDataFactory;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Import(TestKafkaConfig.class)
class PessoaFisicaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    private PessoaFisicaCreationService pessoaFisicaCreationService;

    @Test
    void cadastrarPessoaFisica_validData_returnsCreated() throws Exception {
        EnderecoDTO enderecoDTO = EnderecoTestDataFactory.createValidEnderecoDTO(
                "12345678", "Rua Teste", "123", null, "Cidade Teste", "Bairro Teste", "TS");

        PessoaFisicaDTO pessoaFisicaDTO = PessoaFisicaTestDataFactory.createValidPessoaFisicaDTO(
                "Integration Test PF", "integration.pf@example.com", "01097058077", "11999999999", enderecoDTO);

        mockMvc.perform(post("/pessoas-fisicas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaFisicaDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Pessoa Física cadastrada com sucesso!"));

        PessoaFisica foundPf = pessoaFisicaRepository.findByCpf("01097058077")
                .orElseThrow(() -> new AssertionError("Pessoa Física não encontrada no banco após cadastro."));
        assertNotNull(foundPf.getCpf());
        assertEquals(11, foundPf.getCpf().length());
    }

    @Test
    void cadastrarPessoaFisica_invalidCpf_returnsBadRequest() throws Exception {
        EnderecoDTO enderecoDTO = EnderecoTestDataFactory.createValidEnderecoDTO(
                "12345678", "Rua Teste", "123", null, "Cidade Teste", "Bairro Teste", "TS");

        PessoaFisicaDTO pessoaFisicaDTO = PessoaFisicaTestDataFactory.createInvalidCpfPessoaFisicaDTO(
                "Invalid CPF", "invalid.cpf@example.com", "123", "11999999999", enderecoDTO);

        mockMvc.perform(post("/pessoas-fisicas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaFisicaDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("CPF inválido.")));
    }

    @Test
    void cadastrarPessoaFisica_existingCpf_returnsConflict() throws Exception {
        EnderecoDTO enderecoDTO = EnderecoTestDataFactory.createValidEnderecoDTO(
                "12345678", "Rua Teste", "123", null, "Cidade Teste", "Bairro Teste", "TS");

        PessoaFisicaDTO pessoaFisicaDTO = PessoaFisicaTestDataFactory.createValidPessoaFisicaDTO(
                "Existing CPF", "existing.cpf@example.com", "01097058077", "11999999999", enderecoDTO);

        pessoaFisicaCreationService.cadastrarPessoaFisica(pessoaFisicaDTO);

        mockMvc.perform(post("/pessoas-fisicas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaFisicaDTO)))
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString("Já existe uma Pessoa Física cadastrada com o CPF: 01097058077")));
    }

    @Test
    void cadastrarPessoaFisica_invalidCep_returnsBadRequest() throws Exception {
        EnderecoDTO enderecoDTO = EnderecoTestDataFactory.createInvalidCepEnderecoDTO();

        PessoaFisicaDTO pessoaFisicaDTO = PessoaFisicaTestDataFactory.createValidPessoaFisicaDTO(
                "Invalid CEP PF", "invalid.cep.pf@example.com", "44444444444", "11999999999", enderecoDTO);

        mockMvc.perform(post("/pessoas-fisicas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaFisicaDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("CEP inválido.")));
    }
}