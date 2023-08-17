package com.likelion.mimiki.repository;


import com.likelion.mimiki.entity.WikiPageJP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WikiRepositoryJP extends JpaRepository<WikiPageJP, Long> {
    // 검색
    List<WikiPageJP> findByNameContaining(String name);
    // 년도별 검색
    List<WikiPageJP> findByYear(int year);
}
