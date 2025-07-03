package wefit.service.impl;

import wefit.entity.Endereco;
import wefit.repository.EnderecoRepository;
import wefit.service.EnderecoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnderecoServiceImpl implements EnderecoService {

    private final EnderecoRepository enderecoRepository;

    public EnderecoServiceImpl(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    @Override
    @Transactional
    public Endereco salvarEndereco(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }
}