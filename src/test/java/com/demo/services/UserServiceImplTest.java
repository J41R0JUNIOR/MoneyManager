package com.demo.services;

import com.demo.dto.InternTransferRequestDTO;
import com.demo.dto.UserRequestDTO;
import com.demo.dto.UserResponseDTO;
import com.demo.model.Card;
import com.demo.model.User;
import com.demo.model.Wallet;
import com.demo.repository.UserRepository;
import com.demo.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DataJpaTest
@ActiveProfiles("test")
public class UserServiceImplTest {
    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should make a transaction successfully")
    void transactionBetweenSelfWalletSuccess() throws Exception {
        User userMock = createMockUser();

        Wallet walletMock1 = createMockWallet(userMock, 1L);
        Card cardMock1 = createMockCard(walletMock1, 1L, 10f);

        Wallet walletMock2 = createMockWallet(userMock, 2L);
        Card cardMock2 = createMockCard(walletMock2, 2L, 0f);

        walletMock1.setCards(List.of(cardMock1));
        walletMock2.setCards(List.of(cardMock2));
        userMock.setWallets(List.of(walletMock1, walletMock2));

        when(repository.findUserByEmail("userMock@gmail.com")).thenReturn(Optional.of(userMock));
        when(repository.findById(1L)).thenReturn(Optional.of(userMock));
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));


        service.selfWalletTransfer(new InternTransferRequestDTO(5f, 1L, 2L, 1L, 2L));

        assertEquals(5f, cardMock1.getAmount());
        assertEquals(5f, cardMock2.getAmount());

        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Should throw Exception when no sufficient money")
    void transactionBetweenSelfWalletSError() throws Exception {
        User userMock = createMockUser();

        Wallet walletMock1 = createMockWallet(userMock, 1L);
        Card cardMock1 = createMockCard(walletMock1, 1L, 10f);

        Wallet walletMock2 = createMockWallet(userMock, 2L);
        Card cardMock2 = createMockCard(walletMock2, 2L, 0f);

        walletMock1.setCards(List.of(cardMock1));
        walletMock2.setCards(List.of(cardMock2));
        userMock.setWallets(List.of(walletMock1, walletMock2));

        when(repository.findUserByEmail("userMock@gmail.com")).thenReturn(Optional.of(userMock));
        when(repository.findById(1L)).thenReturn(Optional.of(userMock));
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        service.selfWalletTransfer(new InternTransferRequestDTO(100f, 1L, 2L, 1L, 2L));

        assertEquals(10f, cardMock1.getAmount());
        assertEquals(0f, cardMock2.getAmount());

        SecurityContextHolder.clearContext();
    }


    private User createMockUser() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("userMock@gmail.com");
        SecurityContextHolder.setContext(securityContext);

        return new User(
                1L,
                "userMock@gmail.com",
                "passwordUserMock",
                "UserMock",
                List.of(),
                List.of(),
                List.of()
        );


    }

    private Wallet createMockWallet(User user, Long id) {
        return new Wallet(id, user, "WalletMock", null);
    }

    private Card createMockCard(Wallet wallet, Long id, float amount) {
        return new Card(id, wallet, "credit", 100.0f, amount, "20", null);
    }
}
