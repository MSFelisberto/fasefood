package br.com.fiap.fasefood.application.usecase.usuario;

import br.com.fiap.fasefood.core.domain.entities.Endereco;
import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.UsuarioRepository;
import br.com.fiap.fasefood.core.usecase.interfaces.AtualizarUsuarioUseCase;
import br.com.fiap.fasefood.infra.controller.dto.ListUserDTO;
import br.com.fiap.fasefood.infra.controller.dto.UpdateUserDataDTO;
import br.com.fiap.fasefood.infra.controller.mapper.UsuarioMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AtualizarUsuarioUseCaseImpl implements AtualizarUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;

    public AtualizarUsuarioUseCaseImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public ListUserDTO atualizar(Long id, UpdateUserDataDTO dados) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));

        usuario.atualizarInformacoes(
                dados.nome(),
                dados.email()
        );

        Endereco enderecoUsuario = usuario.getEndereco();

        if (enderecoUsuario != null) {
            enderecoUsuario.atualizar(
                    dados.endereco().logradouro(),
                    dados.endereco().numero(),
                    dados.endereco().cep(),
                    dados.endereco().complemento(),
                    dados.endereco().bairro(),
                    dados.endereco().cidade(),
                    dados.endereco().uf()
            );
        }

        Usuario atualizado = usuarioRepository.salvar(usuario);

        return UsuarioMapper.toListUserDTO(atualizado);
    }
}