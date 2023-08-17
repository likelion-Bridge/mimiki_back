package com.likelion.mimiki.controller;

import com.likelion.mimiki.dto.WikiPageDTO;
import com.likelion.mimiki.service.WikiService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
        WikiPageDTO wikiPageDTO = wikiService.getWikiPageById(id);
        return ResponseEntity.ok(wikiPageDTO);
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

    //
    @ApiOperation(value ="위키 페이지 조회수", tags = "위키 페이지")
    @GetMapping("/{id}/")
    public int findById(@PathVariable Long id, Model model) {
        wikiService.updateHits(id);
        WikiPageDTO wikiPageDTO = wikiService.findById(id);
        model.addAttribute("wikiPage", wikiPageDTO);
        return wikiPageDTO.getWikiPageHits();
    }
}
