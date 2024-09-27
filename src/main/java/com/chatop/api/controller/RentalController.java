package com.chatop.api.controller;

import java.util.List;

import com.chatop.api.dto.FormCreateRentalDTO;
import com.chatop.api.dto.RentalDTO;
import com.chatop.api.mapper.RentalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chatop.api.service.RentalService;

@RestController
@RequestMapping("/rentals")
public class RentalController {

	@Autowired
	private RentalService rentalService;

	@Autowired
	private RentalMapper rentalMapper;

	@GetMapping()
	public ResponseEntity<List<RentalDTO>> getRentals() {
		List<RentalDTO> rentals = rentalService.getRentals();
		return ResponseEntity.ok(rentals);
    }

	@GetMapping("/{id}")
	public ResponseEntity<RentalDTO> getRentalById(@PathVariable Long id) {
		RentalDTO rentalDto = rentalService.getRentalById(id);
		if (rentalDto != null) {
			return ResponseEntity.ok(rentalDto);
		} else {
			return null;
		}
	}

	@PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public RentalDTO createRental (
			@ModelAttribute FormCreateRentalDTO receivedRental
	) throws Exception {
		RentalDTO createdRental = rentalService.createRental(receivedRental);
		return createdRental;
	}

	@PutMapping("/{id}")
	public String updateRental (
			@PathVariable Long id,
			@RequestParam("name") String name,
			@RequestParam("surface") double surface,
			@RequestParam("price") double price,
			@RequestParam("description") String description
	) {
		RentalDTO receivedRental = new RentalDTO(
				name,
				surface,
				price,
				description);
		RentalDTO updatedRental = rentalService.updateRental(id, receivedRental);
		if (updatedRental != null) {
			return "Rental updated !";
		} else {
			return null;
		}
	}

}
