package com.example.repository;

import com.example.dto.EmailFilterDTO;
import com.example.dto.FilterResultDTO;
import com.example.entity.EmailHistoryEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomEmailRepository {

    @Autowired
    private EntityManager entityManager;

    public FilterResultDTO<EmailHistoryEntity> filter(EmailFilterDTO filterDTO, int page, int size){

        StringBuilder stringBuilder = new StringBuilder();

        Map<String, Object> params = new HashMap<>();
        if (filterDTO.getEmail() != null){
            stringBuilder.append(" and e.to_email =:email");
            params.put("email",filterDTO.getEmail());
        }
        if (filterDTO.getCreated_date() != null){
            stringBuilder.append(" and e.created_date =:created_date");
            params.put("created_date",filterDTO.getCreated_date());
        }

        StringBuilder selectBuilder = new StringBuilder("select e from EmailEntity as e where 1=1 ");
        selectBuilder.append(stringBuilder);

        StringBuilder countBuilder = new StringBuilder("select count(e) from EmailEntity as e where 1=1 ");
        countBuilder.append(stringBuilder);

        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        selectQuery.setMaxResults(size);
        selectQuery.setFirstResult(size * page);


        Query countQuery = entityManager.createQuery(countBuilder.toString());
        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
            countQuery.setParameter(param.getKey(), param.getValue());
        }

        List<EmailHistoryEntity> select = selectQuery.getResultList();
        Long count = (Long) countQuery.getSingleResult();

        return new FilterResultDTO<>(select,count);
    }

}
