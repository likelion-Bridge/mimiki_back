package com.likelion.mimiki.service;

import com.likelion.mimiki.dto.CommentDTO;
import com.likelion.mimiki.entity.CommentEntity;
import com.likelion.mimiki.entity.WikiPage;
import com.likelion.mimiki.repository.CommentRepository;
import com.likelion.mimiki.repository.WikiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
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
        List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdDesc(wikiPage);
        /* EntityList -> DTOList */
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (CommentEntity commentEntity: commentEntityList) {
            CommentDTO commentDTO = CommentDTO.toCommentDTO(commentEntity, boardId);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }

}
