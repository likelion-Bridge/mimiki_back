package com.likelion.mimiki.controller;

import com.likelion.mimiki.dto.CommentDTO;
import com.likelion.mimiki.entity.CommentEntity;
import com.likelion.mimiki.exception.CommentNotFoundException;
import com.likelion.mimiki.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/wiki/comment")
@Api(tags = "댓글 API", description = "댓글 관련 API")
public class CommentController {

    private final CommentService commentService;

    @ApiOperation(value = "댓글 저장")
    @ApiResponses({
            @ApiResponse(code = 200, message = "댓글 저장 성공"),
            @ApiResponse(code = 404, message = "해당 게시글이 존재하지 않습니다.")
    })
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
    @ApiOperation(value = "모든 댓글 조회")
    @ApiResponse(code = 200, message = "댓글 목록 조회 성공")
    @GetMapping("/")
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        List<CommentDTO> comment = commentService.getAllComments();
        return ResponseEntity.ok(comment);
    }

    //id 로 댓글 조회
    @ApiOperation(value = "댓글 ID로 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "댓글 조회 성공"),
            @ApiResponse(code = 404, message = "댓글을 찾을 수 없습니다.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) {
        CommentDTO comment = commentService.getCommentById(id);
        return ResponseEntity.ok(comment);
    }

    //댓글 삭제 기능
    @ApiOperation(value = "댓글 삭제")
    @ApiResponses({
            @ApiResponse(code = 200, message = "댓글 삭제 성공"),
            @ApiResponse(code = 404, message = "댓글을 찾을 수 없습니다.")
    })
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
