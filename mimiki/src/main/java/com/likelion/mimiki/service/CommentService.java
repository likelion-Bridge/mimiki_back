package com.likelion.mimiki.service;

import com.likelion.mimiki.dto.CommentDTO;
import com.likelion.mimiki.entity.CommentEntity;
import com.likelion.mimiki.entity.WikiPage;
import com.likelion.mimiki.entity.WikiPageJP;
import com.likelion.mimiki.exception.CommentNotFoundException;
import com.likelion.mimiki.repository.CommentRepository;
import com.likelion.mimiki.repository.WikiRepository;
import com.likelion.mimiki.repository.WikiRepositoryJP;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    //@Autowired
    private final CommentRepository commentRepository;
    private final WikiRepository wikiRepository;

    private CommentDTO convertToDTO(CommentEntity commentEntity) {
        CommentDTO commentDTO = new CommentDTO();
        BeanUtils.copyProperties(commentEntity, commentDTO);
        return commentDTO;
    }

    private CommentEntity convertToEntity(CommentDTO commentDTO) {
        CommentEntity commentEntity = new CommentEntity();
        BeanUtils.copyProperties(commentDTO, commentEntity, "id");
        return commentEntity;
    }

    // 저장
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


    // 저장의 일환
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

    //댓글 조회 기능
    public List<CommentDTO> getAllComments() {
        return commentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    //id 로 댓글 조회
    public CommentDTO getCommentById(Long id) {
        CommentEntity commentEntity = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment 를 찾을 수 없습니다.. id : " + id));
        return convertToDTO(commentEntity);
    }
}
