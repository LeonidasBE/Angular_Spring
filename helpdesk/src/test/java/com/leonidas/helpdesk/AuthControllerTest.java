package com.leonidas.HelpDesk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leonidas.HelpDesk.api.entity.User;
import com.leonidas.HelpDesk.api.security.controller.AuthenticationRestController;
import com.leonidas.HelpDesk.api.security.jwt.JwtAuthenticationRequest;
import com.leonidas.HelpDesk.api.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @MockitoBean
    private com.leonidas.HelpDesk.api.security.jwt.JwtTokenUtil jwtTokenUtil;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper; // To convert objects to JSON

    @Test
    public void testCreateAuthenticationToken_Success() throws Exception {
        // Prepare test data
        String email = "test@example.com";
        String password = "password";
        String token = "mock-jwt-token";

        JwtAuthenticationRequest authRequest = new JwtAuthenticationRequest(email, password);
        User mockUser = new User(); // Assuming your `User` model has these fields
        mockUser.setEmail(email);
        mockUser.setId("1");
        mockUser.setPassword(null);

        UserDetails mockUserDetails = org.springframework.security.core.userdetails.User
                .withUsername(email)
                .password(password)
                .authorities("ROLE_USER")
                .build();

        // Mock dependencies
        Mockito.when(authenticationManager.authenticate(Mockito.any()))
                .thenReturn(new UsernamePasswordAuthenticationToken(email, password));
        Mockito.when(userDetailsService.loadUserByUsername(email)).thenReturn(mockUserDetails);
        Mockito.when(jwtTokenUtil.generateToken(mockUserDetails)).thenReturn(token);
        Mockito.when(userService.findByEmail(email)).thenReturn(mockUser);

        // Perform POST request
        mockMvc.perform(post("/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token))
                .andExpect(jsonPath("$.user.email").value(email))
                .andExpect(jsonPath("$.user.id").value("1"));
    }
}

