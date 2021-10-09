package bartnik.showcase.DeviceRenting.controllers;

import bartnik.showcase.DeviceRenting.dto.PriceListRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    void setPrice_should_return_200() throws Exception {
        this.mockMvc.perform(put("/api/price")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(PriceListRequestDto.builder().pricePerMinute(BigDecimal.valueOf(6d)).deviceName("Pralka").build())))
                .andExpect(status().isOk())
                .andExpect(content().string("Price has been set."));
    }

    @Test
    void setPrice_should_return_404() throws Exception {
        this.mockMvc.perform(put("/api/price")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(PriceListRequestDto.builder().pricePerMinute(BigDecimal.valueOf(6d)).deviceName("Test").build())))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Device has not been found."));
    }

    @Test
    void setPrice_should_return_400() throws Exception {
        this.mockMvc.perform(put("/api/price")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(PriceListRequestDto.builder().pricePerMinute(BigDecimal.valueOf(6d)).build())))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Request body is invalid."));
    }

    @Test
    void setRent_should_return_200() throws Exception {
        this.mockMvc.perform(post("/api/rent")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"customerId\": \"1\",\n" +
                        "    \"deviceName\": \"Pralka\",\n" +
                        "    \"startDateTime\": \"2021-09-08T10:12:11\",\n" +
                        "    \"endDateTime\": \"2021-09-08T11:25:31\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Rent information has been saved."));
    }

    @Test
    void setRent_should_return_404() throws Exception {
        this.mockMvc.perform(post("/api/rent")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"customerId\": \"5\",\n" +
                        "    \"deviceName\": \"Pralka\",\n" +
                        "    \"startDateTime\": \"2021-09-08T10:12:11\",\n" +
                        "    \"endDateTime\": \"2021-09-08T11:25:31\"\n" +
                        "}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Customer has not been found."));
    }

    @Test
    void setRent_should_return_400() throws Exception {
        this.mockMvc.perform(post("/api/rent")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"customerId\": \"1\",\n" +
                        "    \"deviceName\": \"Pralka\",\n" +
                        "    \"endDateTime\": \"2021-09-08T11:25:31\"\n" +
                        "}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Request body is invalid."));
    }

    @Test
    void getReportForMonth_should_return_200() throws Exception {
        this.mockMvc.perform(get("/api/report/1?period=2021-10-01")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(reportFilled));
    }

    @Test
    void getReportForMonth_should_return_200_and_empty_report_because_wrong_period() throws Exception {
        this.mockMvc.perform(get("/api/report/1?period=2021-11-01")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(reportEmptyWrongPeriod));
    }

    @Test
    void getReportForMonth_should_return_200_and_empty_report_because_wrong_customer() throws Exception {
        this.mockMvc.perform(get("/api/report/5?period=2021-10-01")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(reportEmptyWrongCustomer));
    }

    String reportFilled = "{\"rents\":[{\"customerFullName\":\"Name Surname\",\"deviceName\":\"Pralka\",\"startDateTime\":\"2021-10-10T10:10:00\",\"endDateTime\":\"2021-10-10T11:11:00\",\"price\":610.00}],\"charge\":610.00,\"period\":{\"from\":\"2021-10-01\",\"to\":\"2021-10-31\"}}";
    String reportEmptyWrongPeriod = "{\"rents\":[],\"charge\":0,\"period\":{\"from\":\"2021-11-01\",\"to\":\"2021-11-30\"}}";
    String reportEmptyWrongCustomer = "{\"rents\":[],\"charge\":0,\"period\":{\"from\":\"2021-10-01\",\"to\":\"2021-10-31\"}}";


    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}