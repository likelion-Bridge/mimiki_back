package com.likelion.mimiki.service;

import com.likelion.mimiki.dto.CommentDTO;
import com.likelion.mimiki.entity.CommentEntity;
import com.likelion.mimiki.entity.WikiPage;
import com.likelion.mimiki.exception.CommentNotFoundException;
import com.likelion.mimiki.repository.CommentRepository;
import com.likelion.mimiki.repository.WikiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    //@Autowired
    private final CommentRepository commentRepository;
    private final WikiRepository wikiRepository;

    public Long save(CommentDTO commentDTO) {
        /* 부모엔티티(WikiPage) 조회 */
        Optional<WikiPage> optionalWikiPage = wikiRepository.findById(commentDTO.getBoardId());
        if (optionalWikiPage.isPresent()) {
            WikiPage wikiPage = optionalWikiPage.get();
            CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO, wikiPage);
            return commentRepository.save(commentEntity).getId();
        } else {
            return null;
        }
    }

    public List<CommentDTO> findAll(Long boardId) {
        WikiPage wikiPage = wikiRepository.findById(boardId).get();
        List<CommentEntity> commentEntityList = commentRepository.findAllByWikiPageOrderByIdDesc(wikiPage);
        /* EntityList -> DTOList */
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (CommentEntity commentEntity: commentEntityList) {
            CommentDTO commentDTO = CommentDTO.toCommentDTO(commentEntity, boardId);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }

    //댓글 삭제 기능
    public void deleteComment(Long commentId) {
        Optional<CommentEntity> commentOptional = commentRepository.findById(commentId);

        if (commentOptional.isPresent()) {
            commentRepository.deleteById(commentId);
        } else {
            // 해당 ID에 대한 댓글이 없을 경우 예외 처리
            throw new CommentNotFoundException("Comment with id " + commentId + " not found.");
        }
    }

    //댓글 수정 기능
//    public void updateCommentContent(Long commentId, String newCommentContent) {
//        commentRepository.updateCommentContent(commentId, newCommentContent);
//    }

}
