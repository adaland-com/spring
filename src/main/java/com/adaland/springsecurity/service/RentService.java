package com.adaland.springsecurity.service;


import com.adaland.springsecurity.exception.EntityNotFoundException;
import com.adaland.springsecurity.mapper.GameMapper;
import com.adaland.springsecurity.mapper.RentMapper;
import com.adaland.springsecurity.model.auth.User;
import com.adaland.springsecurity.model.dao.Game;
import com.adaland.springsecurity.model.dao.GameStatus;
import com.adaland.springsecurity.model.dao.Rent;
import com.adaland.springsecurity.model.dto.rent.RentCreationDto;
import com.adaland.springsecurity.model.dto.rent.RentDto;
import com.adaland.springsecurity.model.dto.rent.RentUpdateDto;
import com.adaland.springsecurity.repository.GameRepository;
import com.adaland.springsecurity.repository.RentRepository;
import com.adaland.springsecurity.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RentService {

    private static final String CREATED_RENT = "Rent created ";
    private static final String UPDATED_RENT = "Rent updated ";

    @Autowired
    private RentRepository rentRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameService gameService;

    private final RentMapper rentMapper;
    private final GameMapper gameMapper;

    public List<RentDto> findAll() {
        return rentRepository.findAll().stream()
                .map(rentMapper::fromRentToRentDto)
                .collect(Collectors.toList());
    }

    public RentDto findById(String rentId) {
        Rent rent = rentRepository.findById(rentId).orElseThrow(() ->
                new EntityNotFoundException(EntityNotFoundException.ENTITY_NOT_FOUND_MESSAGE, "rent with uuid: " + rentId));
        return rentMapper.fromRentToRentDto(rent);
    }

    public List<RentDto> findByUserId(long userId) {
        List<Rent> list = rentRepository.findAllByUserId(userId);
        return list.stream().map(rentMapper::fromRentToRentDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public RentDto createRent(RentCreationDto rentCreationDto) {

        Rent rentToSave = new Rent();

        List<Game> chosenGamesToRent = new ArrayList<>();
        List<Long> gamesIdToRent = rentCreationDto.getGames();

        boolean isGameRented = false;

        User user = userRepository.findByUsername(rentCreationDto.getUsername()).orElseThrow(() ->
                new EntityNotFoundException(EntityNotFoundException.ENTITY_NOT_FOUND_MESSAGE, "user with username: " + rentCreationDto.getUsername()));

        rentToSave.setUser(user);

        rentToSave.setCost(BigDecimal.ONE);

        for (Long gameId : gamesIdToRent) {
            Game gameToRent = gameRepository.findById(gameId).orElseThrow(() ->
                    new EntityNotFoundException(EntityNotFoundException.ENTITY_NOT_FOUND_MESSAGE, "game with id: " + gameId));
            if (gameToRent.getStatus().equals(GameStatus.AVAILABLE)) {
                gameToRent.setStatus(GameStatus.RENTED);
                chosenGamesToRent.add(gameToRent);
                gameToRent.setRent(rentToSave);
                gameRepository.save(gameToRent);
                isGameRented = true;
            }

        }

        if (isGameRented) {

            rentToSave.setGames(chosenGamesToRent);
            Rent savedRent = rentRepository.save(rentToSave);

            return rentMapper.fromRentToRentDto(savedRent);
//            rentToSave = rentMapper.updateFromRentCreationDtoToRent(rentToSave, rentCreationDto);

        } else {
            throw new RuntimeException("No game has been rented");
        }


    }

    public RentDto updateRent(String rentId, RentUpdateDto update) {
        Rent rent = rentRepository.findById(rentId)
                .orElseThrow(()
                        -> new EntityNotFoundException(EntityNotFoundException.ENTITY_NOT_FOUND_MESSAGE, "rent with uuid: " + rentId));
        Rent rentUpdated = rentMapper.updateFromRentUpdateDtoToRent(rent, update);
        Rent savedRent = rentRepository.save(rentUpdated);
        return rentMapper.fromRentToRentDto(savedRent);

    }

}
