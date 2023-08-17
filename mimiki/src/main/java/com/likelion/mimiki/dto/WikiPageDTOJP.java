package com.likelion.mimiki.dto;


import lombok.Data;

@Data
public class WikiPageDTOJP {

    private Long id;
    private String name;
    private String link;
    private int year;
    private String outline;
    private String explanation;
}
