package com.example.emp.controller;

import com.example.emp.dao.repository.UserRepository;
import com.example.emp.service.TimeKeepService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TimeKeepControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TimeKeepService mockTimeKeepService;

    @InjectMocks
    private TimeKeepController timeKeepController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(timeKeepController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();

    }

    @Test
    void testCheckin() throws Exception {
        // Setup
        when(mockTimeKeepService.checkin(0)).thenReturn("message");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/time-keep/checkin/{code}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void testCheckout() throws Exception {
        // Setup
        when(mockTimeKeepService.checkout(0)).thenReturn("message");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/time-keep/checkout/{code}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getStatus()).isEqualTo(200);
    }
}
