package com.facemind.app.repository.journal;

import com.facemind.app.domain.Journal;
import com.facemind.global.exception.ErrorCode;
import com.facemind.global.exception.RestApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JournalRepositoryImpl implements JournalCustomRepository{

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Journal findSingleJournal(Long journalId, Long memberId) {
        try {
            return em.createQuery(
                            "select j from Journal j" +
                                    " join fetch j.member m" +
                                    " join fetch j.result r" +
                                    " where j.id = :journalId and m.id = :memberId", Journal.class)
                    .setParameter("journalId", journalId)
                    .setParameter("memberId", memberId)
                    .getSingleResult();
        } catch (NoResultException e){
            throw new RestApiException(ErrorCode.JOURNAL_NOT_FOUND);
        }
    }

//    @Override
//    public List<Result> findDailyJournal(LocalDateTime date, Long id) {
//        try {
//            return em.createQuery(
//                            "select j from Journal j" +
//                                    " join fetch j.member m" +
//                                    " right join fetch j.result r" +
//                                    " where m.id = :memberId and r.dateTime = :date" +
//                                    " order by r.dateTime desc ", Journal.class)
//                    .setParameter("memberId", id)
//                    .setParameter("date", date)
//                    .getResultList();
//        } catch (NoResultException e){
//            throw new RestApiException(ErrorCode.JOURNAL_NOT_FOUND);
//        }
//    }

    @Override
    public void deleteJournal(Long journalId){
        em.createQuery("delete from Journal j where j.id = :journalId")
                .setParameter("journalId", journalId)
                .executeUpdate();
    }



}
