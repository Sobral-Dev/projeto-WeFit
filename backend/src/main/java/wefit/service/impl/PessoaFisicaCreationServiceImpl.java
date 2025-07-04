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
import wefit.service.PessoaFisicaCreationService;
import wefit.config.AppConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PessoaFisicaCreationServiceImpl implements PessoaFisicaCreationService {

    private final PessoaFisicaRepository pessoaFisicaRepository;
    private final UsuarioRepository usuarioRepository;
    private final KafkaEventPublisher kafkaEventPublisher;
    private final UsuarioMapper usuarioMapper;

    public PessoaFisicaCreationServiceImpl(PessoaFisicaRepository pessoaFisicaRepository,
                                           UsuarioRepository usuarioRepository,
                                           KafkaEventPublisher kafkaEventPublisher,
                                           UsuarioMapper usuarioMapper) {
        this.pessoaFisicaRepository = pessoaFisicaRepository;
        this.usuarioRepository = usuarioRepository;
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
        kafkaEventPublisher.publish(AppConstants.TOPIC_USUARIO_CADASTRADO, eventDTO);

        return savedPessoaFisica;
    }
}
