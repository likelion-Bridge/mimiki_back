package com.likelion.mimiki.entity;

import com.likelion.mimiki.dto.WikiPageDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
    private String explanation;

    //
    private int views; //조회수 필드 추가

}
