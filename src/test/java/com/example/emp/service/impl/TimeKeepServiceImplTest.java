package com.example.emp.service.impl;

import com.example.emp.common.utils.PsException;
import com.example.emp.dao.entity.Timekeeping;
import com.example.emp.dao.entity.User;
import com.example.emp.dao.repository.TimekeepingRepository;
import com.example.emp.dao.repository.UserRepository;
import com.example.emp.dto.CustomUserDetails;
import com.example.emp.service.UserForCheckin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import java.security.Principal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TimeKeepServiceImplTest {

    @Mock
    private TimekeepingRepository mockTimeKeepingRepository;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private UserForCheckin mockUserForCheckin;

    @InjectMocks
    private TimeKeepServiceImpl timeKeepServiceImplUnderTest;

    @Test
    void testCheckin() {
        // Setup
        // Configure UserRepository.findByEmailAndCode(...).
        final User user = new User(0L, "username", "password", "firstName", "lastName", "email", 0,
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "imgUser", Arrays.asList(
                new Timekeeping(0L, "checkin", "checkout", null,
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())));
        when(mockUserRepository.findByEmailAndCode("email", 0)).thenReturn(user);

        // Configure TimekeepingRepository.save(...).
        SecurityContext context = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        MockedStatic<SecurityContextHolder> securityContextHolderMockedStatic = mockStatic(SecurityContextHolder.class);
        securityContextHolderMockedStatic.when(SecurityContextHolder::getContext).thenReturn(context);
        when(context.getAuthentication()).thenReturn(authentication);
        CustomUserDetails customUserDetails = mock(CustomUserDetails.class);
        when(customUserDetails.getUser()).thenReturn(user);

        when(authentication.getPrincipal()).thenReturn(customUserDetails);


        final Timekeeping timekeeping = new Timekeeping(0L, "checkin", "checkout",
                new User(0L, "username", "password", "firstName", "lastName", "email", 0,
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "imgUser", Arrays.asList()),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        when(mockTimeKeepingRepository.save(any(Timekeeping.class))).thenReturn(timekeeping);

        // Run the test
        final String result = timeKeepServiceImplUnderTest.checkin(0);

        // Verify the results
        assertThat(result).isEqualTo("username Đã checkin thành công");
        verify(mockTimeKeepingRepository).save(any(Timekeeping.class));
    }

    @Test
    public void testCheckin_returnEmailDefaultOidcUser() {
        // Setup
        // Configure UserRepository.findByEmailAndCode(...).
        final User user = new User(0L, "username", "password", "firstName", "lastName", "email", 0,
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "imgUser", Arrays.asList(
                new Timekeeping(0L, "checkin", "checkout", null,
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())));
        when(mockUserRepository.findByEmailAndCode("email", 0)).thenReturn(user);

        // Configure TimekeepingRepository.save(...).
        SecurityContext context = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        MockedStatic<SecurityContextHolder> securityContextHolderMockedStatic = mockStatic(SecurityContextHolder.class);
        securityContextHolderMockedStatic.when(SecurityContextHolder::getContext).thenReturn(context);
        when(context.getAuthentication()).thenReturn(authentication);
        DefaultOidcUser defaultOidcUser = mock(DefaultOidcUser.class);

        when(authentication.getPrincipal()).thenReturn(defaultOidcUser);
        when(defaultOidcUser.getEmail()).thenReturn("email");


        final Timekeeping timekeeping = new Timekeeping(0L, "checkin", "checkout",
                new User(0L, "username", "password", "firstName", "lastName", "email", 0,
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "imgUser", Arrays.asList()),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        when(mockTimeKeepingRepository.save(any(Timekeeping.class))).thenReturn(timekeeping);

        // Run the test
        final String result = timeKeepServiceImplUnderTest.checkin(0);

        // Verify the results
        assertThat(result).isEqualTo("username Đã checkin thành công");
        verify(mockTimeKeepingRepository).save(any(Timekeeping.class));
    }

    @Test
    void testCheckinUserNull() {
        // Setup
        // Configure UserRepository.findByEmailAndCode(...).
        final User user = new User(0L, "username", "password", "firstName", "lastName", "email", 0,
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "imgUser", Arrays.asList(
                new Timekeeping(0L, "checkin", "checkout", null,
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())));
        when(mockUserRepository.findByEmailAndCode("email", 0)).thenReturn(null);

        // Configure TimekeepingRepository.save(...).
        SecurityContext context = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        MockedStatic<SecurityContextHolder> securityContextHolderMockedStatic = mockStatic(SecurityContextHolder.class);
        securityContextHolderMockedStatic.when(SecurityContextHolder::getContext).thenReturn(context);
        when(context.getAuthentication()).thenReturn(authentication);
        DefaultOidcUser defaultOidcUser = mock(DefaultOidcUser.class);

        when(authentication.getPrincipal()).thenReturn(defaultOidcUser);
        when(defaultOidcUser.getEmail()).thenReturn("email");


        final Timekeeping timekeeping = new Timekeeping(0L, "checkin", "checkout",
                new User(0L, "username", "password", "firstName", "lastName", "email", 0,
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "imgUser", Arrays.asList()),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Run the test
        final String result = timeKeepServiceImplUnderTest.checkin(0);

        // Verify the results
        assertThat(result).isEqualTo("Mã nhân viên không tồn tại hoặc không phải mã của bạn");
    }



    @Test
    void testCheckout() {
        // Setup
        // Configure UserRepository.findByEmailAndCode(...).
        final User user = new User(0L, "username", "password", "firstName", "lastName", "email", 0,
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "imgUser", Arrays.asList(
                new Timekeeping(0L, "checkin", "checkout", null,
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())));
        when(mockUserRepository.findByEmailAndCode("email", 0)).thenReturn(user);

        // Configure UserRepository.findByCode(...).
        final User user1 = new User(0L, "username", "password", "firstName", "lastName", "email", 0,
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "imgUser", Arrays.asList(
                new Timekeeping(0L, "checkin", "checkout", null,
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())));
        when(mockUserRepository.findByCode(0)).thenReturn(user1);

        // Configure TimekeepingRepository.findByCodeEmpAndCheckin(...).
        final Timekeeping timekeeping = new Timekeeping(0L, "checkin", "checkout",
                new User(0L, "username", "password", "firstName", "lastName", "email", 0,
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "imgUser", Arrays.asList()),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        when(mockTimeKeepingRepository.findByCodeEmpAndCheckin(0L,
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).thenReturn(timekeeping);

        // Configure TimekeepingRepository.save(...).
        final Timekeeping timekeeping1 = new Timekeeping(0L, "checkin", "checkout",
                new User(0L, "username", "password", "firstName", "lastName", "email", 0,
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), "imgUser", Arrays.asList()),
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        when(mockTimeKeepingRepository.save(any(Timekeeping.class))).thenReturn(timekeeping1);

        // Run the test
        final String result = timeKeepServiceImplUnderTest.checkout(0);

        // Verify the results
        assertThat(result).isEqualTo("Mã nhân viên không tồn tại hoặc không phải mã của bạn");
        verify(mockTimeKeepingRepository).save(any(Timekeeping.class));
    }

    @Test
    void testCheckout_UserRepositoryFindByEmailAndCodeReturnsNull() {
        // Setup
        when(mockUserRepository.findByEmailAndCode("email", 0)).thenReturn(null);

        // Run the test
        final String result = timeKeepServiceImplUnderTest.checkout(0);

        // Verify the results
        assertThat(result).isEqualTo("Mã nhân viên không tồn tại hoặc không phải mã của bạn");
    }

    @Test
    void testFindAllEMPNotCheckin() {
        // Setup
        when(mockUserRepository.listEmpNotCheckin(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).thenReturn(Arrays.asList("value"));

        // Run the test
        final List<String> result = timeKeepServiceImplUnderTest.findAllEMPNotCheckin(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Verify the results
        assertThat(result).isEqualTo(Arrays.asList("value"));
    }

    @Test
    void testFindAllEMPNotCheckin_UserRepositoryReturnsNoItems() {
        // Setup
        when(mockUserRepository.listEmpNotCheckin(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).thenReturn(Collections.emptyList());

        // Run the test
        assertThatThrownBy(() -> timeKeepServiceImplUnderTest.findAllEMPNotCheckin(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).isInstanceOf(PsException.class);
    }

    @Test
    void testFindAllEMPNotCheckout() {
        // Setup
        when(mockUserRepository.listEmpNotCheckOut(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).thenReturn(Arrays.asList("value"));

        // Run the test
        final List<String> result = timeKeepServiceImplUnderTest.findAllEMPNotCheckout(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Verify the results
        assertThat(result).isEqualTo(Arrays.asList("value"));
    }

    @Test
    void testFindAllEMPNotCheckout_UserRepositoryReturnsNull() {
        // Setup
        when(mockUserRepository.listEmpNotCheckOut(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).thenReturn(null);

        // Run the test
        assertThatThrownBy(() -> timeKeepServiceImplUnderTest.findAllEMPNotCheckout(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).isInstanceOf(PsException.class);
    }

    @Test
    void testFindAllEMPNotCheckout_UserRepositoryReturnsNoItems() {
        // Setup
        when(mockUserRepository.listEmpNotCheckOut(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime())).thenReturn(Collections.emptyList());

        // Run the test
        final List<String> result = timeKeepServiceImplUnderTest.findAllEMPNotCheckout(
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }
}
