package com.leonidas.HelpDesk.api.service;

import com.leonidas.HelpDesk.api.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserService {

    User findByEmail(String email);

    User createOrUpdate(User user);

    Optional<User> findById(String id);

    void delete(String id);

    Page<User> findAllUsers(int page, int count);
}
