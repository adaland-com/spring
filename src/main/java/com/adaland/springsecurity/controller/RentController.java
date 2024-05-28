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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/rents", produces = "application/json")
public class RentController {

    private final RentService rentService;

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<RentDto> getAll() {
        return rentService.findAll();
    }

    @GetMapping("/{rentId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public RentDto findById(@PathVariable long rentId) {
        return rentService.findById(rentId);
    }

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<RentDto> findByUserId(@RequestParam long userId) {
        return rentService.findByUserId(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RentDto createRent(@RequestBody RentCreationDto rentCreationDto) {
        return rentService.createRent(rentCreationDto);
    }

    @PutMapping("/{rentId}/return")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RentDto returnGames(@PathVariable long rentId) {
        return rentService.returnGamesOfRent(rentId);
    }

    @PutMapping("/{rentId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RentDto updateRent(@PathVariable long rentId, @RequestBody RentUpdateDto rentUpdateDto) {
        return rentService.updateRent(rentId, rentUpdateDto);
    }


}
