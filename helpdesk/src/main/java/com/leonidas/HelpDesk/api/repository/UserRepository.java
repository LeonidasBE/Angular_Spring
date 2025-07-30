package com.leonidas.HelpDesk.api.repository;

import com.leonidas.HelpDesk.api.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);

}
