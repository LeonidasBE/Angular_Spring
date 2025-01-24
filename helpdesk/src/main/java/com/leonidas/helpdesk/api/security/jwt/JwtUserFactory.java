package com.leonidas.HelpDesk.api.security.jwt;

import com.leonidas.HelpDesk.api.entity.User;
import com.leonidas.HelpDesk.api.enums.ProfileEnum;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class JwtUserFactory {

    /**
     * Cria o usuario
     * @param user
     * @return
     */
    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getProfile())
        );
    }

    /**
     * Converte o perfil de usuario para o que é usado no Spring Security
     */
    private static List<GrantedAuthority> mapToGrantedAuthorities(ProfileEnum profileEnum){
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(profileEnum.toString()));
        return authorities;
    }

}
