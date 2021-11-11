package com.census.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.census.entities.SomeEntity;
import com.census.repositories.SomeEntityRepository;

@Service
public class SomeEntityService {
	private final SomeEntityRepository someEntityRepository;

	public SomeEntityService(final SomeEntityRepository vehicleRepository) {
		this.someEntityRepository = vehicleRepository;
	}

	@Transactional
	public SomeEntity createVehicle(final String type, final String modelCode, final String brandName,
			final String launchDate) {
		final SomeEntity vehicle = new SomeEntity();
		vehicle.setType(type);
		vehicle.setString_field2(modelCode);
		vehicle.setString_field3(brandName);
		vehicle.setLaunchDate(LocalDate.parse(launchDate));
		return this.someEntityRepository.save(vehicle);
	}

	@Transactional(readOnly = true)
	public List<SomeEntity> getAllVehicles(final int count) {
		return this.someEntityRepository.findAll().stream().limit(count).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public Optional<SomeEntity> getVehicle(final int id) {
		return this.someEntityRepository.findById(id);
	}
}