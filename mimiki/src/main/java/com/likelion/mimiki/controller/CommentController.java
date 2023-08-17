package com.likelion.mimiki.controller;

import com.likelion.mimiki.dto.CommentDTO;
import com.likelion.mimiki.entity.CommentEntity;
import com.likelion.mimiki.exception.CommentNotFoundException;
import com.likelion.mimiki.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")

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

    //댓글 조회 기능
    @GetMapping
    public ResponseEntity<List<CommentEntity>> getAllComments() {
        List<CommentEntity> comments = commentService.getAllComments();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    //id 로 댓글 조회
    @GetMapping("/{id}")
    public ResponseEntity<CommentEntity> getCommentById(@PathVariable Long id) {
        CommentEntity comment = commentService.getCommentById(id);
        if (comment != null) {
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

}
