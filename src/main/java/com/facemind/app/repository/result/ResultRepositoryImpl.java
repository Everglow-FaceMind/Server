package com.facemind.app.repository.result;

import com.facemind.app.domain.Result;
import com.facemind.app.web.dto.CalenderResponseDto;
import com.facemind.app.web.dto.WeeklyHeartRateDto;
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

    @Override
    public List<WeeklyHeartRateDto> getWeeklyHeartRate(LocalDate startDate, LocalDate endDate, Long memberId) {
        try {
            return em.createQuery(
                            "select new com.facemind.app.web.dto.WeeklyHeartRateDto(function('DATE', r.dateTime), max(r.heartRateMax), min(r.heartRateMin))" +
                                    " from Result r" +
                                    //" join fetch r.member m" +
                                    " where r.member.id = :memberId" +
                                    " and function('DATE', r.dateTime) between :startDate and :endDate" +
                                    " group by function('DATE', r.dateTime)" +
                                    " order by function('DATE', r.dateTime) asc", WeeklyHeartRateDto.class)
                    .setParameter("memberId", memberId)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .getResultList();
        } catch (NoResultException e) {
            throw new RestApiException(ErrorCode.RESULT_NOT_FOUND);
        }
    }

    @Override
    public List<Result> getWeeklyStressRate(LocalDate startDate, LocalDate endDate, Long memberId) {
        try {
            return em.createQuery(
                    "select r from Result r"+
                            " join fetch r.member m"+
                            " where m.id = :memberId"+
                            " and function('DATE', r.dateTime) between :startDate and :endDate", Result.class)
                    .setParameter("memberId", memberId)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .getResultList();
        } catch (NoResultException e) {
            throw new RestApiException(ErrorCode.RESULT_NOT_FOUND);
        }
    }
}
