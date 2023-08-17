package com.likelion.mimiki.controller;

import com.likelion.mimiki.dto.WikiPageDTO;
import com.likelion.mimiki.service.WikiService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wiki")
@Api(value = "Wiki API", description = "위키 페이지 관련 API")
public class WikiController {
    private final WikiService wikiService;

    @ApiOperation(value = "모든 위키 페이지 조회", tags = "위키 페이지")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 조회되었습니다.")})
    @GetMapping
    public ResponseEntity<List<WikiPageDTO>> getAllWikiPages() {
        List<WikiPageDTO> wikiPages = wikiService.getAllWikiPages();
        return ResponseEntity.ok(wikiPages);
    }

    @ApiOperation(value = "ID로 위키 페이지 조회", tags = "위키 페이지")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 조회되었습니다.")})
    @ApiImplicitParam(name = "id", value = "조회할 위키 페이지 ID", paramType = "path", dataType = "Long", required = true)
    @GetMapping("/{id}")
    public ResponseEntity<WikiPageDTO> getWikiPageById(@PathVariable Long id) {
        WikiPageDTO wikiPage = wikiService.getWikiPageById(id);
        return ResponseEntity.ok(wikiPage);
    }

    @ApiOperation(value = "위키 페이지 생성", tags = "위키 페이지")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "성공적으로 생성되었습니다.")})
    @ApiImplicitParam(name = "wikiPageDTO", value = "생성할 위키 페이지 정보", paramType = "body", dataType = "WikiPageDTO", required = true)
    @PostMapping
    public ResponseEntity<WikiPageDTO> createWikiPage(@RequestBody WikiPageDTO wikiPageDTO) {
        WikiPageDTO createdWikiPage = wikiService.createWikiPage(wikiPageDTO);
        return new ResponseEntity<>(createdWikiPage, HttpStatus.CREATED);
    }

    @ApiOperation(value = "위키 페이지 수정", tags = "위키 페이지")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 수정되었습니다.")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "수정할 위키 페이지 ID", paramType = "path", dataType = "Long", required = true),
            @ApiImplicitParam(name = "wikiPageDTO", value = "수정할 위키 페이지 정보", paramType = "body", dataType = "WikiPageDTO", required = true)})
    @PutMapping("/{id}")
    public ResponseEntity<WikiPageDTO> updateWikiPage(@PathVariable Long id, @RequestBody WikiPageDTO wikiPageDTO) {
        WikiPageDTO updatedWikiPage = wikiService.updateWikiPage(id, wikiPageDTO);
        return ResponseEntity.ok(updatedWikiPage);
    }

    @ApiOperation(value = "위키 페이지 삭제", tags = "위키 페이지")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "성공적으로 삭제되었습니다.")})
    @ApiImplicitParam(name = "id", value = "삭제할 위키 페이지 ID", paramType = "path", dataType = "Long", required = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWikiPage(@PathVariable Long id) {
        wikiService.deleteWikiPage(id);
        return ResponseEntity.noContent().build();
    }

    // 검색
    @ApiOperation(value = "위키페이지 검색", tags = "위키 페이지")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 조회됐습니다.")})
    @ApiImplicitParam(name = "name", value = "검색할 내용", paramType = "path", dataType = "String", readOnly = true)
    @GetMapping("/search/{name}")
    public ResponseEntity<List<WikiPageDTO>> searchWikiPages(@PathVariable String name) {
        List<WikiPageDTO> wikiPages = wikiService.searchWikiPages(name);
        return ResponseEntity.ok(wikiPages);
    }

    // 년도별 분류
    @ApiOperation(value = "년도별 위키 페이지 조회", tags = "위키 페이지")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공적으로 조회되었습니다.")})
    @ApiImplicitParam(name = "year", value = "조회할 위키 페이지 년도", paramType = "path", dataType = "int", required = true)
    @GetMapping("/year/{year}")
    public ResponseEntity<List<WikiPageDTO>> getWikiPagesByYear(@PathVariable int year) {
        List<WikiPageDTO> wikiPages = wikiService.getWikiPageByYear(year);
        return ResponseEntity.ok(wikiPages);
    }
}
