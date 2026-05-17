package com.spring.synctracker.user_service.service.implementation;

import com.spring.synctracker.user_service.dto.UserDTO;
import com.spring.synctracker.user_service.entity.User;
import com.spring.synctracker.user_service.exception.ObjectNotFoundException;
import com.spring.synctracker.user_service.jwt.JwtProvider;
import com.spring.synctracker.user_service.mapper.UserMapper;
import com.spring.synctracker.user_service.repository.UserRepository;
import com.spring.synctracker.user_service.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class, ObjectNotFoundException.class})
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public UserDTO getUserProfileFromAuthentication(String jwt) {
        String email = jwtProvider.getEmailFromToken(jwt);
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException("User not found with the email : " + email));
        UserDTO userDTO = UserMapper.mapUserToUserDTO(user);
        return userDTO;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOList = users
                .stream()
                .map(UserMapper::mapUserToUserDTO)
                .toList();
        return userDTOList;
    }

    @Override
    public UserDTO getUserById(String id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("User not found with the id : " + id));
        UserDTO userDTO = UserMapper.mapUserToUserDTO(user);
        return userDTO;
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException("User not found with the email : " + email));
        UserDTO userDTO = UserMapper.mapUserToUserDTO(user);
        return userDTO;
    }


}
