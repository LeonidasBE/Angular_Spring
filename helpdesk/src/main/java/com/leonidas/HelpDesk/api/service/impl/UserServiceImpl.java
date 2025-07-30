package com.leonidas.HelpDesk.api.service.impl;

import com.leonidas.HelpDesk.api.entity.User;
import com.leonidas.HelpDesk.api.repository.UserRepository;
import com.leonidas.HelpDesk.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public User createOrUpdate(User user) {
        return this.userRepository.save(user);
    }

    public Optional<User> findById(String id) {
        return this.userRepository.findById(id);
    }

    public void delete(String id) {
        this.userRepository.deleteById(id);
    }

    public Page<User> findAllUsers(int page, int count) {
        return this.userRepository.findAll(PageRequest.of(page, count));
    }
}
