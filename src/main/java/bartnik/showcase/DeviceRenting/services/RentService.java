package bartnik.showcase.DeviceRenting.services;

import bartnik.showcase.DeviceRenting.dto.RentDto;
import bartnik.showcase.DeviceRenting.dto.RentRequestDto;
import bartnik.showcase.DeviceRenting.dto.ReportResponseDto;
import bartnik.showcase.DeviceRenting.entities.RentEntity;
import bartnik.showcase.DeviceRenting.mappers.RentMapper;
import bartnik.showcase.DeviceRenting.repositories.RentRepository;
import bartnik.showcase.DeviceRenting.util.Period;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RentService {

    private final RentMapper rentMapper;
    private final RentRepository rentRepository;

    public void saveRent(RentRequestDto request) throws NotFoundException {
        rentRepository.save(rentMapper.mapRentRequestDtoToRentEntity(request));
    }

    public ReportResponseDto createReport(Long customerId, LocalDate period) {
        LocalDateTime periodFrom = period.minusDays(period.getDayOfMonth() - 1).atStartOfDay();
        LocalDateTime periodTo = periodFrom.plusDays(period.lengthOfMonth() - 1).plusHours(23).plusMinutes(59);
        ArrayList<RentEntity> rentEntities = rentRepository.getAllRentalsForUserInPeriod(customerId, periodFrom, periodTo);

        return ReportResponseDto.builder()
                .rents(getRentDtoList(rentEntities))
                .charge(getCharge(rentEntities))
                .period(new Period(periodFrom.toLocalDate(), periodTo.toLocalDate()))
                .build();
    }

    private ArrayList<RentDto> getRentDtoList(ArrayList<RentEntity> rentEntities) {
        return (ArrayList<RentDto>) rentEntities
                .stream()
                .map(rentMapper::mapRentEntityToRentDto)
                .collect(Collectors.toList());
    }

    private BigDecimal getCharge(ArrayList<RentEntity> rentEntities) {
        return total(rentEntities)
                .stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private ArrayList<BigDecimal> total(ArrayList<RentEntity> rentEntities) {
        return (ArrayList<BigDecimal>) rentEntities
                .stream()
                .map((RentEntity::getPrice))
                .collect(Collectors.toList());
    }
}
