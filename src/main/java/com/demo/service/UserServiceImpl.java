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
            userUpdate.getWallets().clear();
            userUpdate.getWallets().addAll(user.wallets());
            linkWallet(userUpdate);
        }

        if (user.investments() != null) {
            userUpdate.getInvestments().clear();
            userUpdate.getInvestments().addAll(user.investments());
            linkInvestment(userUpdate);
        }

        if (user.password() != null && !user.password().isBlank()) {
            String encodedPassword = securityConfig.passwordEncoder().encode(user.password());
            userUpdate.setPassword(encodedPassword);
        }

        userRepository.save(userUpdate);
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
