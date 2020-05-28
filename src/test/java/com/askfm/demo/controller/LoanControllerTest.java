package com.askfm.demo.controller;

import com.askfm.demo.dto.ApplyLoanRequestDto;
import com.askfm.demo.dto.LoanDto;
import com.askfm.demo.filter.RequestFrequencyFilter;
import com.askfm.demo.service.CountryDefinitionService;
import com.askfm.demo.service.LoanService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.askfm.demo.TestConstantHolder.LOAN_AMOUNT;
import static com.askfm.demo.TestConstantHolder.PERSONAL_ID;
import static com.askfm.demo.TestConstantHolder.TERM_DAYS;
import static com.askfm.demo.TestConstantHolder.USER_NAME;
import static com.askfm.demo.TestConstantHolder.USER_SURNAME;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LoanController.class)
@ExtendWith(SpringExtension.class)
class LoanControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CountryDefinitionService countryDefinitionService;

  @MockBean
  private LoanService loanService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void testApplyLoan_thenExpected201() throws Exception {
    //given
    when(countryDefinitionService.getCountryCode(any())).thenReturn("lv");

    var applyLoanDto = new ApplyLoanRequestDto();
    applyLoanDto.setAmount(LOAN_AMOUNT);
    applyLoanDto.setName(USER_NAME);
    applyLoanDto.setSurname(USER_SURNAME);
    applyLoanDto.setTerm(TERM_DAYS);
    applyLoanDto.setPersonalId(PERSONAL_ID);

    var content = objectMapper.writeValueAsString(applyLoanDto);

    //then
    mockMvc.perform(
        post("/loan/apply")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content))
        .andExpect(status().isCreated());
  }

  @Test
  void testApproveLoan_thenExpected200() throws Exception {
    //then
    mockMvc.perform(
        put("/loan/approve/1"))
        .andExpect(status().isOk());
  }

  @Test
  void testUserApprovedLoans_thenExpected200() throws Exception {
    //given
    List<LoanDto> expectedResponse = new ArrayList<>();
    var loanDto1 = new LoanDto();
    loanDto1.setAmount(LOAN_AMOUNT);
    loanDto1.setTerm(TERM_DAYS);

    var loanDto2 = new LoanDto();
    loanDto2.setName(USER_NAME);
    loanDto2.setSurname(USER_SURNAME);

    when(loanService.getAllUserApprovedLoans(anyLong())).thenReturn(expectedResponse);

    //then
    var mvcResult = mockMvc.perform(
        get("/loan/user/1/approved"))
        .andExpect(status().isOk())
        .andReturn();

    var loanDtoList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<LoanDto>>() {});
    assertEquals(loanDtoList, expectedResponse);
  }

  @Test
  void testAllApprovedLoans_thenExpected200() throws Exception {
    //given
    List<LoanDto> expectedResponse = new ArrayList<>();
    var loanDto1 = new LoanDto();
    loanDto1.setAmount(LOAN_AMOUNT);
    loanDto1.setTerm(TERM_DAYS);

    var loanDto2 = new LoanDto();
    loanDto2.setName(USER_NAME);
    loanDto2.setSurname(USER_SURNAME);

    when(loanService.getAllUserApprovedLoans(anyLong())).thenReturn(expectedResponse);

    //then
    var mvcResult = mockMvc.perform(
        get("/loan/approved"))
        .andExpect(status().isOk())
        .andReturn();

    var loanDtoList = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<LoanDto>>() {});
    assertEquals(loanDtoList, expectedResponse);
  }

}