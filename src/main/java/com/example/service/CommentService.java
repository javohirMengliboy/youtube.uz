package com.example.service;

import com.example.dto.ApiResponseDTO;
import com.example.dto.CommentDTO;
import com.example.entity.CommentEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileRole;
import com.example.exp.ItemNotFoundException;
import com.example.repository.CommentRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VideoService videoService;

    // 1. Crate Comment
    public CommentDTO create(CommentDTO dto) {
        CommentEntity entity = new CommentEntity();
        entity.setVideoId(dto.getVideoId());
        entity.setProfileId(SpringSecurityUtil.getCurrentUserId());
        entity.setContent(dto.getContent());
        commentRepository.save(entity);
        dto.setId(entity.getReplyId());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setProfileId(entity.getProfileId());
        return dto;
    }

    // 2. Update Comment
    public ApiResponseDTO update(String id, CommentDTO dto) {
        ProfileEntity currentUser = SpringSecurityUtil.getCurrentUser().getProfile();
        CommentEntity entity = get(id);
        String ownerId = videoService.getOwnerId(entity.getVideoId());
        if (!entity.getProfileId().equals(currentUser.getId()) || currentUser.getId().equals(ownerId)){
            return new ApiResponseDTO(false,"Comment not changed. Comment not yours");
        }
        entity.setContent(dto.getContent());
        commentRepository.save(entity);
        return new ApiResponseDTO(true, "Comment not delete. Comment changed");
    }

    // 3. Delete Comment
    public ApiResponseDTO delete(String id) {
        ProfileEntity currentUser = SpringSecurityUtil.getCurrentUser().getProfile();
        CommentEntity entity = get(id);
        String ownerId = videoService.getOwnerId(entity.getVideoId());
        if (entity.getProfileId().equals(currentUser.getId())
                || currentUser.getId().equals(ownerId)
                || currentUser.getRole().equals(ProfileRole.ROLE_ADMIN)){
            commentRepository.delete(entity);
            return new ApiResponseDTO(true, "Comment changed");
        }
        return new ApiResponseDTO(false,"Comment not yours");
    }

    // 4. Comment List Pagination
    public Page<CommentDTO> getAllPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CommentDTO> dtoPage = commentRepository.getAllPagination(pageable);
        if (dtoPage.isEmpty()){
            throw new ItemNotFoundException("Comment page not found");
        }
        return dtoPage;
    }

    // 5. Comment List By profileId
    public List<CommentDTO> getListByProfileId(String profileId) {
        List<CommentDTO> commentList = commentRepository.getListByProfileId(profileId);
        if (commentList.isEmpty()){
            throw new ItemNotFoundException("Comment list not found");
        }
        return commentList;
    }

    // 6. Comment List By Profile
    public List<CommentDTO> getListByProfile() {
        List<CommentDTO> commentList = commentRepository.getListByProfileId(SpringSecurityUtil.getCurrentUserId());
        if (commentList.isEmpty()){
            throw new ItemNotFoundException("Comment list not found");
        }
        return commentList;
    }

    // 7. Comment List by videoId
    public List<CommentDTO> getListByVideoId(String videoId) {
        List<CommentDTO> commentList = commentRepository.getListByVideoId(videoId);
        if (commentList.isEmpty()){
            throw new ItemNotFoundException("Comment list not found");
        }
        return commentList;
    }

    // 8. Get Comment Replied Comment by comment Id
    public List<CommentDTO> getRepliedCommentListByCommentId(String id) {
        List<CommentDTO> commentList = commentRepository.getRepliedCommentListByCommentId(id);
        if (commentList.isEmpty()){
            throw new ItemNotFoundException("Comment list not found");
        }
        return commentList;
    }

    //------------------------------------------------------------
    public CommentEntity get(String id){
        return commentRepository.findById(id).orElseThrow(()-> new ItemNotFoundException("Comment not found"));
    }



}
