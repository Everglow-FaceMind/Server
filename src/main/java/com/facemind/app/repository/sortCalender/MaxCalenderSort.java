package com.facemind.app.repository.sortCalender;

import com.facemind.app.web.dto.CalenderResponseDto;
import com.facemind.global.exception.ErrorCode;
import com.facemind.global.exception.RestApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class MaxCalenderSort implements CalenderSort{
    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<CalenderResponseDto> sortStressRate(Long memberId, LocalDate startDate, LocalDate endDate) {
        try {
            return em.createQuery(
                            "select new com.facemind.app.web.dto.CalenderResponseDto(function('DATE', r.dateTime), max(r.stressRate))" +
                                    " from Result r" +
                                    //" join fetch r.member m" +
                                    " where r.member.id = :memberId" +
                                    " and function('DATE', r.dateTime) between :startDate and :endDate" +
                                    " group by function('DATE', r.dateTime)" +
                                    " order by function('DATE', r.dateTime) asc", CalenderResponseDto.class)
                    .setParameter("memberId", memberId)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .getResultList();
        } catch (NoResultException e) {
            throw new RestApiException(ErrorCode.RESULT_NOT_FOUND);
        }
    }
}
