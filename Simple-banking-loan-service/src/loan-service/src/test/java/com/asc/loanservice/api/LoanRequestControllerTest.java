package com.asc.loanservice.api;

import com.asc.loanservice.contracts.loanrequest.LoanRequestDto;
import com.asc.loanservice.contracts.loanrequest.LoanRequestMapper;
import com.asc.loanservice.domain.CustomerCheck;
import com.asc.loanservice.domain.LoanRequest;
import com.asc.loanservice.domain.LoanRequest.LoanRequestStatus;
import com.asc.loanservice.domain.LoanRequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import static com.asc.loanservice.api.LoanRequestControllerTest.ControllerTestConfiguration.BASE_CONTROLLER_ENDPOINT;
import static com.asc.loanservice.api.LoanRequestControllerTest.ControllerTestConfiguration.asJsonString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoanRequestControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    LoanRequestMapper loanRequestMapper;

    @MockBean
    LoanRequestService loanRequestService;
    @MockBean
    CustomerCheck customerCheck;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void registerWhenLoanRequestCorrect() throws Exception {
        //given
        LoanRequest loanRequest = loanRequestMapper.asEntity(getLoanRequestDto());
        loanRequest = loanRequest.toBuilder().loanRequestNumber(1)
                .evaluationResult(LoanRequestStatus.REJECTED).build();

        //when
        when(loanRequestService.registerLoanRequest(Mockito.any())).thenReturn(loanRequest);

        when(customerCheck.isCustomerDebtor(Mockito.anyString())).thenReturn(Boolean.FALSE);

        String jsonToSend = asJsonString(getLoanRequestDto());
        ResultActions resultActions = mockMvc
                .perform(post(BASE_CONTROLLER_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(jsonToSend)));

        //then
        resultActions
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    public void registerWhenLoanRequestIncorrect() throws Exception {
        String jsonToSend = asJsonString(LoanRequestDto.builder().build());
        ResultActions resultActions = mockMvc
                .perform(post(BASE_CONTROLLER_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Objects.requireNonNull(jsonToSend)));

        //then
        resultActions
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getByNumberWhenLoanExist() throws Exception {
        //given
        LoanRequest loanRequest = loanRequestMapper.asEntity(getLoanRequestDto());
        loanRequest = loanRequest.toBuilder().loanRequestNumber(1)
                .evaluationResult(LoanRequestStatus.APPROVED).build();

        //when
        when(loanRequestService.getLoanRequest(Mockito.anyLong())).thenReturn(loanRequest);

        String loan = BASE_CONTROLLER_ENDPOINT + "/1";
        ResultActions resultActions = mockMvc
                .perform(get(loan)
                        .contentType(MediaType.APPLICATION_JSON));
        //then
        resultActions
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    private LoanRequestDto getLoanRequestDto() {
        int numberOfInstallments = 12;
        return LoanRequestDto.builder()
                .customerName("Pawel")
                .customerBirthday(LocalDate.of(2000, 5, 1))
                .customerTaxId("99999")
                .customerMonthlyIncome(BigDecimal.valueOf(5679.06))
                .loanAmount(BigDecimal.valueOf(10000))
                .numberOfInstallments(numberOfInstallments)
                .firstInstallmentDate(LocalDate.now().plusMonths(12))
                .build();
    }

    @TestConfiguration
    static class ControllerTestConfiguration {
        static final String BASE_CONTROLLER_ENDPOINT = "/api/loans";

        public static String asJsonString(final Object obj) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.findAndRegisterModules();
                objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
                return objectMapper.writeValueAsString(obj);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}