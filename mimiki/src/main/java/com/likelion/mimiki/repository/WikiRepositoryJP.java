package com.likelion.mimiki.repository;


import com.likelion.mimiki.entity.WikiPageJP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WikiRepositoryJP extends JpaRepository<WikiPageJP, Long> {

}
