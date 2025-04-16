package com.example.demo.worker;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Investment;
import com.example.demo.model.User;
import com.example.demo.model.Wallet;
import com.example.demo.repository.UserRepository;

@Service
public class UserWorker implements UserWorkerInterface {

    @Autowired
    private UserRepository repository;
    
    @Override
    public User save(User user){
        
        if (user.getWallets() != null) {
        for (Wallet wallet : user.getWallets()) {
            wallet.setUser(user);
        }
    }

    if (user.getInvestments() != null) {
        for (Investment investment : user.getInvestments()) {
            investment.setUser(user);
        }
    }
        return repository.save(user);
    }

    @Override
    public List<User> getAll(){
        return repository.findAll();
    }
}
