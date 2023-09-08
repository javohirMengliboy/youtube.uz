package com.example.sevice;

import com.example.dto.ApiResponseDTO;
import com.example.dto.ProfileDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.Language;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.repository.ProfileRepository;
import com.example.service.AuthService;
import com.example.service.MailSenderService;
import com.example.service.ResourceBundleService;
import com.example.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;
@Slf4j

public class AuthServiceTest {
    private AuthService underTest;
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private MailSenderService mailSenderService;

    @Mock
    private ResourceBundleService resourceBundleService;
    @Captor
    private ArgumentCaptor<ProfileEntity> customProfileEntityArgumentCapture;

    private MD5Util md5Util;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        md5Util = new MD5Util();
        underTest = new AuthService();
        underTest.setProfileRepository(profileRepository);
        underTest.setMailSenderService(mailSenderService);
        underTest.setMd5Util(md5Util);
        underTest.setResourceBundleService(resourceBundleService);
    }


    @Test
    public void itShouldRegisterNewProfile() {
        //given
        ProfileDTO dto = new ProfileDTO();
        dto.setName("Alish");
        dto.setSurname("Aliyev");
        dto.setEmail("alish@mail.ru");
        dto.setPassword("123456");
        // when
        ApiResponseDTO response = underTest.registration(dto, Language.uz);
        // then
        Assertions.assertThat(response.isStatus()).isTrue();
    }

    @Test
    public void itShouldNotRegisterNewProfile_existingEmail() {
        // given
        ProfileDTO dto = new ProfileDTO();
        dto.setName("Alish");
        dto.setSurname("Aliyev");
        dto.setEmail("alish@mail.ru");
        dto.setPassword("123456");
        //
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setStatus(ProfileStatus.REGISTRATION);
        BDDMockito.given(profileRepository.findByEmail(dto.getEmail())).willReturn(Optional.of(profileEntity));
        // when
        ApiResponseDTO response = underTest.registration(dto, Language.uz);
        // then
        Assertions.assertThat(response.isStatus()).isTrue();
        BDDMockito.then(profileRepository).should().delete(customProfileEntityArgumentCapture.capture());
        Assertions.assertThat(customProfileEntityArgumentCapture.getValue()).isEqualTo(profileEntity);
    }

    @Test
    public void itShouldNotRegisterNewProfile_existingAndActiveProfile() {
        //given
        ProfileDTO dto = new ProfileDTO();
        dto.setEmail("alish@mail.ru");
        //
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setStatus(ProfileStatus.ACTIVE);
        BDDMockito.given(profileRepository.findByEmail(dto.getEmail())).willReturn(Optional.of(profileEntity));
        // when
        ApiResponseDTO response = underTest.registration(dto, Language.uz);
        // then
        Assertions.assertThat(response.isStatus()).isFalse();
        Assertions.assertThat(response.getMessage()).isEqualTo(resourceBundleService.getMessage("email.already.exists", Language.uz));
        BDDMockito.then(mailSenderService).shouldHaveNoInteractions();
        BDDMockito.then(profileRepository).should(BDDMockito.times(1)).findByEmail(BDDMockito.any(String.class));
        BDDMockito.then(profileRepository).should(BDDMockito.times(0)).save(BDDMockito.any(ProfileEntity.class));
    }

    @Test
    public void itShouldAuthorize() {
        //given
        ProfileDTO dto = new ProfileDTO();
        dto.setEmail("998");
        dto.setPassword("12345");
        //
        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setName("Ali");
        profileEntity.setSurname("Aliyev");
        profileEntity.setEmail("998");
        profileEntity.setStatus(ProfileStatus.ACTIVE);
        profileEntity.setPassword(md5Util.encode(dto.getPassword()));
        profileEntity.setRole(ProfileRole.ROLE_ADMIN);
        BDDMockito.given(profileRepository.findByEmail(dto.getEmail())).willReturn(Optional.of(profileEntity));
        // when
        ApiResponseDTO response = underTest.authorization(dto);
        // then
        Assertions.assertThat(response.isStatus()).isTrue();
        ProfileDTO profileDTO = (ProfileDTO) response.getData();

//        Assertions.assertThat(profileDTO.getName()).isEqualTo(profileEntity.getName());
        Assertions.assertThat(profileDTO.getJwt()).isNotBlank();

        Assertions.assertThat(profileDTO)
                .usingRecursiveComparison()
                .comparingOnlyFields("role", "name", "surname", "email")
                .isEqualTo(profileEntity);
    }
}

