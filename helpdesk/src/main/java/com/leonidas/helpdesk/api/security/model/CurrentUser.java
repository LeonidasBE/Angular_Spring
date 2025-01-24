package com.leonidas.HelpDesk.api.security.model;

import com.leonidas.HelpDesk.api.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CurrentUser {

    private String token;
    private User user;

}
