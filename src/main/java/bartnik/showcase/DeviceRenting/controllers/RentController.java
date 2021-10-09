package bartnik.showcase.DeviceRenting.controllers;

import bartnik.showcase.DeviceRenting.config.MessageProvider;
import bartnik.showcase.DeviceRenting.dto.PriceListRequestDto;
import bartnik.showcase.DeviceRenting.dto.RentRequestDto;
import bartnik.showcase.DeviceRenting.dto.ReportResponseDto;
import bartnik.showcase.DeviceRenting.services.PriceService;
import bartnik.showcase.DeviceRenting.services.RentService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class RentController {

    private final MessageProvider messageProvider;
    private final RentService rentService;
    private final PriceService priceService;

    @PutMapping("/price")
    public ResponseEntity<?> setPrice(@Valid @RequestBody PriceListRequestDto price) throws NotFoundException {
        priceService.setPrice(price);
        return ResponseEntity.ok(messageProvider.getSuccess().get("setPrice"));
    }

    @PostMapping("/rent")
    public ResponseEntity<String> setRent(@Valid @RequestBody RentRequestDto rent) throws NotFoundException {
        rentService.saveRent(rent);
        return ResponseEntity.ok(messageProvider.getSuccess().get("saveRent"));
    }

    @GetMapping("/report/{customerId}")
    public ResponseEntity<ReportResponseDto> getReportForMonth(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate period, @PathVariable Long customerId) {
        return ResponseEntity.ok(rentService.createReport(customerId, period));
    }
}
