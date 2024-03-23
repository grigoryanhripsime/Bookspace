package com.bookspace.web.repositories;

import com.bookspace.web.models.Saved;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavedRepository extends JpaRepository<Saved, Long> {
    List<Saved> findByUserId(Long userId);
    boolean existsByUserIdAndOpenLibId(Long userId, String openLibId);
    @Transactional
    void deleteByUserIdAndOpenLibId(Long userId, String openLibId);
}
