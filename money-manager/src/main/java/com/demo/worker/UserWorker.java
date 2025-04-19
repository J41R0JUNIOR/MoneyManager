package com.demo.worker;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.Investment;
import com.demo.model.User;
import com.demo.model.Wallet;
import com.demo.repository.UserRepository;

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
}
