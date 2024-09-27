package com.chatop.api.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "rentals")
public class Rental {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private double surface;

	private double price;

	private String picture;

	private String description;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private User ownerId;

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

	public Long getOwnerEntityId() {
		return ownerId.getId();
	}
}
