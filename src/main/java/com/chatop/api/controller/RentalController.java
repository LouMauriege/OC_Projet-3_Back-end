package com.chatop.api.controller;

import java.io.IOException;
import java.util.List;

import com.chatop.api.dto.RentalDTO;
import com.chatop.api.mapper.RentalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
			@RequestParam("name") String name,
			@RequestParam("surface") double surface,
			@RequestParam("price") double price,
			@RequestPart("picture") MultipartFile picture,
			@RequestParam("description") String description
	) throws IOException {
		RentalDTO receivedRental = new RentalDTO(
				name,
				surface,
				price,
				description,
				1L);
		RentalDTO createdRental = rentalService.createRental(receivedRental);
		String fileUrl = rentalService.uploadFile(createdRental, picture);
		createdRental.setPicture(fileUrl);
		RentalDTO updatedRental = rentalService.updateRental(createdRental.getId(), createdRental);
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
