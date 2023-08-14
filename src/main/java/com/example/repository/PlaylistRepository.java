package com.example.repository;

import com.example.dto.PlaylistDTO;
import com.example.entity.PlaylistEntity;
import com.example.mapper.PlayListShortInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlaylistRepository extends CrudRepository<PlaylistEntity, Integer> {
    @Query("select new com.example.dto.PlaylistDTO(id, channelId, name, description, status, orderNumber) from PlaylistEntity order by id")
    Page<PlaylistDTO> getPlaylistPage(Pageable pageable);

    @Query("select new com.example.dto.PlaylistDTO(p.id, p.channelId, p.name, p.description, p.status, p.orderNumber) from PlaylistEntity p" +
            " inner join p.channel ch where ch.profileId = :userId order by p.orderNumber")
    List<PlaylistDTO> getListByUserId(String userId);

    @Query("select new com.example.mapper.PlayListShortInfo(ch.name, p.name, p.status) from PlaylistEntity p" +
            " inner join p.channel ch where ch.profileId = :currentUserId order by p.orderNumber")
    List<PlayListShortInfo> getUserPlaylist(String currentUserId);
}
