package com.example.service;

import com.example.dto.ApiResponseDTO;
import com.example.dto.EmailHistoryDTO;
import com.example.dto.EmailFilterDTO;
import com.example.dto.FilterResultDTO;
import com.example.entity.EmailHistoryEntity;
import com.example.repository.CustomEmailRepository;
import com.example.repository.EmailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailHistoryService {

    @Autowired
    private EmailHistoryRepository emailRepository;

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private CustomEmailRepository customEmailRepository;

    public void setValue(EmailHistoryDTO dto) {

        EmailHistoryEntity entity = new EmailHistoryEntity();
        entity.setTo_email(dto.getTo_email());
        entity.setTitle(dto.getTitle());
        entity.setMessage(dto.getMessage());
        emailRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreated_date(entity.getCreated_date());
        new ApiResponseDTO(true, "message seved");

    }


    public PageImpl<EmailHistoryDTO> emailPagination(int page, int size){
        Pageable pageable = PageRequest.of(page,size, Sort.Direction.DESC,"id");
        Page<EmailHistoryEntity> pageObj = emailRepository.findAll(pageable);
        List<EmailHistoryDTO> list = pageObj.stream().map(this::toDTO).collect(Collectors.toList());
        return new PageImpl<>(list,pageable,pageObj.getTotalElements());

    }



    public PageImpl<EmailHistoryDTO> emailPaginationByEmail(String email, int page, int size){
        Pageable pageable = PageRequest.of(page,size, Sort.Direction.DESC,"id");
        Page<EmailHistoryEntity> pageObj = emailRepository.pageByEmail(pageable,email);
        List<EmailHistoryDTO> list = pageObj.stream().map(this::toDTO).collect(Collectors.toList());
        return new PageImpl<>(list,pageable,pageObj.getTotalElements());

    }



    public PageImpl<EmailHistoryDTO> filter(EmailFilterDTO dto, int page, int size){
        FilterResultDTO<EmailHistoryEntity> result = customEmailRepository.filter(dto,page,size);
        List<EmailHistoryDTO> list = result.getContent().stream().map(this::toDTO).collect(Collectors.toList());

        return new PageImpl<>(list,PageRequest.of(page,size),result.getTotalCount());
    }




    public EmailHistoryDTO toDTO(EmailHistoryEntity entity) {
        EmailHistoryDTO dto = new EmailHistoryDTO();
        dto.setId(entity.getId());
        dto.setTo_email(entity.getTo_email());
        dto.setTitle(entity.getTitle());
        dto.setMessage(entity.getMessage());
        dto.setCreated_date(entity.getCreated_date());
        return dto;
    }


}
