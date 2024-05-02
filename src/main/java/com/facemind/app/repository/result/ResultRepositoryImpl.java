package com.facemind.app.repository.result;

import com.facemind.app.domain.Result;
import com.facemind.global.exception.ErrorCode;
import com.facemind.global.exception.RestApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ResultRepositoryImpl implements ResultCustomRepository{

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Result> findDailyResultAndJournal(LocalDate date, Long id) {
        try{
            return em.createQuery(
                    "select r from Result r"+
                            " join fetch r.member m"+
                            " left join fetch r.journal j"+
                            " where m.id = :memberId and" +
                            " function('DATE', r.dateTime) = :date"+
                            " order by r.dateTime desc", Result.class)
                    .setParameter("memberId", id)
                    .setParameter("date", date)
                    .getResultList();
        }catch (NoResultException e){
            throw new RestApiException(ErrorCode.RESULT_NOT_FOUND);
        }
    }
}
