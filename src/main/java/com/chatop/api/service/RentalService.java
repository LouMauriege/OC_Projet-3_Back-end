package com.chatop.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.chatop.api.dto.RentalDTO;
import com.chatop.api.mapper.RentalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.chatop.api.model.Rental;
import com.chatop.api.repository.RentalRepository;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Service
public class RentalService {
	@Value("${upload.rentals.path}")
	private String uploadFolder;
	
	@Autowired
	private RentalRepository rentalRepository;

	@Autowired
	private RentalMapper rentalMapper;
	
	public List<RentalDTO> getRentals() {
		List<Rental> rentals = rentalRepository.findAll();
		return rentals.stream().map(rentalMapper::toDTO).collect(Collectors.toList());
	}
	
	public RentalDTO getRentalById(Long id) {
		 Optional<Rental> rental = rentalRepository.findById(id);
		 return rental.map(rentalMapper::toDTO).orElse(null);
	}

	public RentalDTO createRental(RentalDTO rentalDTO) {
		Rental rental = rentalMapper.toEntity(rentalDTO);
		Rental savedRental = rentalRepository.save(rental);
		return rentalMapper.toDTO(savedRental);
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
			Rental updatedRental = rentalRepository.save(rental);
			return rentalMapper.toDTO(rental);
		}
		return null;
	}

	public String uploadFile(RentalDTO rentalDTO, MultipartFile picture) throws IOException {
		String rentalId = rentalDTO.getId().toString();
		String fileName = picture.getOriginalFilename();
		String fileExtension;
		if (fileName != null && fileName.contains(".")) {
			fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
			Path filePath = Paths.get(uploadFolder + "/" + rentalId + "." + fileExtension);
			Files.write(filePath, picture.getBytes());
			return filePath.toString().replace("\\", "/");
		}
		return "";
	}
}
