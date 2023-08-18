package com.likelion.mimiki.dto;

import com.likelion.mimiki.entity.CommentEntity;
import com.likelion.mimiki.entity.CommentEntityJP;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CommentDTOJP {
    private Long id;
    private String commentWriter;
    private String commentContents;
    private Long boardId;
    private LocalDateTime commentCreatedTime;

    public static CommentDTOJP toCommentDTO(CommentEntityJP commentEntityJP, Long boardId) {
        CommentDTOJP commentDTO = new CommentDTOJP();
        commentDTO.setId(commentEntityJP.getId());
        commentDTO.setCommentWriter(commentEntityJP.getCommentWriter());
        commentDTO.setCommentContents(commentEntityJP.getCommentContents());
        commentDTO.setCommentCreatedTime(commentEntityJP.getCreatedTime());
        commentDTO.setBoardId(boardId);
        return commentDTO;
    }
}
