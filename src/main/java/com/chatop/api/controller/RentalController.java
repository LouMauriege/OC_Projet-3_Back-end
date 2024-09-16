package com.chatop.api.controller;

import java.util.List;
import java.util.Optional;

import com.chatop.api.dto.RentalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chatop.api.model.Rental;
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
	public String createRental (@RequestBody RentalDTO rentalDto) {
		RentalDTO createdRental = rentalService.createRental(rentalDto);
		return "Rental created !";
	}

	@PutMapping("/rentals/{id}")
	public String updateRental (@PathVariable Long id, @RequestBody RentalDTO rentalDto) {
		RentalDTO updatedRental = rentalService.updateRental(id, rentalDto);
		if (updatedRental != null) {
			return "Rental updated !";
		} else {
			return null;
		}
	}

}
