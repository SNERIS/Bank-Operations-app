package com.my1project.my1projecg.Controllers;


import com.my1project.my1projecg.Repository.TransactionRepository;
import com.my1project.my1projecg.dto.TransactionDTO;
import com.my1project.my1projecg.dto.TransferDTO;
import com.my1project.my1projecg.dto.UserRegistrationDTO;
import com.my1project.my1projecg.entity.Transaction;
import com.my1project.my1projecg.entity.User;
import com.my1project.my1projecg.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository transactionRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserRegistrationDTO registrationDTO) {
            User savedUser = userService.registerUser(
                    registrationDTO.getUsername(),
                    registrationDTO.getPassword(),
                    registrationDTO.getRoleName()
            );

            return ResponseEntity.ok("Përdoruesi u krijua me sukses me ID: " + savedUser.getId());
    }


    @PutMapping("/deposit")
    public ResponseEntity<String> deposit(Principal principal, @RequestBody TransactionDTO transactionDTO){
            String username = principal.getName();

            userService.depositByUsername(username, transactionDTO.getAmount());
            return ResponseEntity.ok("Shuma"+ transactionDTO.getAmount() + "u depozitua me sukses");

    }
    @PutMapping("/withdraw")
    public ResponseEntity<String> withdraw(Principal principal, @RequestBody TransactionDTO transactionDTO){
            String username = principal.getName();

            userService.withdraw(username, transactionDTO.getAmount());
            return ResponseEntity.ok("Shuma"+ transactionDTO.getAmount() + "u terhoq me sukses");
    }

    @PutMapping("/transfer")
    public ResponseEntity<String> transfer(Principal principal, @RequestBody TransferDTO transferDTO) {
            String senderUsername = principal.getName();

            userService.transferMoney(senderUsername, transferDTO.getReceiverUsername(), transferDTO.getAmount());
            return ResponseEntity.ok("Transferta u krye me sukses!");
    }

    @GetMapping("/history")
    public ResponseEntity<List<Transaction>> getHistory(Principal principal) {
            String username = principal.getName();
            List<Transaction> history = userService.getUserHistory(username);
            return ResponseEntity.ok(history);
    }
}

