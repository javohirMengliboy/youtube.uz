package com.example.repository;

import com.example.dto.PlaylistDTO;
import com.example.entity.PlaylistEntity;
import com.example.entity.ProfileEntity;
import com.example.mapper.PlayListShortInfo;
import com.example.mapper.PlaylistFullInfoMapperI;
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

    @Query("select ch.profile from PlaylistEntity p inner join p.channel ch where p.id = :playlistId")
    ProfileEntity getOwner(Integer playlistId);

    @Query("select new com.example.mapper.PlayListShortInfo(ch.name, p.name, p.status) from PlaylistEntity p" +
            " inner join p.channel ch where ch.id = :channelId and p.status = PUBLIC order by p.orderNumber")
    List<PlayListShortInfo> getChannelPlaylist(String channelId);

    @Query(value = "select id, name," +
            " (select count(*) from playlist_and_video where playlist_id = :playlistId) as videoCount," +
            " (select sum(v.view_count) from playlist_and_video pv inner join video v on pv.video_id = v.id" +
            " where pv.playlist_id = :playlistId) as totalViewCount, " +
            " (select v.published_date from playlist_and_video pv inner join video v on pv.video_id = v.id" +
            " where pv.playlist_id = :playlistId order by v.published_date desc  limit 1) as lastUpdateDate from playlist where id = :playlistId", nativeQuery = true)
    PlaylistFullInfoMapperI getPlaylistById(Integer playlistId);
}
