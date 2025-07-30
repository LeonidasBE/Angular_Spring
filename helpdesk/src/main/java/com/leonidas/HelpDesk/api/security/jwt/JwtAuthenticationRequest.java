package com.leonidas.HelpDesk.api.security.jwt;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenticationRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -438916409272195713L;

    private String email;
    private String password;

}
