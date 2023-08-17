package com.likelion.mimiki.service;

import com.likelion.mimiki.dto.WikiPageDTO;
import com.likelion.mimiki.entity.WikiPage;
import com.likelion.mimiki.exception.WikiNotFoundException;
import com.likelion.mimiki.repository.WikiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WikiService {

    @Autowired
    private WikiRepository wikiRepository;

    private WikiPageDTO convertToDTO(WikiPage wikiPage) {
        WikiPageDTO wikiPageDTO = new WikiPageDTO();
        BeanUtils.copyProperties(wikiPage, wikiPageDTO);
        return wikiPageDTO;
    }

    private WikiPage convertToEntity(WikiPageDTO wikiPageDTO) {
        WikiPage wikiPage = new WikiPage();
        BeanUtils.copyProperties(wikiPageDTO, wikiPage, "id"); // id 필드를 무시합니다.
        return wikiPage;
    }

    public List<WikiPageDTO> getAllWikiPages() {
        return wikiRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public WikiPageDTO getWikiPageById(Long id) {
        WikiPage wikiPage = wikiRepository.findById(id)
                .orElseThrow(() -> new WikiNotFoundException("Wiki 페이지를 찾을 수 없습니다. id : " + id));

        return convertToDTO(wikiPage);
    }

    public WikiPageDTO createWikiPage(WikiPageDTO wikiPageDTO) {

        if (wikiPageDTO.getName() == null || wikiPageDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Wiki 페이지의 제목은 필수입니다.");
        }
        WikiPage wikiPage = convertToEntity(wikiPageDTO);
        WikiPage newWikiPage = wikiRepository.save(wikiPage);
        return convertToDTO(newWikiPage);
    }

    public WikiPageDTO updateWikiPage(Long id, WikiPageDTO wikiPageDTO) {
        WikiPage existingWikiPage = wikiRepository.findById(id)
                .orElseThrow(() -> new WikiNotFoundException("Wiki 페이지를 찾을 수 없습니다. id : " + id));
        WikiPage updatedWikiPage = convertToEntity(wikiPageDTO);
        BeanUtils.copyProperties(updatedWikiPage, existingWikiPage, "id"); // id 필드를 무시합니다.
        WikiPage savedWikiPage = wikiRepository.save(existingWikiPage);
        return convertToDTO(savedWikiPage);
    }


    public void deleteWikiPage(Long id) {
        if (!wikiRepository.existsById(id)) {
            throw new WikiNotFoundException("Wiki 페이지를 찾을 수 없습니다. id : " + id);
        }
        wikiRepository.deleteById(id);
    }

    // 검색
    public List<WikiPageDTO> searchWikiPages(String name) {
        List<WikiPage> wikiPages = wikiRepository.findByNameContaining(name);
        List<WikiPageDTO> wikiPageDTOS = new ArrayList<>();

        for (int i=0; i<wikiPages.size(); i++) {
            WikiPage wikiPage = wikiPages.get(i);
            WikiPageDTO wikiPageDTO = convertToDTO(wikiPage);
            wikiPageDTOS.add(wikiPageDTO);
        }
        return wikiPageDTOS;
    }

    // 년도별 조회
    public List<WikiPageDTO> getWikiPageByYear(int year) {
        List<WikiPage> wikiPages = wikiRepository.findByYear(year);
        return wikiPages.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
