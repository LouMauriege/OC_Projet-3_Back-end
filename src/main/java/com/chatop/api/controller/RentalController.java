package com.chatop.api.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.chatop.api.dto.RentalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chatop.api.service.RentalService;

@RestController
public class RentalController {

	@Autowired
	private RentalService rentalService;

	@GetMapping("/rentals")
	public ResponseEntity<List<RentalDTO>> getRentals() {
		List<RentalDTO> rentals = rentalService.getRentals();
		return ResponseEntity.ok(rentals);
    }

	@GetMapping("/rentals/{id}")
	public ResponseEntity<RentalDTO> getRentalById(@PathVariable Long id) {
		RentalDTO rentalDto = rentalService.getRentalById(id);
		if (rentalDto != null) {
			return ResponseEntity.ok(rentalDto);
		} else {
			return null;
		}
	}

	@PostMapping("/rentals")
	public String createRental (
			@RequestParam("name") String name,
			@RequestParam("surface") double surface,
			@RequestParam("price") double price,
			@RequestParam("picture") String picture,
			@RequestParam("description") String description,
			@RequestParam("ownerId") Long ownerId) {
		RentalDTO receivedRental = new RentalDTO(
			0L,
			name,
			surface,
			price,
			picture,
			description,
			ownerId,
			LocalDateTime.now(),
			LocalDateTime.now());
		RentalDTO createdRental = rentalService.createRental(receivedRental);
		return "Rental created !";
	}

	@PutMapping("/rentals/{id}")
	public String updateRental (
			@PathVariable Long id,
			@RequestParam("name") String name,
			@RequestParam("surface") double surface,
			@RequestParam("price") double price,
			@RequestParam("description") String description) {
		RentalDTO receivedRental = new RentalDTO(
				0L,
				name,
				surface,
				price,
				"",
				description,
				0L,
				LocalDateTime.now(),
				LocalDateTime.now());
		RentalDTO updatedRental = rentalService.updateRental(id, receivedRental);
		if (updatedRental != null) {
			return "Rental updated !";
		} else {
			return null;
		}
	}

}
