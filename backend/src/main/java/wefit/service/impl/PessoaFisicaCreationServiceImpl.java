package wefit.service.impl;

import wefit.dto.PessoaFisicaDTO;
import wefit.dto.UsuarioCadastradoEventDTO;
import wefit.entity.Endereco;
import wefit.entity.PessoaFisica;
import wefit.exception.UsuarioExistenteException;
import wefit.kafka.KafkaEventPublisher;
import wefit.mapper.UsuarioMapper;
import wefit.repository.PessoaFisicaRepository;
import wefit.repository.UsuarioRepository;
import wefit.service.EnderecoService;
import wefit.service.PessoaFisicaCreationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PessoaFisicaCreationServiceImpl implements PessoaFisicaCreationService {

    private final PessoaFisicaRepository pessoaFisicaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EnderecoService enderecoService;
    private final KafkaEventPublisher kafkaEventPublisher;
    private final UsuarioMapper usuarioMapper;

    public PessoaFisicaCreationServiceImpl(PessoaFisicaRepository pessoaFisicaRepository,
                                           UsuarioRepository usuarioRepository,
                                           EnderecoService enderecoService,
                                           KafkaEventPublisher kafkaEventPublisher,
                                           UsuarioMapper usuarioMapper) {
        this.pessoaFisicaRepository = pessoaFisicaRepository;
        this.usuarioRepository = usuarioRepository;
        this.enderecoService = enderecoService;
        this.kafkaEventPublisher = kafkaEventPublisher;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    @Transactional
    public PessoaFisica cadastrarPessoaFisica(PessoaFisicaDTO dto) {
        if (pessoaFisicaRepository.findByCpf(dto.getCpf()).isPresent()) {
            throw new UsuarioExistenteException("Já existe uma Pessoa Física cadastrada com o CPF: " + dto.getCpf());
        }
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new UsuarioExistenteException("Já existe um usuário cadastrado com o email: " + dto.getEmail());
        }

        PessoaFisica pessoaFisica = usuarioMapper.toPessoaFisica(dto);
        Endereco endereco = usuarioMapper.toEndereco(dto.getEndereco());

        pessoaFisica.setEndereco(endereco);
        endereco.setUsuario(pessoaFisica);

        PessoaFisica savedPessoaFisica = pessoaFisicaRepository.save(pessoaFisica);

        UsuarioCadastradoEventDTO eventDTO = new UsuarioCadastradoEventDTO(
                savedPessoaFisica.getId(),
                "PESSOA_FISICA",
                savedPessoaFisica.getNome(),
                savedPessoaFisica.getEmail(),
                savedPessoaFisica.getCpf()
        );
        kafkaEventPublisher.publish("usuario.cadastrado", eventDTO);

        return savedPessoaFisica;
    }
}
