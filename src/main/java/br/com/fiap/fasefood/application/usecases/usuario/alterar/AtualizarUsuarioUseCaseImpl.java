package br.com.fiap.fasefood.application.usecases.usuario.alterar;

import br.com.fiap.fasefood.application.usecases.usuario.listar.ListUserOutput;
import br.com.fiap.fasefood.core.entities.Endereco;
import br.com.fiap.fasefood.core.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.gateways.UsuarioRepository;


public class AtualizarUsuarioUseCaseImpl implements AtualizarUsuarioUseCase {
    private final UsuarioRepository usuarioRepository;

    public AtualizarUsuarioUseCaseImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public ListUserOutput atualizar(Long id, UpdateUserDataInput dados) {
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

        return new ListUserOutput(
                atualizado.getId(),
                atualizado.getNome(),
                atualizado.getEmail(),
                atualizado.getLogin(),
                atualizado.getTipoUsuario(),
                atualizado.getEndereco()
        );
    }
}