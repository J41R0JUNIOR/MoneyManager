package com.demo.service;

import java.util.List;
import java.util.Optional;
import com.demo.config.SecurityConfig;
import com.demo.dto.*;
import com.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.demo.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserServiceInterface {

	@Autowired
	private UserRepository userRepository;

    @Autowired
    private SecurityConfig securityConfig;



    @Override
    public void updateUser(UserRequestDTO user){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User userUpdate = userRepository.findUserByEmail(email).orElseThrow();

        if (user.wallets() != null) {
            userUpdate.setWallets(user.wallets());
            linkWallet(userUpdate);
        }

        if (user.investments() != null) {
            userUpdate.setInvestments(user.investments());
            linkInvestment(userUpdate);
        }

        if (user.password() != null && !user.password().isBlank()) {
            String encodedPassword = securityConfig.passwordEncoder().encode(user.password());
            userUpdate.setPassword(encodedPassword);
        }

        linkWallet(userUpdate);
        linkInvestment(userUpdate);

        userRepository.save(userUpdate);
    }

    @Override
    public void selfWalletTransfer(InternTransferRequestDTO transferDTO) throws Exception {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Long id = userRepository.findUserByEmail(email).orElseThrow().getId();

        Optional<User> user = this.findById(id);

        if (user.isEmpty() || user.get().getWallets().isEmpty()) {
            throw new Exception("No user found or wallet empty");
        }

        Wallet senderWallet = null;
        Wallet receiverWallet = null;

        for (Wallet wallet : user.get().getWallets()) {
            if (wallet.getId().equals(transferDTO.walletSenderId())) {
                senderWallet = wallet;
            } else if (wallet.getId().equals(transferDTO.walletReceiverId())) {
                receiverWallet = wallet;
            }
        }

        if (senderWallet == null || receiverWallet == null) {
            throw new Exception("Error finding wallets");
        }

        Card cardSender = null;
        Card cardReceiver = null;

        for (Card card : senderWallet.getCards()) {
            if (card.getId().equals(transferDTO.cardSenderId())) {
                cardSender = card;
            }
        }
        for (Card card : receiverWallet.getCards()) {
            if (card.getId().equals(transferDTO.cardReceiverId())) {
                cardReceiver = card;
            }
        }

        if (cardSender == null || cardReceiver == null) {
            throw new Exception("Error finding cards");
        }

        if (cardSender.getAmount() >= transferDTO.amount()){
            cardSender.decrementAmount(transferDTO.amount());
            cardReceiver.incrementAmount(transferDTO.amount());
            userRepository.save(user.get());
        }
    }

    private void linkWallet(User user) {
        if (user.getWallets() == null) {
            return;
        }

        for (Wallet wallet : user.getWallets()) {
            wallet.setUser(user);
            linkCard(wallet);
        }
    }

    private void linkCard(Wallet wallet){
        if (wallet.getCards().isEmpty()) return;

        for (Card card : wallet.getCards()) {
            card.setWallet(wallet);
        }
    }

    private void linkInvestment(User user){
        if (user.getInvestments() == null) {
            return;
        }

        for (Investment investment : user.getInvestments()) {
            investment.setUser(user);
        }
    }

    @Override
    public void deleteAll(){
        userRepository.deleteAll();
    }

    @Override
    public void delete(Long id){
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAll(){
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }
}
