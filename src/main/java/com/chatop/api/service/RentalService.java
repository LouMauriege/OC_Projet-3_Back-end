package com.chatop.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.chatop.api.dto.FormCreateRentalDTO;
import com.chatop.api.dto.RentalDTO;
import com.chatop.api.mapper.RentalMapper;
import com.chatop.api.mapper.UserMapper;
import com.chatop.api.model.User;
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

	@Autowired
	private UserService userService;

	@Autowired
	private UserMapper userMapper;
	
	public List<RentalDTO> getRentals() {
		List<Rental> rentals = rentalRepository.findAll();
		return rentals.stream().map(rentalMapper::toDTO).collect(Collectors.toList());
	}
	
	public RentalDTO getRentalById(Long id) {
		 Optional<Rental> rental = rentalRepository.findById(id);
		 return rental.map(rentalMapper::toDTO).orElse(null);
	}

	public RentalDTO createRental(FormCreateRentalDTO formCreateRentalDTO) throws Exception {
		formCreateRentalDTO.setOwnerId(1L);
		User owner = userMapper.toEntity(userService.getUserById(formCreateRentalDTO.getOwnerId()));
		System.out.println(owner);
		Rental rental = new Rental();
		rental.setName(formCreateRentalDTO.getName());
		rental.setSurface(formCreateRentalDTO.getSurface());
		rental.setPrice(formCreateRentalDTO.getPrice());
		rental.setPicture(uploadFile(rental.getId(), formCreateRentalDTO.getPicture()));
		rental.setDescription(formCreateRentalDTO.getDescription());
		rental.setOwnerId(owner);
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

	public String uploadFile(Long rentalId, MultipartFile picture) throws IOException {
		String fileName = picture.getOriginalFilename();
		if (fileName != null && fileName.contains(".")) {
			String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
			Path filePath = Paths.get(uploadFolder + "/" + rentalId + "." + fileExtension);
			Files.write(filePath, picture.getBytes());
			return filePath.toString().replace("\\", "/");
		}
		return "";
	}
}
