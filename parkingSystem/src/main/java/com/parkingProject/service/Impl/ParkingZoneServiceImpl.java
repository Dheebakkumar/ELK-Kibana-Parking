package com.parkingProject.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.parkingProject.DTO.ParkingZoneDTO;
import com.parkingProject.model.ParkingZone;
import com.parkingProject.repository.ParkingZoneRepo;
import com.parkingProject.service.ParkingZoneService;


@Service("userService")
public class ParkingZoneServiceImpl implements ParkingZoneService {
	
	Logger logger = LoggerFactory.getLogger(ParkingZoneServiceImpl.class);
	
	@Autowired
	ParkingZoneRepo parkingZoneRepo;	

	@Override
	public ParkingZoneDTO getUserDetails(long id, long userId) {
	    
		String url = "/zones/" + id;
	    ParkingZone parkingZone = parkingZoneRepo.findById(id);    
	    if (parkingZone != null) {
	        logger.info("Zone found: ID: {}, User ID: {}, Name: {}, URL: {}", id, userId, parkingZone.getNameZone(), url);

	        ParkingZoneDTO parkingZoneDto = new ParkingZoneDTO();
	        parkingZoneDto.setId(parkingZone.getId());
	        parkingZoneDto.setUserId(userId);
	        parkingZoneDto.setNameZone(parkingZone.getNameZone());
	        parkingZoneDto.setStatus(parkingZone.getStatus());
	        parkingZoneDto.setCreateOn(parkingZone.getCreatedOn());
	        parkingZoneDto.setUpdateOn(parkingZone.getUpdatedOn());

	        return parkingZoneDto;
	    } else {
	        logger.error("Zone not found - Zone ID: {}, User ID: {}, URL: {}", id, userId, url);
	        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Zone not found with ID: " + id + " for User ID: " + userId);
	    }
	}

	@Override
	public ParkingZoneDTO addZones( ParkingZoneDTO parkingZoneDto) {
		
		ParkingZone parkingZone = new ParkingZone();
		//parkingZone.setId(userDTO.getId());
		parkingZone.setNameZone(parkingZoneDto.getNameZone());
		parkingZone.setStatus(parkingZoneDto.getStatus());
		//parkingZone.setCreatedOn(userDTO.getCreateOn());
		//parkingZone.setUpdatedOn(userDTO.getUpdateOn());
		
		ParkingZone addZone = parkingZoneRepo.save(parkingZone);
		ParkingZoneDTO dto = new ParkingZoneDTO();
		dto.setId(addZone.getId());
		dto.setNameZone(addZone.getNameZone());
		dto.setStatus(addZone.getStatus());
		dto.setCreateOn(addZone.getCreatedOn());
		dto.setUpdateOn(addZone.getUpdatedOn());
		
		return dto;
	}

	@Override
	public List<ParkingZoneDTO> getZones( ) {

		List<ParkingZone> parkingZoneList = parkingZoneRepo.findAll();
		List<ParkingZoneDTO> parkingZoneDto = new ArrayList();
		
		for (ParkingZone parkingZone :parkingZoneList ) {
			
			ParkingZoneDTO dto = new ParkingZoneDTO();
			dto.setId(parkingZone.getId());
			dto.setNameZone(parkingZone.getNameZone());
			dto.setStatus(parkingZone.getStatus());
			dto.setCreateOn(parkingZone.getCreatedOn());
			dto.setUpdateOn(parkingZone.getUpdatedOn());
			
			parkingZoneDto.add(dto);
		}
		
		return parkingZoneDto;
	}


	@Override
	public ParkingZoneDTO updateZone(ParkingZoneDTO parkingZoneDTO) {

		ParkingZone parkingZone = parkingZoneRepo.findById(parkingZoneDTO.getId());
		
		//ParkingZone parkingZone = new ParkingZone();
		//parkingZone.setId(userDTO.getId());
		if(parkingZone !=null) {
		parkingZone.setNameZone(parkingZoneDTO.getNameZone());
		parkingZone.setStatus(parkingZoneDTO.getStatus());
//		parkingZone.setCreatedOn(userDTO.getCreateOn());
//		parkingZone.setUpdatedOn(userDTO.getUpdateOn());
		ParkingZone saveZone = parkingZoneRepo.save(parkingZone);
		
		//return "Updated Successfully...	";
		return update(saveZone);
		}
		else {
			throw new RuntimeException("Id Not Present");
		}
	}
	
	public ParkingZoneDTO update(ParkingZone saveZone) {
		
		ParkingZoneDTO parkingZoneDTO = new ParkingZoneDTO();
		parkingZoneDTO.setId(saveZone.getId());
		parkingZoneDTO.setNameZone(saveZone.getNameZone());
		parkingZoneDTO.setStatus(saveZone.getStatus());
		parkingZoneDTO.setCreateOn(saveZone.getCreatedOn());
		parkingZoneDTO.setUpdateOn(saveZone.getUpdatedOn());
		
		return parkingZoneDTO;
		
	}
	
	
	
}
