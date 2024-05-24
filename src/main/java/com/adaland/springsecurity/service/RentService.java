package com.adaland.springsecurity.service;


import com.adaland.springsecurity.exception.EntityNotFoundException;
import com.adaland.springsecurity.mapper.GameMapper;
import com.adaland.springsecurity.mapper.RentMapper;
import com.adaland.springsecurity.model.auth.User;
import com.adaland.springsecurity.model.dao.Rent;
import com.adaland.springsecurity.model.dto.rent.RentCreationDto;
import com.adaland.springsecurity.model.dto.rent.RentDto;
import com.adaland.springsecurity.model.dto.rent.RentUpdateDto;
import com.adaland.springsecurity.repository.GameRepository;
import com.adaland.springsecurity.repository.RentRepository;
import com.adaland.springsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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


    /*public RentDto createRent(RentCreationDto rentCreationDto) {
        List<GameDto> gamesToRent = rentCreationDto.getGames();

        List<GameDto> availableGames = gameRepository.findAll().stream()
                .filter(game -> game.getStatus().equals(GameStatus.AVAILABLE))
                .map(gameMapper::fromGameToGameDto)
                .toList();

        boolean areGamesAvailable = new HashSet<>(gamesToRent).containsAll(availableGames);
        if(areGamesAvailable){
            Rent rent = rentMapper.fromRentCreationDtoToRent(rentCreationDto);
            gamesToRent.forEach(game->{
                GameUpdateDto updateDto = GameUpdateDto.builder()
                        .status(GameStatus.RENTED)
                        .build();
                this.gameService.updateGame(game.getId(), updateDto);
                rent.setActive(true);
            });

            Rent savedRent = rentRepository.save(rent);

            return rentMapper.fromRentToRentDto(savedRent);
        }else {
            throw new GameNotAvailableException(GameNotAvailableException.NOT_AVAILABLE_MESSAGE, gamesToRent.toString());
        }
    }*/

    public RentDto createRent(RentCreationDto rentCreationDto) {


        User user = userRepository.findByUsername(rentCreationDto.getUsername()).orElseThrow(() ->
                new EntityNotFoundException(EntityNotFoundException.ENTITY_NOT_FOUND_MESSAGE, "user with username: " + rentCreationDto.getUsername()));

        Rent rentToSave = new Rent();
        rentToSave.setUser(user);
        rentToSave = rentMapper.fromRentCreationDtoToRent(rentToSave, rentCreationDto);
        // TODO - calculate from games
        rentToSave.setCost(BigDecimal.ONE);

//        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Rent saved = rentRepository.save(rentToSave);

        return rentMapper.fromRentToRentDto(saved);
    }

    public RentDto updateRent(String rentId, RentUpdateDto update) {
        Rent rent = rentRepository.findById(rentId)
                .orElseThrow(()
                        -> new EntityNotFoundException(EntityNotFoundException.ENTITY_NOT_FOUND_MESSAGE, "rent with uuid: " + rentId));
        Rent rentUpdated = rentMapper.fromRentUpdateDtoToRent(rent, update);
        Rent savedRent = rentRepository.save(rentUpdated);
        return rentMapper.fromRentToRentDto(savedRent);

    }

}
