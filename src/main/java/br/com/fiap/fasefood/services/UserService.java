package br.com.fiap.fasefood.services;

import br.com.fiap.fasefood.dtos.ChangeUserPasswordDTO;
import br.com.fiap.fasefood.dtos.CreateUserDTO;
import br.com.fiap.fasefood.dtos.ListUserDTO;
import br.com.fiap.fasefood.dtos.UpdateUserDataDTO;
import br.com.fiap.fasefood.entities.User;
import br.com.fiap.fasefood.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<ListUserDTO> findAllUsers(Pageable paginacao) {
        return userRepository.findAllByAtivoTrue(paginacao).map(ListUserDTO::new);
    }

    public Optional<ListUserDTO> findUserById(Long id) {
        return userRepository.findById(id).map(ListUserDTO::new);
    }

    public void saveUser(CreateUserDTO createUserDTO) {
        userRepository.save(new User(createUserDTO));
    }

    public void updateUserDetais(UpdateUserDataDTO updateUserDTO) {
        var user = userRepository.getReferenceById(updateUserDTO.id());
        user.atualizarInformacoes(updateUserDTO);
    }

    public void deleteUser(Long id) {
        var user = userRepository.getReferenceById(id);
        user.deleteUser();
    }

    public void changeUserPassword(ChangeUserPasswordDTO userData) {
        var user = userRepository.getReferenceById(userData.id());
        user.setSenha(userData.senha());
    }

}
