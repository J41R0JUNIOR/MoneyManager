package com.demo.service;

import java.util.List;
import java.util.Optional;

import com.demo.dto.InternTransferRequestDTO;
import com.demo.model.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.Investment;
import com.demo.model.User;
import com.demo.model.Wallet;
import com.demo.repository.UserRepository;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepository repository;
    
    @Override
    public void save(User user){

        linkWallet(user);
        linkInvestment(user);

        repository.save(user);
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
        if (wallet.getCards() == null) {
            return;
        }

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
        repository.deleteAll();
    }

    @Override
    public void delete(Long id){
        repository.deleteById(id);
    }

    @Override
    public List<User> getAll(){
        return repository.findAll();
    }

    @Override
    public Optional<User> findById(Long id){
        return repository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) { return repository.findUserByEmail(email); }

    @Override
    public void selfWalletTransfer(InternTransferRequestDTO transferDTO) throws Exception {

        Optional<User> user = this.findById(transferDTO.id());

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
            repository.save(user.get());
        }
    }
}
