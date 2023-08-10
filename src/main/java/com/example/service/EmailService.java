package com.example.service;

import com.example.dto.ApiResponseDTO;
import com.example.dto.EmailDTO;
import com.example.dto.EmailFilterDTO;
import com.example.dto.FilterResultDTO;
import com.example.entity.EmailEntity;
import com.example.repository.CustomEmailRepository;
import com.example.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private CustomEmailRepository customEmailRepository;

    public ApiResponseDTO create(EmailDTO dto) {

        EmailEntity entity = new EmailEntity();
        entity.setTo_email(dto.getTo_email());
        entity.setTitle(dto.getTitle());
        entity.setMessage(dto.getMessage());
        emailRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreated_date(entity.getCreated_date());

        mailSenderService.sendEmail(dto.getTo_email(), "YouTube registration compilation",
                "Bu youtube saytining tekshiruv email habarnomasi");
        return new ApiResponseDTO(true, "Sent message to email");

    }




    public PageImpl<EmailDTO> emailPagination(int page, int size){
        Pageable pageable = PageRequest.of(page,size, Sort.Direction.DESC,"id");
        Page<EmailEntity> pageObj = emailRepository.findAll(pageable);
        List<EmailDTO> list = pageObj.stream().map(this::toDTO).collect(Collectors.toList());
        return new PageImpl<>(list,pageable,pageObj.getTotalElements());

    }

    public PageImpl<EmailDTO> filter(EmailFilterDTO dto, int page, int size){
        FilterResultDTO<EmailEntity> result = customEmailRepository.filter(dto,page,size);
        List<EmailDTO> list = result.getContent().stream().map(this::toDTO).collect(Collectors.toList());

        return new PageImpl<>(list,PageRequest.of(page,size),result.getTotalCount());
    }




    public EmailDTO toDTO(EmailEntity entity) {
        EmailDTO dto = new EmailDTO();
        dto.setId(entity.getId());
        dto.setTo_email(entity.getTo_email());
        dto.setTitle(entity.getTitle());
        dto.setMessage(entity.getMessage());
        dto.setCreated_date(entity.getCreated_date());
        return dto;
    }


}
