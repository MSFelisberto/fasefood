package br.com.fiap.fasefood.application.usecase.usuario.criar;

import br.com.fiap.fasefood.core.domain.entities.TipoUsuario;
import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.ResourceAlreadyExists;
import br.com.fiap.fasefood.core.exceptions.ResourceNotFoundException;
import br.com.fiap.fasefood.core.usecase.gateways.TipoUsuarioRepository;
import br.com.fiap.fasefood.core.usecase.gateways.UsuarioRepository;
import br.com.fiap.fasefood.infra.controller.dto.CreateUserDTO;
import br.com.fiap.fasefood.infra.controller.mapper.UsuarioMapper;
import org.springframework.stereotype.Service;

@Service
public class CriarUsuarioUseCaseImpl implements CriarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final TipoUsuarioRepository tipoUsuarioRepository;

    public CriarUsuarioUseCaseImpl(UsuarioRepository usuarioRepository, TipoUsuarioRepository tipoUsuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.tipoUsuarioRepository = tipoUsuarioRepository;
    }

    @Override
    public Usuario criarUsuario(CreateUserDTO dto) {
        if (usuarioRepository.findByEmail(dto.email()).isPresent()
                || usuarioRepository.findByLogin(dto.login()).isPresent()) {
            throw new ResourceAlreadyExists("Usuário já cadastrado");
        }

        TipoUsuario tipoUsuario = tipoUsuarioRepository.findByNome(dto.tipoUsuarioNome())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de usuário '" + dto.tipoUsuarioNome() + "' não encontrado"));
        Usuario usuario = UsuarioMapper.toDomain(dto, tipoUsuario);

        return usuarioRepository.salvar(usuario);
    }
}
