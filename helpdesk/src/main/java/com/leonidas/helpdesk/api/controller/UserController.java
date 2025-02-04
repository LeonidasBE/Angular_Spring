package com.leonidas.HelpDesk.api.controller;

import com.leonidas.HelpDesk.api.entity.User;
import com.leonidas.HelpDesk.api.response.Response;
import com.leonidas.HelpDesk.api.service.UserService;
import com.mongodb.DuplicateKeyException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<User>> create(HttpServletRequest request, @RequestBody User user, BindingResult result) {
        Response<User> userResponse  = new Response<User>();
        try {
            validateCreateUser(user, result);
            if(result.hasErrors()) {
                result.getAllErrors().forEach(error -> userResponse.getErrors().add(error.getDefaultMessage()));
                return ResponseEntity.badRequest().body(userResponse);
            }
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            User persisteduser = userService.createOrUpdate(user);
            userResponse.setData(persisteduser);
        } catch (DuplicateKeyException duplicateKey) {
            userResponse.getErrors().add("E-mail already registered");
            return ResponseEntity.badRequest().body(userResponse);
        } catch (Exception e) {
            userResponse.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(userResponse);
        }
        return ResponseEntity.ok(userResponse);
    }

    private void validateCreateUser(User user, BindingResult result) {
        if(user.getEmail() == null)
            result.addError(new ObjectError("User", "Email not informed"));
    }

    private void validateUpdateUser(User user, BindingResult result) {
        if(user.getId() == null) {
            result.addError(new ObjectError("User", "Id not informed"));
        }

        if(user.getEmail() == null)
            result.addError(new ObjectError("User", "Email not informed"));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<User>> update(HttpServletRequest request, @RequestBody User user, BindingResult result) {
        Response<User> userResponse  = new Response<User>();
        try {
            validateUpdateUser(user, result);
            if(result.hasErrors()) {
                result.getAllErrors().forEach(error -> userResponse.getErrors().add(error.getDefaultMessage()));
                return ResponseEntity.badRequest().body(userResponse);
            }
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            User persisteduser = userService.createOrUpdate(user);
            userResponse.setData(persisteduser);
        } catch (DuplicateKeyException duplicateKey) {
            userResponse.getErrors().add("E-mail already registered");
            return ResponseEntity.badRequest().body(userResponse);
        } catch (Exception e) {
            userResponse.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(userResponse);
        }
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<User>> findById(@PathVariable("id") String id) {
        Response<User> userResponse = new Response<User>();
        Optional<User> optionalUser = userService.findById(id);
        if(optionalUser.isEmpty()){
            userResponse.getErrors().add("User not found");
            return ResponseEntity.badRequest().body(userResponse);
        }
        userResponse.setData(optionalUser.get());
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<String>> delete(@PathVariable("id") String id) {
        Response<String> response = new Response<String>();
        Optional<User> user = userService.findById(id);
        if (user.isEmpty()) {
            response.getErrors().add("Register not found id:" + id);
            return ResponseEntity.badRequest().body(response);
        }
        userService.delete(id);
        return ResponseEntity.ok(new Response<String>());
    }

    @GetMapping("{page}/{count}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<Page<User>>> findAll(@PathVariable("page") int page, @PathVariable("count") int count) {
        Response<Page<User>> pageResponse = new Response<>();
        Page<User> users = userService.findAllUsers(page, count);
        pageResponse.setData(users);
        return ResponseEntity.ok(pageResponse);
    }
}
