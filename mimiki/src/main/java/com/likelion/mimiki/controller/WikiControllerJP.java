package com.likelion.mimiki.controller;


import com.likelion.mimiki.dto.WikiPageDTOJP;
import com.likelion.mimiki.service.WikiServiceJP;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wiki/jp")
@Api(value = "Wiki JP API", tags = "일본 밈 위키 페이지 관련 API")
public class WikiControllerJP {
    private final WikiServiceJP wikiServiceJP;

    @ApiOperation(value = "모든 위키 페이지 조회", tags = "위키 페이지")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 조회되었습니다.")})
    @GetMapping
    public ResponseEntity<List<WikiPageDTOJP>> getAllWikiPages() {
        List<WikiPageDTOJP> wikiPages = wikiServiceJP.getAllWikiPagesJP();
        return ResponseEntity.ok(wikiPages);
    }

    @ApiOperation(value = "ID로 위키 페이지 조회, 조회수 증가", tags = "위키 페이지")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 조회되었습니다.")})
    @ApiImplicitParam(name = "id", value = "조회할 위키 페이지 ID", paramType = "path", dataType = "Long", required = true)
    @GetMapping("/{id}")
    public ResponseEntity<WikiPageDTOJP> getWikiPageById(@PathVariable Long id) {
        WikiPageDTOJP wikiPage = wikiServiceJP.getWikiPageById(id);
        wikiServiceJP.updatateHits(id);
        return ResponseEntity.ok(wikiPage);
    }

    @ApiOperation(value = "위키 페이지 생성", tags = "위키 페이지")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "성공적으로 생성되었습니다.")})
    @ApiImplicitParam(name = "wikiPageDTOJP", value = "생성할 위키 페이지 정보", paramType = "body", dataType = "WikiPageDTO", required = true)
    @PostMapping
    public ResponseEntity<WikiPageDTOJP> createWikiPage(@RequestBody WikiPageDTOJP wikiPageDTO) {
        WikiPageDTOJP createdWikiPage = wikiServiceJP.createWikiPage(wikiPageDTO);
        return ResponseEntity.ok(createdWikiPage);
    }


    @ApiOperation(value = "위키 페이지 수정", tags = "위키 페이지")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 수정되었습니다.")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "수정할 위키 페이지 ID", paramType = "path", dataType = "Long", required = true),
            @ApiImplicitParam(name = "wikiPageDTOJP", value = "수정할 위키 페이지 정보", paramType = "body", dataType = "WikiPageDTOJP", required = true)
    })
    @PutMapping("/{id}")
    public ResponseEntity<WikiPageDTOJP> updateWikiPage(@PathVariable Long id, @RequestBody WikiPageDTOJP wikiPageDTO) {
        WikiPageDTOJP updatedWikiPage = wikiServiceJP.updateWikiPage(id, wikiPageDTO);
        return ResponseEntity.ok(updatedWikiPage);
    }


    @ApiOperation(value = "위키 페이지 삭제", tags = "위키 페이지")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 삭제되었습니다.")})
    @ApiImplicitParam(name = "id", value = "삭제할 위키 페이지 ID", paramType = "path", dataType = "Long", required = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWikiPage(@PathVariable Long id) {
        wikiServiceJP.deleteWikiPage(id);
        return ResponseEntity.ok().build();
    }

    // 검색
    @ApiOperation(value = "위키 페이지 검색", tags = "위키 페이지")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 검색되었습니다.")})
    @ApiImplicitParam(name = "name", value = "검색할 위키 페이지 이름", paramType = "path", dataType = "String", required = true)
    @GetMapping("/search/{name}")
    public ResponseEntity<List<WikiPageDTOJP>> searchWikiPage(@PathVariable String name) {
        List<WikiPageDTOJP> wikiPages = wikiServiceJP.searchWikiPage(name);
        return ResponseEntity.ok(wikiPages);
    }

    // 년도별 분류
    @ApiOperation(value = "년도별 위키 페이지 분류", tags = "위키 페이지")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 분류되었습니다.")})
    @ApiImplicitParam(name = "year", value = "분류할 위키 페이지 년도", paramType = "path", dataType = "int", required = true)
    @GetMapping("/year/{year}")
    public ResponseEntity<List<WikiPageDTOJP>> searchWikiPage(@PathVariable int year) {
        List<WikiPageDTOJP> wikiPages = wikiServiceJP.searchWikiPageByYear(year);
        return ResponseEntity.ok(wikiPages);
    }
}
