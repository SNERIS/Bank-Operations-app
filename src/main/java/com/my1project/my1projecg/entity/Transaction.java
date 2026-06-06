package com.my1project.my1projecg.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Transactions")
@Getter
@Setter

public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sender_id", nullable = false)
    private Long senderId;

    @Column(name = "receiver_id", nullable = false)
    private Long receiverId;

    @Column(name = "amount", scale = 2)
    private Double amount;

    @Column(name = "transaction_type")
    private String type;

    @Column(name = "created_at")
    private LocalDateTime timestamp;

    public Transaction() {}
    public Transaction(Long senderId, Long receiverId, Double amount, String type, LocalDateTime timestamp) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
        this.type = type;
        this.timestamp = timestamp;
    }


}
