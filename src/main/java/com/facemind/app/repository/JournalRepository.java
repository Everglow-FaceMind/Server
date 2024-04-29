package com.facemind.app.repository;

import com.facemind.app.domain.Journal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepository extends JpaRepository<Journal, Long>, JournalCustomRepository {
    void deleteById(Long id);
}
