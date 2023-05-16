package com.example.emp.dao.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
class TimekeepingTest {

    @Mock
    private User mockIdUser;

    private Timekeeping timekeepingUnderTest;

    @BeforeEach
    void setUp() {
        timekeepingUnderTest = new Timekeeping(0L, "checkin", "checkout", mockIdUser,
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    }

    @Test
    void testGetId() {
        // Setup
        final Long expectedResult = 0L;

        // Run the test
        final Long result = timekeepingUnderTest.getId();

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
