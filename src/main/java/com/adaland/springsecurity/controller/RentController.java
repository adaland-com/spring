package com.adaland.springsecurity.controller;

import com.adaland.springsecurity.model.dto.rent.RentCreationDto;
import com.adaland.springsecurity.model.dto.rent.RentDto;
import com.adaland.springsecurity.model.dto.rent.RentUpdateDto;
import com.adaland.springsecurity.service.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/rent", produces = "application/json")
public class RentController {

    private final RentService rentService;

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<RentDto> getAll() {
        return rentService.findAll();
    }

    @GetMapping("/{gameId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public RentDto findById(@PathVariable String rentId) {
        return rentService.findById(rentId);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RentDto createRent(@RequestBody RentCreationDto rentCreationDto) {
        return rentService.createRent(rentCreationDto);
    }

    @PutMapping("/{rentId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RentDto updateRent(@PathVariable String rentId, @RequestBody RentUpdateDto rentUpdateDto) {
        return rentService.updateRent(rentId, rentUpdateDto);
    }


}