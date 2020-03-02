package com.intrafind.user.service;

import com.intrafind.user.exception.NotFoundException;
import com.intrafind.user.model.dao.User;
import com.intrafind.user.model.entity.UserEntity;
import com.intrafind.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll().stream()
                .map(userEntity -> modelMapper.map(userEntity, User.class))
                .collect(Collectors.toList());
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public User getUser(int id) {
        return modelMapper.map(userRepository.findById(id), User.class);
    }

    public User updateUser(User user, int id) {
        UserEntity userEntity1 = userRepository.findById(id)
                .map(userEntity -> {
                    userEntity.setFirstName(user.getFirstName());
                    userEntity.setEmailAddress(user.getEmailAddress());
                    userEntity.setLastName(user.getLastName());
                    return userRepository.save(userEntity);
                }).orElseThrow(() -> new NotFoundException("We can't find this id in our db "));
        return modelMapper.map(userEntity1, User.class);
    }

    public User addUser(User user) {
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        userEntity = userRepository.save(userEntity);
        return modelMapper.map(userEntity, User.class);
    }
}
