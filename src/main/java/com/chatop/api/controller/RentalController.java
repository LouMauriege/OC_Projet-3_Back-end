package com.chatop.api.controller;

import java.io.IOException;
import java.util.List;

import com.chatop.api.dto.ArrayRentalsDTO;
import com.chatop.api.dto.FormCreateRentalDTO;
import com.chatop.api.dto.RentalDTO;
import com.chatop.api.mapper.RentalMapper;
import com.chatop.api.model.FormUpdateRental;
import com.chatop.api.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
	public ResponseEntity<ArrayRentalsDTO> getRentals() {
		ArrayRentalsDTO arrayRentalsDTO = new ArrayRentalsDTO(rentalService.getRentals());
		RentalDTO[] rentals = rentalService.getRentals();
		return ResponseEntity.ok(arrayRentalsDTO);
    }

	@GetMapping("/{id}")
	public ResponseEntity<RentalDTO> getRentalById(@PathVariable Long id) {
		return ResponseEntity.ok(rentalService.getRentalById(id));
	}

	@PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<String> createRental (
			@AuthenticationPrincipal UserPrincipal principal,
			@ModelAttribute FormCreateRentalDTO receivedRental) throws IOException {
		rentalService.createRental(principal.getUserId(), receivedRental);
		return ResponseEntity.ok("\"message\": \"Rental created !\"");
	}

	@PutMapping("/{rentalId}")
	public ResponseEntity<String> updateRental (
			@PathVariable Long rentalId,
			@ModelAttribute FormUpdateRental receivedRental) {
		RentalDTO receivedRentalDTO = new RentalDTO(
				receivedRental.getName(),
				receivedRental.getSurface(),
				receivedRental.getPrice(),
				receivedRental.getDescription());
		RentalDTO updatedRental = rentalService.updateRental(rentalId, receivedRentalDTO);
		return ResponseEntity.ok("\"message\": \"Rental updated !\"");
	}

}
