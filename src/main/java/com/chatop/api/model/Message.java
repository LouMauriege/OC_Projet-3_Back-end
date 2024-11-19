package com.chatop.api.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "messages")
public class Message {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

    @ManyToOne
	@JoinColumn(name = "rental_id")
	public Rental rentalId;

    @ManyToOne
	@JoinColumn(name = "user_id")
	public User userId;
	
	public String message;
	
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
	
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getUserId() {
        return userId.getId();
    }

    public Long getRentalId() {
        return rentalId.getId();
    }
	
}
