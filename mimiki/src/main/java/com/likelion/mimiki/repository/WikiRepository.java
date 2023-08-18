package com.likelion.mimiki.repository;

import com.likelion.mimiki.entity.WikiPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WikiRepository extends JpaRepository<WikiPage, Long> {
    // 검색
    List<WikiPage> findByNameContaining(String name);
    // 년도별 분류
    List<WikiPage> findByYear(int year);
    // 위키 조회수 증가
    @Modifying
    @Query(value = "update WikiPage w set w.wikiPageHits=w.wikiPageHits+1 where w.id=:id")
    void updateHits(@Param("id") Long id);
}
