package com.my1project.my1projecg.service;

import com.my1project.my1projecg.Repository.RoleRepository;
import com.my1project.my1projecg.Repository.TransactionRepository;
import com.my1project.my1projecg.Repository.UserRepository;
import com.my1project.my1projecg.entity.Role;
import com.my1project.my1projecg.entity.Transaction;
import com.my1project.my1projecg.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TransactionRepository transactionRepository;

    //Metoda per te rregjistruar nje perdorus te ri me nje rol
    @Transactional
    public User registerUser(String userName, String password, String roleName) {
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() ->
                        new RuntimeException("Gabim: Roli '" + roleName + "' nuk u gjet!"));

        User user = new User();
        user.setUsername(userName);
        user.setPassword(passwordEncoder.encode(password));
        user.getRoles().add(role);

        return userRepository.save(user);
    }

    @Transactional
    public void depositByUsername(String username, Double amount) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Gabim: User nuk u gjet!"));
        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);
        Transaction transaction = new Transaction(
                user.getId(),
                user.getId(),
                amount,
                "DEPOSIT",
                LocalDateTime.now()
        );
        transactionRepository.save(transaction);

    }

    @Transactional
    public void withdraw(String username, Double amount) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Gabim: User nuk u gjet!"));
        if(user.getBalance() < amount) {
            throw new RuntimeException("Gabim: Nuk keni para te mjaftueshme");
        }
        user.setBalance(user.getBalance() - amount);
        userRepository.save(user);
        Transaction transaction = new Transaction(
                user.getId(),
                user.getId(),
                amount,
                "WITHDRAW",
                LocalDateTime.now()
        );
        transactionRepository.save(transaction);
    }

    @Transactional
    public void transferMoney(String senderUsername, String receiverUsername, Double amount) {
        User sender = userRepository.findByUsername(senderUsername)
                .orElseThrow(() -> new RuntimeException("Gabim: Derguesi nuk u gjet!"));
        User reciver  = userRepository.findByUsername(receiverUsername)
                .orElseThrow(() -> new RuntimeException("Gabim: Marresi nuk u gjet!"));

        if(sender.getBalance() < amount) {
            throw new RuntimeException("Gabim: Nuk keni para te mjaftueshme per transferten");
        }
        sender.setBalance(sender.getBalance() - amount);
        reciver.setBalance(reciver.getBalance() + amount);
        userRepository.save(sender);
        userRepository.save(reciver);

        Transaction transaction = new Transaction(
                sender.getId(),
                reciver.getId(),
                amount,
                "TRANSFER",
                LocalDateTime.now()

        );
        transactionRepository.save(transaction);

    }

    // ... brenda UserService:

    public List<Transaction> getUserHistory(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Përdoruesi nuk u gjet!"));

        return transactionRepository.findBySenderIdOrReceiverId(user.getId(), user.getId());
    }

}
