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
import wefit.dto.PessoaJuridicaDTO;
import wefit.entity.PessoaJuridica;
import wefit.repository.PessoaJuridicaRepository;
import wefit.service.PessoaJuridicaCreationService;
import wefit.factory.EnderecoTestDataFactory;
import wefit.factory.PessoaJuridicaTestDataFactory;

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
class PessoaJuridicaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    @Autowired
    private PessoaJuridicaCreationService pessoaJuridicaCreationService;

    @Test
    void cadastrarPessoaJuridica_validData_returnsCreated() throws Exception {
        EnderecoDTO enderecoDTO = EnderecoTestDataFactory.createValidEnderecoDTO(
                "87654321", "Av. Principal", "456", null, "Outra Cidade", "Centro", "OT");

        PessoaJuridicaDTO pessoaJuridicaDTO = PessoaJuridicaTestDataFactory.createValidPessoaJuridicaDTO(
                "Integration Test PJ", "integration.pj@example.com", "88508547000143", "01097058077", "11998877665", enderecoDTO);

        mockMvc.perform(post("/pessoas-juridicas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaJuridicaDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Pessoa Jurídica cadastrada com sucesso!"));

        PessoaJuridica foundPj = pessoaJuridicaRepository.findByCnpj("88508547000143")
                .orElseThrow(() -> new AssertionError("Pessoa Jurídica não encontrada no banco após cadastro."));
        assertNotNull(foundPj.getCnpj());
        assertEquals(14, foundPj.getCnpj().length());
    }

    @Test
    void cadastrarPessoaJuridica_invalidCnpj_returnsBadRequest() throws Exception {
        EnderecoDTO enderecoDTO = EnderecoTestDataFactory.createValidEnderecoDTO(
                "87654321", "Av. Principal", "456", null, "Outra Cidade", "Centro", "OT");

        PessoaJuridicaDTO pessoaJuridicaDTO = PessoaJuridicaTestDataFactory.createInvalidCnpjPessoaJuridicaDTO(
                "Invalid CNPJ", "invalid.cnpj@example.com", "123", "98765432109", "11998877665", enderecoDTO);

        mockMvc.perform(post("/pessoas-juridicas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaJuridicaDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("CNPJ inválido.")));
    }

    @Test
    void cadastrarPessoaJuridica_existingCnpj_returnsConflict() throws Exception {
        EnderecoDTO enderecoDTO = EnderecoTestDataFactory.createValidEnderecoDTO(
                "87654321", "Av. Principal", "456", null, "Outra Cidade", "Centro", "OT");

        PessoaJuridicaDTO pessoaJuridicaDTO = PessoaJuridicaTestDataFactory.createValidPessoaJuridicaDTO(
                "Existing CNPJ", "existing.cnpj@example.com", "88508547000143", "01097058077", "11998877665", enderecoDTO);

        pessoaJuridicaCreationService.cadastrarPessoaJuridica(pessoaJuridicaDTO);

        mockMvc.perform(post("/pessoas-juridicas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaJuridicaDTO)))
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString("Já existe uma Pessoa Jurídica cadastrada com o CNPJ: 88508547000143")));
    }

    @Test
    void cadastrarPessoaJuridica_invalidCep_returnsBadRequest() throws Exception {
        EnderecoDTO enderecoDTO = EnderecoTestDataFactory.createInvalidCepEnderecoDTO();

        PessoaJuridicaDTO pessoaJuridicaDTO = PessoaJuridicaTestDataFactory.createValidPessoaJuridicaDTO(
                "Invalid CEP PJ", "invalid.cep.pj@example.com", "55555555000155", "66666666666", "11999999999", enderecoDTO);

        mockMvc.perform(post("/pessoas-juridicas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaJuridicaDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("CEP inválido.")));
    }
}