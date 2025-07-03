package wefit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import wefit.dto.PessoaFisicaDTO;
import wefit.dto.PessoaJuridicaDTO;
import wefit.entity.PessoaFisica;
import wefit.entity.PessoaJuridica;
import wefit.entity.Endereco;
import wefit.dto.EnderecoDTO;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    // Mapeamento de EnderecoDTO para Endereco
    Endereco toEndereco(EnderecoDTO enderecoDTO);

    // Mapeamento de PessoaFisicaDTO para PessoaFisica
    @Mapping(target = "endereco", source = "endereco")
    PessoaFisica toPessoaFisica(PessoaFisicaDTO pessoaFisicaDTO);

    // Mapeamento de PessoaJuridicaDTO para PessoaJuridica
    @Mapping(target = "endereco", source = "endereco")
    PessoaJuridica toPessoaJuridica(PessoaJuridicaDTO pessoaJuridicaDTO);

    // Mapeamento de PessoaFisica para PessoaFisicaDTO
    @Mapping(target = "endereco", source = "endereco")
    PessoaFisicaDTO toPessoaFisicaDTO(PessoaFisica pessoaFisica);

    // Mapeamento de PessoaJuridica para PessoaJuridicaDTO
    @Mapping(target = "endereco", source = "endereco")
    PessoaJuridicaDTO toPessoaJuridicaDTO(PessoaJuridica pessoaJuridica);

    // Mapeamento de Endereco para EnderecoDTO
    EnderecoDTO toEnderecoDTO(Endereco endereco);
}