package com.likelion.mimiki.controller;

import com.likelion.mimiki.dto.CommentDTO;
import com.likelion.mimiki.exception.CommentNotFoundException;
import com.likelion.mimiki.service.CommentService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wiki")
// @Api(value = "Wiki API", description = "위키 페이지 관련 API")
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/save")
    public ResponseEntity save(@ModelAttribute CommentDTO commentDTO) {
        System.out.println("commentDTO = " + commentDTO);
        Long saveResult = commentService.save(commentDTO);
        if (saveResult != null) {
            List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getBoardId());
            return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }

    //댓글 삭제 기능
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        try {
            commentService.deleteComment(id);
            return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
        } catch (CommentNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //댓글 수정 기능
    @PutMapping("/{id}")
    public ResponseEntity<String> updateComment(@PathVariable Long id, @RequestBody Map<String, String> requestMap) {
        String newCommentContent = requestMap.get("commentContents");

        if (newCommentContent == null || newCommentContent.isEmpty()) {
            return new ResponseEntity<>("New content cannot be empty", HttpStatus.BAD_REQUEST);
        }

        try {
            commentService.updateCommentContent(id, newCommentContent);
            return new ResponseEntity<>("Comment updated successfully", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Comment not found", HttpStatus.NOT_FOUND);
        }
    }
}
