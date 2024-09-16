package com.chatop.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.chatop.api.dto.RentalDTO;
import com.chatop.api.mapper.RentalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatop.api.model.Rental;
import com.chatop.api.repository.RentalRepository;

import lombok.Data;

@Data
@Service
public class RentalService {
	
	@Autowired
	private RentalRepository rentalRepository;
	
	public List<RentalDTO> getRentals() {
		List<Rental> rentals =  rentalRepository.findAll();
		return rentals.stream().map(RentalMapper::toDTO).collect(Collectors.toList());
	}
	
	public RentalDTO getRentalById(Long id) {
		 Optional<Rental> rental = rentalRepository.findById(id);
		 return rental.map(RentalMapper::toDTO).orElse(null);
	}

	public RentalDTO createRental(RentalDTO rentalDTO) {
		Rental rental = RentalMapper.toEntity(rentalDTO);
		Rental savedRental = rentalRepository.save(rental);
		return RentalMapper.toDTO(savedRental);
	}

	public RentalDTO updateRental(Long id, RentalDTO rentalDTO) {
		Optional<Rental> existingRental = rentalRepository.findById(id);
		if (existingRental.isPresent()) {
			Rental rental = existingRental.get();
			rental.setName(rentalDTO.getName());
			rental.setSurface(rentalDTO.getSurface());
			rental.setPrice(rentalDTO.getPrice());
			rental.setPicture(rentalDTO.getPicture());
			rental.setDescription(rentalDTO.getDescription());
			rental.setOwnerId(rentalDTO.getOwnerId());
			Rental updatedRental = rentalRepository.save(rental);
			return RentalMapper.toDTO(rental);
		}
		return null;
	}

	public String deleteUser(Long id) {
		rentalRepository.deleteById(id);
		return "User deleted !";
	}

}
