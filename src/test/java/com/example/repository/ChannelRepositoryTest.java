package com.example.repository;

import com.example.entity.ChannelEntity;
import com.example.enums.ProfileStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest(properties = {"spring.jpa.properties.javax.persistence.validation.mode=none"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ChannelRepositoryTest {


    @Autowired
    private ChannelRepository channelRepository;

    @Test
    public void itShouldCreateArticle() {
        // given
        ChannelEntity entity = new ChannelEntity();
        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setName(null);
        // when
        channelRepository.save(entity);
        // then
        System.out.println("aaaa");
//        Optional<CategoryEntity> optional = categoryRepository.findById(categoryEntity.getId());
//        Assertions.assertThat(optional.isPresent()).isTrue();
    }

}
