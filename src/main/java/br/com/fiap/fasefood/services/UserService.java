package br.com.fiap.fasefood.services;

import br.com.fiap.fasefood.controllers.UserDTOMapper;
import br.com.fiap.fasefood.core.domain.User;
import br.com.fiap.fasefood.dtos.*;
import br.com.fiap.fasefood.entities.UserEntity;
import br.com.fiap.fasefood.entities.UserEntityMapper;
import br.com.fiap.fasefood.repositories.UserRepository;
import br.com.fiap.fasefood.services.exceptions.ResourceAlreadyExists;
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
        return userRepository
                .findAllByAtivoTrue(paginacao)
                .map(UserEntityMapper::toDomain)
                .map(ListUserDTO::new);
    }


    public Optional<ListUserDTO> findUserById(Long id) {
        logger.info("Buscando usuário pelo ID: {}", id);
        return userRepository
                .findByIdAndAtivoTrue(id)
                .map(UserEntityMapper::toDomain)
                .map(ListUserDTO::new);
    }


    public ListUserDTO saveUser(User user) {
        logger.info("Criando novo usuário com login: {}", user.getLogin());

        Optional<UserEntity> userByEmailExists = this.getUserByEmail(user.getEmail());
        Optional<UserEntity> userByLoginExists = this.getUserByLogin(user.getLogin());
        if(userByEmailExists.isPresent() || userByLoginExists.isPresent()){
            throw new ResourceAlreadyExists("Usuário já cadastrado, verifique o login ou e-mail!");
        }

        UserEntity userEntity = userRepository.save(UserEntityMapper.toEntity(user));
        logger.info("Usuário criado com ID: {}", userEntity.getId());
        return new ListUserDTO(UserEntityMapper.toDomain(userEntity));
    }


    public ListUserDTO updateUserDetails(UpdateUserDataDTO updateUserDTO, long id) {
        logger.info("Atualizando dados do usuário com ID: {}", id);
        UserEntity userEntity = getUserById(id);

        User user = UserEntityMapper.toDomain(userEntity);

        user.atualizarInformacoes(updateUserDTO);

        UserEntity saved = userRepository.save(UserEntityMapper.toEntity(user));

        return new ListUserDTO(UserEntityMapper.toDomain(saved));
    }


    public boolean deleteUser(Long id) {
        logger.info("Tentando excluir usuário com ID: {}", id);
        return userRepository.findByIdAndAtivoTrue(id)
                .map(userEntity -> {
                    User userDomain = UserEntityMapper.toDomain(userEntity);
                    userDomain.deleteUser();
                    userRepository.save(UserEntityMapper.toEntity(userDomain));
                    logger.info("Usuário com ID: {} excluído com sucesso", id);
                    return true;
                })
                .orElse(false);
    }


    private UserEntity getUserById(Long id) {
        return userRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> {
                    logger.error("Usuário não encontrado com ID: {}", id);
                    return new ResourceNotFoundException("Usuário não encontrado com ID: " + id);
                });
    }

    private Optional<UserEntity> getUserByEmail(String email) {
        return this.userRepository.findByEmailAndAtivoTrue(email);
    }

    private Optional<UserEntity> getUserByLogin(String login) {
        return this.userRepository.findByLoginAndAtivoTrue(login);
    }
}