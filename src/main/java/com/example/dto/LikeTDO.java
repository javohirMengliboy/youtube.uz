package com.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeTDO {
    private Integer likeCount;
    private Integer dislikeCount;
    private Boolean isUserLike;
    private Boolean isUserDislike;

    public LikeTDO() {
    }

    public LikeTDO(Integer likeCount, Integer dislikeCount, Boolean isUserLike, Boolean isUserDislike) {
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.isUserLike = isUserLike;
        this.isUserDislike = isUserDislike;
    }
}
