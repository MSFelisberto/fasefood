package br.com.fiap.fasefood.application.usecases.usuario.criar;

import br.com.fiap.fasefood.application.usecases.shared.endereco.EnderecoInput;
import br.com.fiap.fasefood.core.entities.Endereco;
import br.com.fiap.fasefood.core.entities.TipoUsuario;
import br.com.fiap.fasefood.core.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.ResourceAlreadyExists;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.gateways.TipoUsuarioRepository;
import br.com.fiap.fasefood.core.gateways.UsuarioRepository;

public class CriarUsuarioUseCaseImpl implements CriarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final TipoUsuarioRepository tipoUsuarioRepository;

    public CriarUsuarioUseCaseImpl(UsuarioRepository usuarioRepository, TipoUsuarioRepository tipoUsuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.tipoUsuarioRepository = tipoUsuarioRepository;
    }

    @Override
    public CriarUsuarioOutput criarUsuario(CriarUsuarioInput criarUsuarioInput) {
        if (usuarioRepository.findByEmail(criarUsuarioInput.email()).isPresent()
                || usuarioRepository.findByLogin(criarUsuarioInput.login()).isPresent()) {
            throw new ResourceAlreadyExists("Usuário já cadastrado");
        }

        TipoUsuario tipoUsuario = tipoUsuarioRepository.findByNome(criarUsuarioInput.tipoUsuarioNome())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de usuário '" + criarUsuarioInput.tipoUsuarioNome() + "' não encontrado"));


        Usuario usuarioSalvo = usuarioRepository.salvar(
                Usuario.criarUsuario(
                        null,
                        criarUsuarioInput.nome(),
                        criarUsuarioInput.email(),
                        criarUsuarioInput.login(),
                        criarUsuarioInput.senha(),
                        null,
                        Endereco.criarEndereco(
                                null,
                                criarUsuarioInput.endereco().logradouro(),
                                criarUsuarioInput.endereco().numero(),
                                criarUsuarioInput.endereco().cep(),
                                criarUsuarioInput.endereco().complemento(),
                                criarUsuarioInput.endereco().bairro(),
                                criarUsuarioInput.endereco().cidade(),
                                criarUsuarioInput.endereco().uf()
                        ),
                        tipoUsuario,
                        true
                )
        );

        return new CriarUsuarioOutput(
                usuarioSalvo.getId(),
                usuarioSalvo.getNome(),
                usuarioSalvo.getEmail(),
                usuarioSalvo.getLogin(),
                usuarioSalvo.getSenha(),
                usuarioSalvo.getTipoUsuario(),
                new EnderecoInput(
                        usuarioSalvo.getEndereco().getLogradouro(),
                        usuarioSalvo.getEndereco().getNumero(),
                        usuarioSalvo.getEndereco().getCep(),
                        usuarioSalvo.getEndereco().getComplemento(),
                        usuarioSalvo.getEndereco().getBairro(),
                        usuarioSalvo.getEndereco().getCidade(),
                        usuarioSalvo.getEndereco().getUf()
                )

        );
    }
}
