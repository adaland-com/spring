package com.adaland.springsecurity.repository;

import com.adaland.springsecurity.model.dao.Game;
import com.adaland.springsecurity.model.dao.GameCategory;
import com.adaland.springsecurity.model.dao.Rent;
import com.adaland.springsecurity.model.dao.RentStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class RentRepositoryTest {
    @Autowired
    private RentRepository rentRepository;

    @Test
    public void givenRentObject_whenSave_thenReturnSavedRent() {
        BigDecimal price =BigDecimal.valueOf(20.00);
        LocalDate startDate= LocalDate.of(2024,6,24);
        LocalDate endDate = startDate.plusDays(7);
        LocalDateTime localDateTime=LocalDateTime.now();
        RentStatus status =RentStatus.ACTIVE;
        boolean isSettled = false;

        Rent rent = Rent.builder()
                .cost(price)
                .creationTs(localDateTime)
                .updateTs(localDateTime)
                .startDate(startDate)
                .endDate(endDate)
                .status(status)
                .isSettled(isSettled)
                .build();

        Rent savedRent = rentRepository.save(rent);

        assertThat(savedRent).isNotNull();
        assertThat(savedRent.getCost()).isEqualTo(price);
        assertThat(savedRent.getCreationTs()).isEqualTo(localDateTime);
        assertThat(savedRent.getUpdateTs()).isEqualTo(localDateTime);
        assertThat(savedRent.getStartDate()).isEqualTo(startDate);
        assertThat(savedRent.getEndDate()).isEqualTo(endDate);
        assertThat(savedRent.getStatus()).isEqualTo(status);
        assertThat(savedRent.isSettled()).isEqualTo(isSettled);
    }
    @Test
    public void givenRentObject_whenFindAll_thenReturnRentList() {
        BigDecimal price =BigDecimal.valueOf(20.00);
        LocalDate startDate= LocalDate.of(2024,6,24);
        LocalDate endDate = startDate.plusDays(7);
        LocalDateTime localDateTime=LocalDateTime.now();
        RentStatus status =RentStatus.ACTIVE;
        boolean isSettled = false;

        Rent rent = Rent.builder()
                .cost(price)
                .creationTs(localDateTime)
                .updateTs(localDateTime)
                .startDate(startDate)
                .endDate(endDate)
                .status(status)
                .isSettled(isSettled)
                .build();

        BigDecimal price2 =BigDecimal.valueOf(120.00);
        LocalDate startDate2= LocalDate.of(2024,6,24);
        LocalDate endDate2 = startDate.plusDays(7);
        LocalDateTime localDateTime2=LocalDateTime.now();
        RentStatus status2 =RentStatus.ACTIVE;
        boolean isSettled2 = false;

        Rent rent2 = Rent.builder()
                .cost(price2)
                .creationTs(localDateTime2)
                .updateTs(localDateTime2)
                .startDate(startDate2)
                .endDate(endDate2)
                .status(status2)
                .isSettled(isSettled2)
                .build();

        Rent savedRent = rentRepository.save(rent);
        Rent savedRent2 = rentRepository.save(rent2);

        List<Rent> gameList = rentRepository.findAll();

        assertThat(gameList.size()).isEqualTo(2);
        assertThat(gameList.containsAll(List.of(savedRent,savedRent2)));
    }

    @Test
    public void givenRentObject_whenFindById_thenReturnSavedRent() {
        BigDecimal price =BigDecimal.valueOf(20.00);
        LocalDate startDate= LocalDate.of(2024,6,24);
        LocalDate endDate = startDate.plusDays(7);
        LocalDateTime localDateTime=LocalDateTime.now();
        RentStatus status =RentStatus.ACTIVE;
        boolean isSettled = false;

        Rent rent = Rent.builder()
                .cost(price)
                .creationTs(localDateTime)
                .updateTs(localDateTime)
                .startDate(startDate)
                .endDate(endDate)
                .status(status)
                .isSettled(isSettled)
                .build();

        Rent savedRent = rentRepository.save(rent);
        Rent foundRent = rentRepository.findById(savedRent.getId()).orElse(null);

        assertThat(foundRent).isNotNull();
        assertThat(foundRent.getCost()).isEqualTo(price);
        assertThat(foundRent.getCreationTs()).isEqualTo(localDateTime);
        assertThat(foundRent.getUpdateTs()).isEqualTo(localDateTime);
        assertThat(foundRent.getStartDate()).isEqualTo(startDate);
        assertThat(foundRent.getEndDate()).isEqualTo(endDate);
        assertThat(foundRent.getStatus()).isEqualTo(status);
        assertThat(foundRent.isSettled()).isEqualTo(isSettled);
    }

    @Test
    public void givenRentObject_whenUpdate_thenReturnUpdatedRent() {
        BigDecimal price =BigDecimal.valueOf(20.00);
        LocalDate startDate= LocalDate.of(2024,6,24);
        LocalDate endDate = startDate.plusDays(7);
        LocalDateTime localDateTime=LocalDateTime.now();
        RentStatus status =RentStatus.ACTIVE;
        boolean isSettled = false;

        Rent rent = Rent.builder()
                .cost(price)
                .creationTs(localDateTime)
                .updateTs(localDateTime)
                .startDate(startDate)
                .endDate(endDate)
                .status(status)
                .isSettled(isSettled)
                .build();

        Rent savedRent = rentRepository.save(rent);
        Rent foundRent = rentRepository.findById(savedRent.getId()).orElse(null);

        BigDecimal updatedCost = BigDecimal.valueOf(37.60);

        assertThat(foundRent).isNotNull();
        foundRent.setCost(updatedCost);
        Rent updatedRent = rentRepository.save(foundRent);


        assertThat(updatedRent.getCost()).isEqualTo(updatedCost);
        assertThat(updatedRent.getCreationTs()).isEqualTo(localDateTime);
        assertThat(updatedRent.getUpdateTs()).isEqualTo(localDateTime);
        assertThat(updatedRent.getStartDate()).isEqualTo(startDate);
        assertThat(updatedRent.getEndDate()).isEqualTo(endDate);
        assertThat(updatedRent.getStatus()).isEqualTo(status);
        assertThat(updatedRent.isSettled()).isEqualTo(isSettled);
    }

    @Test
    public void whenFindByNonExistingRent_thenThrowsException() {
        long rentId = 1L;

        Optional<Rent> byId = rentRepository.findById(rentId);

        assertTrue(byId.isEmpty());
    }

}