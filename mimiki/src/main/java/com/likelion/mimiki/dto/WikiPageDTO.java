package com.likelion.mimiki.dto;


import com.likelion.mimiki.entity.WikiPage;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class WikiPageDTO {
    private Long id;
    private String name;
    private String link;
    private int year;
    private String outline;
    private String explanation;

}
