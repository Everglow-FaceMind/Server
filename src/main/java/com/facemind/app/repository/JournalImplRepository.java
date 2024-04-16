package com.facemind.app.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JournalImplRepository {

    @PersistenceContext
    private final EntityManager em;


    public void deleteJournal(Long journalId){
        em.createQuery("delete from Journal j where j.id = :journalId")
                .setParameter("journalId", journalId)
                .executeUpdate();
    }


}
