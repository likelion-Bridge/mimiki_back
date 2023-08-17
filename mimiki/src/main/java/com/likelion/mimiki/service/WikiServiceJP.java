package com.likelion.mimiki.service;


import com.likelion.mimiki.dto.WikiPageDTOJP;
import com.likelion.mimiki.entity.WikiPageJP;
import com.likelion.mimiki.exception.WikiNotFoundException;
import com.likelion.mimiki.repository.WikiRepositoryJP;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WikiServiceJP {

    @Autowired
    private WikiRepositoryJP wikiRepositoryJP;


    // DTO로 데이터 전송
    private WikiPageDTOJP convertToDTO(WikiPageJP wikiPageJP) {
        WikiPageDTOJP wikiPageDTOJP = new WikiPageDTOJP();
        BeanUtils.copyProperties(wikiPageJP, wikiPageDTOJP);
        return wikiPageDTOJP;
    }

    private WikiPageJP convertToEntity(WikiPageDTOJP wikiPageDTOJP) {
        WikiPageJP wikiPageJP = new WikiPageJP();
        BeanUtils.copyProperties(wikiPageDTOJP, wikiPageJP, "id");
        return wikiPageJP;
    }


    public List<WikiPageDTOJP> getAllWikiPagesJP() {
        return wikiRepositoryJP.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public WikiPageDTOJP getWikiPageById(Long id) {
        WikiPageJP wikiPageJP = wikiRepositoryJP.findById(id)
                .orElseThrow(() -> new WikiNotFoundException("Wiki 페이지를 찾을 수 없습니다. id : " + id));
        return convertToDTO(wikiPageJP);
    }

    public WikiPageDTOJP createWikiPage(WikiPageDTOJP wikiPageDTOJP) {

        if (wikiPageDTOJP.getName() == null || wikiPageDTOJP.getName().isEmpty()) {
            throw new IllegalArgumentException("Wiki 페이지의 제목은 필수입니다.");
        }
        WikiPageJP wikiPageJP = convertToEntity(wikiPageDTOJP);
        WikiPageJP newWikiPage = wikiRepositoryJP.save(wikiPageJP);
        return convertToDTO(newWikiPage);
    }


    public WikiPageDTOJP updateWikiPage(Long id, WikiPageDTOJP wikiPageDTOJP) {
        WikiPageJP existingWikiPage = wikiRepositoryJP.findById(id)
                .orElseThrow(() -> new WikiNotFoundException("Wiki 페이지를 찾을 수 없습니다. : " + id));
        WikiPageJP updatedWikiPage = convertToEntity(wikiPageDTOJP);
        BeanUtils.copyProperties(updatedWikiPage, existingWikiPage, "id");
        WikiPageJP saveWikiPage =  wikiRepositoryJP.save(existingWikiPage);
        return convertToDTO(saveWikiPage);
    }

    public void deleteWikiPage(Long id) {
        if (!wikiRepositoryJP.existsById(id)) {
            throw new WikiNotFoundException("Wiki 페이지를 찾을 수 없습니다. id : " + id);
        }
        wikiRepositoryJP.deleteById(id);
    }

    // 검색
    public List<WikiPageDTOJP> searchWikiPage(String keyword) {
        return wikiRepositoryJP.findByNameContaining(keyword).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 년도별 조회
    public List<WikiPageDTOJP> searchWikiPageByYear(int year) {
        List<WikiPageJP> wikiPages = wikiRepositoryJP.findByYear(year);
        return wikiPages.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
