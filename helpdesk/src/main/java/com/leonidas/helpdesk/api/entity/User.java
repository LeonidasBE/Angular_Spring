package com.leonidas.HelpDesk.api.entity;

import com.leonidas.HelpDesk.api.enums.ProfileEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    @NotBlank(message = "Email Required")
    @Email(message = "Email invalid")
    private String email;

    @NotBlank(message = "Password required")
    @Size(min = 6)
    private String password;

    private ProfileEnum profile;
}
