package com.demo.config;

import com.demo.dto.UserSignInRequestDTO;
import com.demo.dto.UserSignUpRequestDTO;
import com.demo.model.RoleName;
import com.demo.model.User;
import com.demo.repository.UserRepository;
import com.demo.security.JwtTokenServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")

public class SecurityConfigTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtTokenServiceImpl jwtTokenService;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    @DisplayName("Should return ok accessing public endpoint")
    public void publicEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/getAll"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return forbiden for non authorization access")
    public void privateEndpointAndNoAuthorization() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/deleteAll"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void privateEndpointAndAuthorized() throws Exception {
        String fakeToken = "tokenTest";
        String email = "UserTest@gmail.com";

        when(jwtTokenService.getSubject(fakeToken)).thenReturn(email);

        UserSignUpRequestDTO user = new UserSignUpRequestDTO("email", "password", RoleName.ROLE_USER);
        User newUser =  new User(user);
        newUser.setId(1L);

        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(newUser));

        String urlTest = "/user/" + newUser.getId();
        mockMvc.perform(

                MockMvcRequestBuilders.get(urlTest)
                        .header("Authorization", "Bearer " + fakeToken)
                )
                .andExpect(status().isOk());
    }
}
