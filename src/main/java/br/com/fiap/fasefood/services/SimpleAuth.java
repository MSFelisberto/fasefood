package br.com.fiap.fasefood.services;

import br.com.fiap.fasefood.core.domain.User;
import br.com.fiap.fasefood.dtos.ChangeUserPasswordDTO;
import br.com.fiap.fasefood.dtos.LoginRequestDTO;
import br.com.fiap.fasefood.dtos.LoginResponseDTO;
import br.com.fiap.fasefood.entities.UserEntity;
import br.com.fiap.fasefood.entities.UserEntityMapper;
import br.com.fiap.fasefood.repositories.UserRepository;
import br.com.fiap.fasefood.services.exceptions.AuthenticationFailedException;
import br.com.fiap.fasefood.services.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SimpleAuth implements AuthStrategy {

    private static final Logger logger = LoggerFactory.getLogger(SimpleAuth.class);
    private final UserRepository userRepository;

    public SimpleAuth(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public LoginResponseDTO authenticate(LoginRequestDTO loginRequestDTO) {
        logger.info("Tentativa de login para o usuário: {}", loginRequestDTO.login());

        Optional<UserEntity> userOptional = userRepository.findByLoginAndAtivoTrue(loginRequestDTO.login());

        if (userOptional.isEmpty()) {
            logger.warn("Tentativa de login falhou: login não encontrado - {}", loginRequestDTO.login());
            throw new AuthenticationFailedException("Login ou senha incorretos");
        }

        UserEntity userEntity = userOptional.get();

        if (!userEntity.getSenha().equals(loginRequestDTO.senha())) {
            logger.warn("Tentativa de login falhou: senha incorreta para usuário - {}", loginRequestDTO.login());
            throw new AuthenticationFailedException("Login ou senha incorretos");
        }

        logger.info("Login realizado com sucesso para o usuário: {}", loginRequestDTO.login());
        return new LoginResponseDTO(true, "Login realizado com sucesso");
    }

    @Override
    public void changeUserPassword(Long id, ChangeUserPasswordDTO userData) {
        logger.info("Alterando senha do usuário com ID: {}", id);
        UserEntity userEntity = userRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> {
                    logger.error("Usuário não encontrado com ID: {}", id);
                    return new ResourceNotFoundException("Usuário não encontrado com ID: " + id);
                });

        User userDomain = UserEntityMapper.toDomain(userEntity);
        userDomain.changeUserPassword(userData);

        userRepository.save(UserEntityMapper.toEntity(userDomain));
        logger.info("Senha do usuário alterada com sucesso");
    }
}
