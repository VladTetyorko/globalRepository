package com.census.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.census.entities.SomeEntity;
import com.census.services.SomeEntityService;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;

@Component
public class SomeEntityQuery implements GraphQLQueryResolver {
    @Autowired
    private SomeEntityService service;
    
    public List<SomeEntity> getVehicles(final int count) {
        return this.service.getAllVehicles(count);
    }
    
    public Optional<SomeEntity> getVehicle(final int id) {
        return this.service.getVehicle(id);
    }
}