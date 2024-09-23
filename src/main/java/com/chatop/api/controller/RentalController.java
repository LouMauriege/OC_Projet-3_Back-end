package com.chatop.api.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.chatop.api.dto.RentalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.chatop.api.service.RentalService;

@RestController
@RequestMapping("/rentals")
public class RentalController {

//	// Ignore picture field while mapping formdata to RentalDTO
//	@InitBinder
//	public void initBinder(WebDataBinder binder) {
//		binder.setDisallowedFields("picture");
//		System.out.println("Disallowed fields: " + Arrays.toString(binder.getDisallowedFields()));
//	}

	@Autowired
	private RentalService rentalService;

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
			@RequestParam("name") String name,
			@RequestParam("surface") double surface,
			@RequestParam("price") double price,
			@RequestPart("picture") MultipartFile picture,
			@RequestParam("description") String description
	) throws IOException {
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
		// receivedRental.setPicture(fileUrl);
		RentalDTO createdRental = rentalService.createRental(receivedRental);
		String fileUrl = rentalService.uploadFile(createdRental, picture);
		createdRental.setPicture(fileUrl);
		RentalDTO updatedRental = rentalService.updateRental(createdRental.getId(), createdRental);
		System.out.println(updatedRental);
		return updatedRental;
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
