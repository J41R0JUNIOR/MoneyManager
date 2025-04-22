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
    public User save(User user){
        
        if (user.getWallets() != null) {
            for (Wallet wallet : user.getWallets()) {
                wallet.setUser(user);

                if (wallet.getCards() != null) {
                    for (Card card : wallet.getCards()) {
                        card.setWallet(wallet);
                    }
                }
            }
        }

        if (user.getInvestments() != null) {
            for (Investment investment : user.getInvestments()) {
                investment.setUser(user);
            }
        }
        return repository.save(user);
    }

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
    public Optional<User> selfWalletTransfer(InternTransferRequestDTO transferDTO){

        Optional<User> user = repository.findById(transferDTO.id());

        if (user.isPresent()) {
            System.out.println(user);
        }

        if (user.isPresent() && !transferDTO.walletSenderId().equals(transferDTO.walletReceiverId())) {
            if (user.get().getWallets() != null) {
                Wallet senderWallet = null;
                Wallet receiverWallet = null;

                for (Wallet wallet : user.get().getWallets()) {
                    if (wallet.getId().equals(transferDTO.walletSenderId())) {
                        senderWallet = wallet;
                    } else if (wallet.getId().equals(transferDTO.walletReceiverId())) {
                        receiverWallet = wallet;
                    }
                }

                if (senderWallet != null && receiverWallet != null) {
                    Card cardSender = null;
                    Card cardReceiver = null;

                    for (Card card : senderWallet.getCards()) {
                        if (card.getId().equals(transferDTO.cardSenderId())) {
                            cardSender = card;

                        } else if (card.getId().equals(transferDTO.cardReceiverId())) {
                            cardReceiver = card;
                        }
                    }

                    if (cardSender != null && cardReceiver != null) {
                        if (cardSender.getAmount() >= transferDTO.amount()){
                            cardSender.setAmount((float) (cardSender.getAmount() - transferDTO.amount()));
                            cardReceiver.setAmount((float) (cardReceiver.getAmount() + transferDTO.amount()));

                            return user;
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }
}
