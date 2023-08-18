package com.likelion.mimiki.dto;


import com.likelion.mimiki.entity.WikiPageJP;
import lombok.Data;

import java.security.PublicKey;

@Data
public class WikiPageDTOJP {

    private Long id;
    private String name;
    private String link;
    private int year;
    private String outline;
    private String explanation;
    private int wikiPageHits;

    public static WikiPageDTOJP toWikiPageDTO(WikiPageJP wikiPage) {
        WikiPageDTOJP wikiPageDTO = new WikiPageDTOJP();
        wikiPageDTO.setId(wikiPage.getId());
        wikiPageDTO.setWikiPageHits(wikiPage.getWikiPageHits());

        return wikiPageDTO;
    }

}
