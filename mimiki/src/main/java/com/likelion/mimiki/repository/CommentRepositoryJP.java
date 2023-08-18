package com.likelion.mimiki.repository;

import com.likelion.mimiki.entity.CommentEntity;
import com.likelion.mimiki.entity.CommentEntityJP;
import com.likelion.mimiki.entity.WikiPage;
import com.likelion.mimiki.entity.WikiPageJP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepositoryJP extends JpaRepository<CommentEntityJP, Long> {
    // select * from comment_table where board_id=? order by id desc;
    List<CommentEntityJP> findAllByWikiPageJPOrderByIdDesc(WikiPageJP wikiPageJP);
}

