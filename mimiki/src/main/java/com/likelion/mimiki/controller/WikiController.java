package com.likelion.mimiki.controller;

import com.likelion.mimiki.dto.WikiPageDTO;
import com.likelion.mimiki.service.WikiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wiki")
public class WikiController {
    private final WikiService wikiService;

    @GetMapping
    public ResponseEntity<List<WikiPageDTO>> getAllWkikiPages() {
        List<WikiPageDTO> wikiPages = wikiService.getAllWikiPages();
        return ResponseEntity.ok(wikiPages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WikiPageDTO> getWikiPageById(@PathVariable Long id) {
        WikiPageDTO wikiPage = wikiService.getWikiPageById(id);
        return ResponseEntity.ok(wikiPage);
    }

    @PostMapping
    public ResponseEntity<WikiPageDTO> createWikiPage(@RequestBody WikiPageDTO wikiPageDTO) {
        WikiPageDTO createdWikiPage = wikiService.createWikiPage(wikiPageDTO);
        return new ResponseEntity<>(createdWikiPage, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WikiPageDTO> updateWikiPage(@PathVariable Long id, @RequestBody WikiPageDTO wikiPageDTO) {
        WikiPageDTO updatedWikiPage = wikiService.updateWikiPage(id, wikiPageDTO);
        return ResponseEntity.ok(updatedWikiPage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWikiPage(@PathVariable Long id) {
        wikiService.deleteWikiPage(id);
        return ResponseEntity.noContent().build();
    }
}
