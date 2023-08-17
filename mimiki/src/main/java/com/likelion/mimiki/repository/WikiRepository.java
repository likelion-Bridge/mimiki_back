package com.likelion.mimiki.repository;

import com.likelion.mimiki.entity.WikiPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WikiRepository extends JpaRepository<WikiPage, Long> {
    // 검색 쿼리
    List<WikiPage> findByNameContaining(String name);
}
