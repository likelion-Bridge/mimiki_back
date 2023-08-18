package com.likelion.mimiki.repository;


import com.likelion.mimiki.entity.WikiPageJP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WikiRepositoryJP extends JpaRepository<WikiPageJP, Long> {
    // 검색
    List<WikiPageJP> findByNameContaining(String name);
    // 년도별 검색
    List<WikiPageJP> findByYear(int year);
    // 위키 조회수 증가
    @Modifying
    @Query(value = "update WikiPageJP w set w.wikiPageHits=w.wikiPageHits+1 where w.id=:id")
    void updateHits(@Param("id") Long id);
}
