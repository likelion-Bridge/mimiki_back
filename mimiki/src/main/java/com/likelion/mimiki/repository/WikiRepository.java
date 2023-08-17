package com.likelion.mimiki.repository;

import com.likelion.mimiki.entity.WikiPage;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WikiRepository extends JpaRepository<WikiPage, Long> {
    // 추가적인 쿼리 메서드는 생략

    //
    @Modifying
    @Query(value = "update WikiPage w set w.wikiPageHits=w.wikiPageHits+1 where w.id=:id")
    void updateHits(@Param("id") Long id);

}
