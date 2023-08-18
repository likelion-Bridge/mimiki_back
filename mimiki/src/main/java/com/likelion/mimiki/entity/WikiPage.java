package com.likelion.mimiki.entity;

import com.likelion.mimiki.dto.WikiPageDTO;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
@Data
public class WikiPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String link;
    private int year;
    private String outline;
    private String explanation;
    private int wikiPageHits;

    public static WikiPageDTO toWikiPageDTO(WikiPage wikiPage) {
        WikiPageDTO wikiPageDTO = new WikiPageDTO();
        wikiPageDTO.setId(wikiPage.getId());
        wikiPageDTO.setWikiPageHits(wikiPage.getWikiPageHits());
        //  wikiPageDTO.setBoardCreatedTime(wikiPage.getCreatedTime());
        // wikiPageDTO.setBoardUpdatedTime(wikiPage.getUpdatedTime());

        return wikiPageDTO;
    }
}
