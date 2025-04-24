package com.demo.services;

import com.demo.dto.InternTransferRequestDTO;
import com.demo.model.Card;
import com.demo.model.User;
import com.demo.model.Wallet;
import com.demo.repository.UserRepository;
import com.demo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DataJpaTest
@ActiveProfiles("test")
public class UserServiceTest {
    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
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

        when(repository.findById(1L)).thenReturn(Optional.of(userMock));
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        service.selfWalletTransfer(new InternTransferRequestDTO(1L, 5, 1L, 2L, 1L, 2L));

        assertEquals(5f, cardMock1.getAmount());
        assertEquals(5f, cardMock2.getAmount());
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

        when(repository.findById(1L)).thenReturn(Optional.of(userMock));
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        service.selfWalletTransfer(new InternTransferRequestDTO(1L, 15, 1L, 2L, 1L, 2L));

        assertEquals(10f, cardMock1.getAmount());
        assertEquals(0f, cardMock2.getAmount());
    }

    private User createMockUser() {
        return new User(1L, "UserMock", "userMock@gmail.com", "passwordUserMock", null, null);
    }

    private Wallet createMockWallet(User user, Long id) {
        return new Wallet(id, user, "WalletMock", null);
    }

    private Card createMockCard(Wallet wallet, Long id, float amount) {
        return new Card(id, wallet, "credito", 100.0f, amount, "20", null);
    }
}
