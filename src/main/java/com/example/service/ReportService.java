package com.example.service;

import com.example.dto.ApiResponseDTO;
import com.example.dto.PhotoDTO;
import com.example.dto.ReportDTO;
import com.example.entity.ReportEntity;
import com.example.enums.ReportType;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.mapper.ProfileForReport;
import com.example.mapper.ReportInfoMapper;
import com.example.mapper.ReportInfoI;
import com.example.repository.ReportRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private AttachService attachService;

    // 1. Create report
    public ReportDTO create(ReportDTO dto) {
        ReportEntity entity = new ReportEntity();
        if (dto.getChannelId() != null){
            entity.setChannelId(dto.getChannelId());
            entity.setType(ReportType.CHANNEL);
        }else if (dto.getVideoId() != null){
            entity.setVideoId(dto.getVideoId());
            entity.setType(ReportType.VIDEO);
        }else {
            throw new AppBadRequestException("Report not send");
        }
        entity.setProfileId(SpringSecurityUtil.getCurrentUserId());
        entity.setContent(dto.getContent());
        reportRepository.save(entity);
        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    // 2. ReportList Pagination
    public PageImpl<ReportInfoMapper> getPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ReportInfoI> reportPage = reportRepository.getPagination(pageable);
        if (reportPage.isEmpty()){
            throw new ItemNotFoundException("Report page not found");
        }
        List<ReportInfoMapper> mapperList = toMapper(reportPage);
        return new PageImpl<>(mapperList, pageable, reportPage.getTotalElements());
    }

    // 3. Remove Report by id
    public ApiResponseDTO remove(String id) {
        reportRepository.deleteById(id);
        return new ApiResponseDTO(true, "Report removed");
    }

    public List<ReportInfoMapper> getReportListByUserId(String userId) {
        List<ReportInfoI> reportIList = reportRepository.getReportListByUserId(userId);
        if (reportIList.isEmpty()){
            throw new ItemNotFoundException("Report list not found");
        }
        return toMapper(reportIList);
    }


    public List<ReportInfoMapper> toMapper(List<ReportInfoI> list){
        return convertToMapper(list);
    }
    public List<ReportInfoMapper> toMapper(Page<ReportInfoI> list){
        return convertToMapper(list);
    }
    public List<ReportInfoMapper> convertToMapper(Iterable<ReportInfoI> page){
        List<ReportInfoMapper> mapperList = new ArrayList<>();
        page.forEach(entity -> {
            PhotoDTO photo = new PhotoDTO();
            photo.setId(entity.getPhotoId());
            photo.setUrl(attachService.getUrl(entity.getPhotoId()));

            ProfileForReport profile = new ProfileForReport();
            profile.setId(entity.getProfileId());
            profile.setName(entity.getName());
            profile.setSurname(entity.getSurname());
            profile.setPhoto(photo);

            ReportInfoMapper info = new ReportInfoMapper();
            info.setId(entity.getId());
            info.setProfile(profile);
            info.setContent(entity.getContent());
            if (entity.getChannelId() != null){
                info.setChannelId(entity.getChannelId());
            }else {
                info.setVideoId(entity.getVideoId());
            }
            info.setType(entity.getType());
            mapperList.add(info);
        });
        return mapperList;
    }




}
