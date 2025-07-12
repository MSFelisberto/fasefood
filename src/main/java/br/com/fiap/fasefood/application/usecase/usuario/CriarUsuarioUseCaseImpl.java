package br.com.fiap.fasefood.application.usecase.usuario;

import br.com.fiap.fasefood.core.domain.entities.Usuario;
import br.com.fiap.fasefood.core.exceptions.ResourceAlreadyExists;
import br.com.fiap.fasefood.core.usecase.gateways.UsuarioRepository;
import br.com.fiap.fasefood.core.usecase.interfaces.CriarUsuarioUseCase;
import org.springframework.stereotype.Service;

@Service
public class CriarUsuarioUseCaseImpl implements CriarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;

    public CriarUsuarioUseCaseImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario criarUsuario(Usuario user) {
        if (usuarioRepository.findByEmail(user.getEmail()).isPresent()
                || usuarioRepository.findByLogin(user.getLogin()).isPresent()) {
            throw new ResourceAlreadyExists("Usuário já cadastrado");
        }

        return usuarioRepository.salvar(user);
    }
}
