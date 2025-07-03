package wefit.service.impl;

import wefit.dto.PessoaJuridicaDTO;
import wefit.dto.UsuarioCadastradoEventDTO;
import wefit.entity.Endereco;
import wefit.entity.PessoaJuridica;
import wefit.exception.UsuarioExistenteException;
import wefit.kafka.KafkaEventPublisher;
import wefit.mapper.UsuarioMapper;
import wefit.repository.PessoaJuridicaRepository;
import wefit.repository.UsuarioRepository;
import wefit.service.EnderecoService;
import wefit.service.PessoaJuridicaCreationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PessoaJuridicaCreationServiceImpl implements PessoaJuridicaCreationService {

    private final PessoaJuridicaRepository pessoaJuridicaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EnderecoService enderecoService;
    private final KafkaEventPublisher kafkaEventPublisher;
    private final UsuarioMapper usuarioMapper;

    public PessoaJuridicaCreationServiceImpl(PessoaJuridicaRepository pessoaJuridicaRepository,
                                             UsuarioRepository usuarioRepository,
                                             EnderecoService enderecoService,
                                             KafkaEventPublisher kafkaEventPublisher,
                                             UsuarioMapper usuarioMapper) {
        this.pessoaJuridicaRepository = pessoaJuridicaRepository;
        this.usuarioRepository = usuarioRepository;
        this.enderecoService = enderecoService;
        this.kafkaEventPublisher = kafkaEventPublisher;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    @Transactional
    public PessoaJuridica cadastrarPessoaJuridica(PessoaJuridicaDTO dto) {
        if (pessoaJuridicaRepository.findByCnpj(dto.getCnpj()).isPresent()) {
            throw new UsuarioExistenteException("Já existe uma Pessoa Jurídica cadastrada com o CNPJ: " + dto.getCnpj());
        }
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new UsuarioExistenteException("Já existe um usuário cadastrado com o email: " + dto.getEmail());
        }

        PessoaJuridica pessoaJuridica = usuarioMapper.toPessoaJuridica(dto);
        Endereco endereco = usuarioMapper.toEndereco(dto.getEndereco());

        pessoaJuridica.setEndereco(endereco);
        endereco.setUsuario(pessoaJuridica);

        PessoaJuridica savedPessoaJuridica = pessoaJuridicaRepository.save(pessoaJuridica);

        UsuarioCadastradoEventDTO eventDTO = new UsuarioCadastradoEventDTO(
                savedPessoaJuridica.getId(),
                "PESSOA_JURIDICA",
                savedPessoaJuridica.getNome(),
                savedPessoaJuridica.getEmail(),
                savedPessoaJuridica.getCnpj()
        );
        kafkaEventPublisher.publish("usuario.cadastrado", eventDTO);

        return savedPessoaJuridica;
    }
}