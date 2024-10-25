package com.chatop.api.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.chatop.api.dto.FormCreateRentalDTO;
import com.chatop.api.dto.RentalDTO;
import com.chatop.api.exception.RentalNotFound;
import com.chatop.api.exception.UserNotFound;
import com.chatop.api.mapper.RentalMapper;
import com.chatop.api.model.User;
import com.chatop.api.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.chatop.api.model.Rental;
import com.chatop.api.repository.RentalRepository;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Log4j2
@Service
public class RentalService {
	@Value("${upload.rentals.path}")
	private String uploadFolder;

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
		System.out.println(savedRental.getId());
		System.out.println(formCreateRentalDTO.getPicture());
		try {
			rental.setPicture(uploadFile(savedRental.getId(), formCreateRentalDTO.getPicture()));
		} catch (IOException e) {
			throw new RuntimeException("Echec de l'upload du fichier !");
		}
		Rental savedRentalWithPicture = rentalRepository.save(rental);
		System.out.println(savedRentalWithPicture);
	}

	public RentalDTO updateRental(Long rentalId, RentalDTO rentalDTO) {
		Rental existingRental = rentalRepository.findById(rentalId).orElseThrow(
				() -> new RentalNotFound("Rental non trouvé !"));
        existingRental.setName(rentalDTO.getName());
		existingRental.setSurface(rentalDTO.getSurface());
		existingRental.setPrice(rentalDTO.getPrice());
		existingRental.setPicture(rentalDTO.getPicture());
		existingRental.setDescription(rentalDTO.getDescription());
		Rental updatedRental = rentalRepository.save(existingRental);
		return rentalMapper.toDTO(updatedRental);
	}

	public String uploadFile(Long rentalId, MultipartFile picture) throws IOException {
		String originalFilename = picture.getOriginalFilename();
		if (originalFilename != null && originalFilename.contains(".")) {
			String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
			String fileName = rentalId + "." + fileExtension;
			String filePath = uploadFolder + fileName;
            File createdFile = new File(filePath);
			try (FileOutputStream fos = new FileOutputStream(createdFile)) {
				fos.write(picture.getBytes());
			}
            String url = urlToUploadFolder + fileName;
			System.out.println(url);
            return url;
        } else {
			throw new RentalNotFound("Echec de l'upload du fichier !");
		}
    }
}
