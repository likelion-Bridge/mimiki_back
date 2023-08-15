package com.likelion.mimiki.repository;

import com.likelion.mimiki.entity.CommentEntity;
import com.likelion.mimiki.entity.WikiPage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    // select * from comment_table where board_id=? order by id desc;
    List<CommentEntity> findAllByBoardEntityOrderByIdDesc(WikiPage boardEntity);
}

