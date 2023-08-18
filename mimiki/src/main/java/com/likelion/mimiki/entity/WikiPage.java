package com.likelion.mimiki.entity;

import com.likelion.mimiki.dto.WikiPageDTO;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class WikiPage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String link;
    private int year;
    private String outline;
    @Lob
    private String explanation;
    private int wikiPageHits;


    @OneToMany(mappedBy = "wikiPage", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();
}
