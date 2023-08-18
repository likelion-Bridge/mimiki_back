package com.likelion.mimiki.entity;

import com.likelion.mimiki.dto.CommentDTOJP;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "comment_tableJP")
public class CommentEntityJP extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String commentWriter;

    @Column
    private String commentContents;

    /* Board:Comment = 1:N */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wikiPageJP_id")
    private WikiPageJP wikiPageJP;


    public static CommentEntityJP toSaveEntity(CommentDTOJP commentDTOJP, WikiPageJP wikiPageJP) {
        CommentEntityJP commentEntity = new CommentEntityJP();
        commentEntity.setCommentWriter(commentDTOJP.getCommentWriter());
        commentEntity.setCommentContents(commentDTOJP.getCommentContents());
        commentEntity.setWikiPageJP(wikiPageJP);
        return commentEntity;
    }
}
