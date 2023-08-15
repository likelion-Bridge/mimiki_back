package com.likelion.mimiki.repository;

import com.likelion.mimiki.entity.CommentEntity;
import com.likelion.mimiki.entity.WikiPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    // select * from comment_table where board_id=? order by id desc;
    List<CommentEntity> findAllByBoardEntityOrderByIdDesc(WikiPage boardEntity);

    //댓글 수정 기능
    @Modifying
    @Query("UPDATE CommentEntity c SET c.commentContents = :newContent WHERE c.id = :commentId")
    void updateCommentContent(@Param("commentId") Long commentId, @Param("newCommentContent") String newCommentContent);
}

