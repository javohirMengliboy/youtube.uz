package com.example.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PhoneValidationUtilTest {
    PhoneValidationUtil phoneValidationUtil;

    @BeforeEach
    public void setUp(){
        phoneValidationUtil = new PhoneValidationUtil();
    }
    @Test
    @DisplayName("Should Validate")
    public void itShouldValidatePhone() {
        // given
        String phone = "+99891572123";
        // when
        boolean result = phoneValidationUtil.test(phone);
        // then
        Assertions.assertThat(result).isTrue();
        // Assertions.assertThat(result).isEqualTo(true);
    }

    @Test
    public void itShouldValidateWrongPhoneNumber() {
        // given
        String phone = "+9989157213";
        // when
        boolean result = phoneValidationUtil.test(phone);
        // then
        Assertions.assertThat(result).isFalse();
        // Assertions.assertThat(result).isEqualTo(true);
    }

    @Test
    public void itShouldValidatePhoneNumber_withoutPlus() {
        // given
        String phone = "998915721213";
        // when
        boolean result = phoneValidationUtil.test(phone);
        // then
        Assertions.assertThat(result).isFalse();
    }

    @Disabled
    @Test
    public void itShouldValidateNull() {
        // given
        // when
        boolean result = phoneValidationUtil.test(null);
        // then
        Assertions.assertThat(result).isFalse();
    }

    @ParameterizedTest
    @CsvSource({"998915721213", ".", "null"})
    public void itShouldValidatePhoneNumber_withAllGivenValues(String phone) {
        // given
        // when
        boolean result = phoneValidationUtil.test(phone);
        // then
        Assertions.assertThat(result).isFalse();
    }
}
