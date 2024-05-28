package com.adaland.springsecurity.service;


import com.adaland.springsecurity.exception.EntityNotFoundException;
import com.adaland.springsecurity.exception.GameNotAvailableException;
import com.adaland.springsecurity.mapper.GameMapper;
import com.adaland.springsecurity.mapper.RentMapper;
import com.adaland.springsecurity.model.auth.User;
import com.adaland.springsecurity.model.dao.Game;
import com.adaland.springsecurity.model.dao.GameStatus;
import com.adaland.springsecurity.model.dao.Rent;
import com.adaland.springsecurity.model.dao.RentStatus;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public RentDto findById(long rentId) {
        Rent rent = rentRepository.findById(rentId).orElseThrow(() ->
                new EntityNotFoundException(EntityNotFoundException.ENTITY_NOT_FOUND_MESSAGE, "rent with id: " + rentId));
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
        List<Long> gamesIdToRent = rentCreationDto.getGames();
        List<Game> chosenGamesToRent = new ArrayList<>();

        boolean isGameRented = false;

        User user = userRepository.findByUsername(rentCreationDto.getUsername()).orElseThrow(() ->
                new EntityNotFoundException(EntityNotFoundException.ENTITY_NOT_FOUND_MESSAGE, "user with username: " + rentCreationDto.getUsername()));


        StringBuilder message = new StringBuilder();

        for (Long gameId : gamesIdToRent) {
            Game gameToRent = gameRepository.findById(gameId).orElseThrow(() ->
                    new EntityNotFoundException(EntityNotFoundException.ENTITY_NOT_FOUND_MESSAGE, "game with id: " + gameId));
            if (gameToRent.getStatus().equals(GameStatus.AVAILABLE)) {
                gameToRent.setRent(rentToSave);
                gameToRent.setStatus(GameStatus.RENTED);
                chosenGamesToRent.add(gameToRent);
                gameRepository.save(gameToRent);
                isGameRented = true;
            }
            else{
                message.append(gameToRent.getTitle()).append(" ");
            }

        }


        if (isGameRented) {

            rentToSave.setCreationTs(LocalDateTime.now());

            rentToSave.setStartDate(rentCreationDto.getStartDate());
            rentToSave.setEndDate(rentCreationDto.getStartDate().plusDays(7));
            rentToSave.setGames(chosenGamesToRent);
            rentToSave.setUser(user);
            rentToSave.setStatus(RentStatus.ACTIVE);
            rentToSave.setSettled(false);
            rentToSave.setUpdateTs(LocalDateTime.now());
//            rentToSave.setCost(rentToSave.getCost().add(ga));

            Rent savedRent = rentRepository.save(rentToSave);

            return rentMapper.fromRentToRentDto(savedRent);
//            rentToSave = rentMapper.updateFromRentCreationDtoToRent(rentToSave, rentCreationDto);

        } else {

            throw new GameNotAvailableException(GameNotAvailableException.NOT_AVAILABLE_MESSAGE,message.toString());
        }


    }

    public RentDto updateRent(long rentId, RentUpdateDto update) {
        Rent rent = rentRepository.findById(rentId)
                .orElseThrow(()
                        -> new EntityNotFoundException(EntityNotFoundException.ENTITY_NOT_FOUND_MESSAGE, "rent with uuid: " + rentId));
        Rent rentUpdated = rentMapper.updateFromRentUpdateDtoToRent(rent, update);
        Rent savedRent = rentRepository.save(rentUpdated);
        return rentMapper.fromRentToRentDto(savedRent);

    }



    public RentDto returnGamesOfRent(long rentId) {
        log.debug("here");
        Rent rent = rentRepository.findById(rentId)
                .orElseThrow(()
                        -> new EntityNotFoundException(EntityNotFoundException.ENTITY_NOT_FOUND_MESSAGE, "rent with id: " + rentId));

        rent.getGames().forEach(game->{
            Game gameToReturn = gameRepository.findById(game.getId()).orElseThrow(()
                    -> new EntityNotFoundException(EntityNotFoundException.ENTITY_NOT_FOUND_MESSAGE, "game with id: " + game.getId()));

            gameToReturn.setStatus(GameStatus.AVAILABLE);
            gameRepository.save(gameToReturn);
        });

        rent.setSettled(true);
        rent.setStatus(RentStatus.COMPLETED);
        Rent savedRent = rentRepository.save(rent);
        return rentMapper.fromRentToRentDto(savedRent);

    }

}
