package com.likelion.mimiki.entity;

import com.likelion.mimiki.dto.WikiPageDTO;
import com.likelion.mimiki.dto.WikiPageDTOJP;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
public class WikiPageJP extends BaseEntity {

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

    @OneToMany(mappedBy = "wikiPageJP", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntityJP> commentEntityListJP = new ArrayList<>();
}
