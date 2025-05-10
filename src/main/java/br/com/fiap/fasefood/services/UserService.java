package br.com.fiap.fasefood.services;

import br.com.fiap.fasefood.dtos.*;
import br.com.fiap.fasefood.entities.User;
import br.com.fiap.fasefood.repositories.UserRepository;
import br.com.fiap.fasefood.services.exceptions.AuthenticationFailedException;
import br.com.fiap.fasefood.services.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Serviço responsável por operações relacionadas a usuários
 */
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Page<ListUserDTO> findAllUsers(Pageable paginacao) {
        logger.info("Buscando todos os usuários ativos com paginação: {}", paginacao);
        return userRepository.findAllByAtivoTrue(paginacao).map(ListUserDTO::new);
    }


    public Optional<ListUserDTO> findUserById(Long id) {
        logger.info("Buscando usuário pelo ID: {}", id);
        return userRepository.findByIdAndAtivoTrue(id).map(ListUserDTO::new);
    }


    public ListUserDTO saveUser(CreateUserDTO createUserDTO) {
        logger.info("Criando novo usuário com login: {}", createUserDTO.login());
        User user = new User(createUserDTO);
        user = userRepository.save(user);
        logger.info("Usuário criado com ID: {}", user.getId());
        return new ListUserDTO(user);
    }


    public ListUserDTO updateUserDetails(UpdateUserDataDTO updateUserDTO) {
        logger.info("Atualizando dados do usuário com ID: {}", updateUserDTO.id());
        User user = getUserById(updateUserDTO.id());
        user.atualizarInformacoes(updateUserDTO);
        userRepository.save(user);
        logger.info("Dados do usuário atualizados com sucesso");
        return new ListUserDTO(user);
    }


    public boolean deleteUser(Long id) {
        logger.info("Tentando excluir usuário com ID: {}", id);
        return userRepository.findByIdAndAtivoTrue(id)
                .map(user -> {
                    user.deleteUser();
                    userRepository.save(user);
                    logger.info("Usuário com ID: {} excluído com sucesso", id);
                    return true;
                })
                .orElse(false);
    }


    public void changeUserPassword(Long id, ChangeUserPasswordDTO userData) {
        logger.info("Alterando senha do usuário com ID: {}", id);
        User user = getUserById(id);
        user.changeUserPassword(userData);
        userRepository.save(user);
        logger.info("Senha do usuário alterada com sucesso");
    }



    public LoginResponseDTO authenticate(LoginRequestDTO loginRequestDTO) {
        logger.info("Tentativa de login para o usuário: {}", loginRequestDTO.login());

        Optional<User> userOptional = userRepository.findByLoginAndAtivoTrue(loginRequestDTO.login());

        if (userOptional.isEmpty()) {
            logger.warn("Tentativa de login falhou: login não encontrado - {}", loginRequestDTO.login());
            throw new AuthenticationFailedException("Login ou senha incorretos");
        }

        User user = userOptional.get();

        if (!user.getSenha().equals(loginRequestDTO.senha())) {
            logger.warn("Tentativa de login falhou: senha incorreta para usuário - {}", loginRequestDTO.login());
            throw new AuthenticationFailedException("Login ou senha incorretos");
        }

        logger.info("Login realizado com sucesso para o usuário: {}", loginRequestDTO.login());
        return new LoginResponseDTO(true, "Login realizado com sucesso");
    }



    private User getUserById(Long id) {
        return userRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> {
                    logger.error("Usuário não encontrado com ID: {}", id);
                    return new ResourceNotFoundException("Usuário não encontrado com ID: " + id);
                });
    }
}