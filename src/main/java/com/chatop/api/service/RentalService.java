package com.chatop.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.chatop.api.dto.FormCreateRentalDTO;
import com.chatop.api.dto.RentalDTO;
import com.chatop.api.exception.RentalNotFound;
import com.chatop.api.exception.UserNotFound;
import com.chatop.api.mapper.RentalMapper;
import com.chatop.api.model.User;
import com.chatop.api.repository.UserRepository;
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
	private Path uploadFolder;

	@Value("${upload.rentals.url}")
	private String urlToUploadFolder;
	
	@Autowired
	private RentalRepository rentalRepository;

	@Autowired
	private RentalMapper rentalMapper;

	@Autowired
	private UserRepository userRepository;

	public RentalDTO[] getRentals() {
		List<Rental> rentals = rentalRepository.findAll();
		return rentals.stream().map(rentalMapper::toDTO).toArray(RentalDTO[]::new);
	}
	
	public RentalDTO getRentalById(Long id) {
		return rentalMapper.toDTO(rentalRepository.findById(id).orElseThrow(
				() -> new RentalNotFound("Rental non trouvé !")));
	}

	public void createRental(Long ownerId, FormCreateRentalDTO formCreateRentalDTO) {
		User userFind = userRepository.findById(ownerId).orElseThrow(
				() -> new UserNotFound("Utilisateur non trouvé !"));
		Rental rental = new Rental();
		rental.setName(formCreateRentalDTO.getName());
		rental.setSurface(formCreateRentalDTO.getSurface());
		rental.setPrice(formCreateRentalDTO.getPrice());
		rental.setDescription(formCreateRentalDTO.getDescription());
		rental.setOwnerId(userFind);
		Rental savedRental = rentalRepository.save(rental);
		rental.setPicture(uploadFile(savedRental.getId(), formCreateRentalDTO.getPicture()));
		Rental savedRentalWithPicture = rentalRepository.save(rental);
	}

	public RentalDTO updateRental(Long rentalId, RentalDTO rentalDTO) {
		Rental existingRental = rentalRepository.findById(rentalId).orElseThrow(
				() -> new RentalNotFound("Rental non trouvé !"));
        existingRental.setName(rentalDTO.getName());
		existingRental.setSurface(rentalDTO.getSurface());
		existingRental.setPrice(rentalDTO.getPrice());
		existingRental.setDescription(rentalDTO.getDescription());
		Rental updatedRental = rentalRepository.save(existingRental);
		return rentalMapper.toDTO(updatedRental);
	}

	public String uploadFile(Long rentalId, MultipartFile picture) {
		String originalFilename = picture.getOriginalFilename();
		if (originalFilename != null && originalFilename.contains(".")) {
			String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
			String fileName = rentalId + "." + fileExtension;
			Path filePath = Paths.get(uploadFolder + "/" + fileName);
			try {
				Files.createDirectories(filePath.getParent()); // Ensure the directory exists
				Files.write(filePath, picture.getBytes());
				String fileUrl = urlToUploadFolder + fileName;
				return fileUrl;
			} catch (IOException e) {
				throw new RuntimeException("l'erreur est : " + e);
			}
		} else {
			throw new RentalNotFound("Echec de l'upload du fichier !");
		}
    }
}
