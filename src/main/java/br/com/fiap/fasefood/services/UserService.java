package br.com.fiap.fasefood.services;

import br.com.fiap.fasefood.core.domain.User;
import br.com.fiap.fasefood.dtos.*;
import br.com.fiap.fasefood.entities.UserEntity;
import br.com.fiap.fasefood.entities.UserEntityMapper;
import br.com.fiap.fasefood.repositories.UserRepository;
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


    public ListUserDTO saveUser(User user) {
        logger.info("Criando novo usuário com login: {}", user.getLogin());
        userRepository.save(UserEntityMapper.toEntity(user));
        logger.info("Usuário criado com ID: {}", UserEntityMapper.toEntity(user).getId());
        return new ListUserDTO(UserEntityMapper.toEntity(user));
    }


    public ListUserDTO updateUserDetails(UpdateUserDataDTO updateUserDTO, long id) {
        logger.info("Atualizando dados do usuário com ID: {}", id);
        UserEntity userEntity = getUserById(id);
        userEntity.atualizarInformacoes(updateUserDTO);
        userRepository.save(userEntity);
        logger.info("Dados do usuário atualizados com sucesso");
        return new ListUserDTO(userEntity);
    }


    public boolean deleteUser(Long id) {
        logger.info("Tentando excluir usuário com ID: {}", id);
        return userRepository.findByIdAndAtivoTrue(id)
                .map(userEntity -> {
                    userEntity.deleteUser();
                    userRepository.save(userEntity);
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
}