package com.likelion.mimiki.repository;

import com.likelion.mimiki.entity.WikiPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WikiRepository extends JpaRepository<WikiPage, Long> {
    // 추가적인 쿼리 메서드는 생략
}
