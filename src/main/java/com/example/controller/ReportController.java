package com.example.controller;

import com.example.dto.ApiResponseDTO;
import com.example.dto.ReportDTO;
import com.example.mapper.ReportInfoMapper;
import com.example.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    // 1. Create report
    @PostMapping("/create")
    public ResponseEntity<ReportDTO> create(@RequestBody ReportDTO dto){
        return ResponseEntity.ok().body(reportService.create(dto));
    }

    // 2. ReportList Pagination
    @GetMapping("/get_pagination")
    public ResponseEntity<PageImpl<ReportInfoMapper>> getPagination(@RequestParam("page") int page,
                                                                    @RequestParam("size") int size){
        return ResponseEntity.ok().body(reportService.getPagination(page, size));
    }

    // 3. Remove Report by id
    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponseDTO> remove(@PathVariable("id") String id){
        return ResponseEntity.ok().body(reportService.remove(id));
    }

    // 4. Report List By User id
    @GetMapping("/get_report_list_by_user_id")
    public ResponseEntity<List<ReportInfoMapper>> getReportListByUserId(@RequestParam("userId") String userId){
        return ResponseEntity.ok().body(reportService.getReportListByUserId(userId));
    }
}
