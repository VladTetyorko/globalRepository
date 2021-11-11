package com.census.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.census.entities.SomeEntity;
import com.census.services.SomeEntityService;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;

@Component
public class VehicleMutation implements GraphQLMutationResolver {
	@Autowired
	private SomeEntityService someEntityService;

	public SomeEntity createVehicle(final String type, final String string_field2, final String string_field3,
			final String launchDate) {
		return this.someEntityService.createVehicle(type, string_field2, string_field3, launchDate);
	}
}