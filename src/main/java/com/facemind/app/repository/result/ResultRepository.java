package com.facemind.app.repository.result;

import com.facemind.app.domain.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long>, ResultCustomRepository {
    Optional<Result> findById(Long id);
}
