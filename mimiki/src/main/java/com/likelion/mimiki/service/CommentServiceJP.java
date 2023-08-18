package com.likelion.mimiki.service;

import com.likelion.mimiki.dto.CommentDTO;
import com.likelion.mimiki.dto.CommentDTOJP;
import com.likelion.mimiki.entity.CommentEntity;
import com.likelion.mimiki.entity.CommentEntityJP;
import com.likelion.mimiki.entity.WikiPage;
import com.likelion.mimiki.entity.WikiPageJP;
import com.likelion.mimiki.exception.CommentNotFoundException;
import com.likelion.mimiki.repository.CommentRepository;
import com.likelion.mimiki.repository.CommentRepositoryJP;
import com.likelion.mimiki.repository.WikiRepository;
import com.likelion.mimiki.repository.WikiRepositoryJP;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceJP {
    //@Autowired
    private final CommentRepositoryJP commentRepositoryJP;
    private final WikiRepositoryJP wikiRepositoryJP;

    // 전송 과정
    private CommentDTOJP convertToDTO(CommentEntityJP commentEntityJP) {
        CommentDTOJP commentDTOJP = new CommentDTOJP();
        BeanUtils.copyProperties(commentEntityJP, commentDTOJP);
        return commentDTOJP;
    }

    private CommentEntityJP convertToEntityJP (CommentDTOJP commentDTOJP) {
        CommentEntityJP commentEntityJP = new CommentEntityJP();
        BeanUtils.copyProperties(commentDTOJP, commentEntityJP, "id");;
        return commentEntityJP;
    }


    // 저장
    public Long save(CommentDTOJP commentDTOJP) {
        /* 부모엔티티(WikiPage) 조회 */
        Optional<WikiPageJP> optionalWikiPageJP = wikiRepositoryJP.findById(commentDTOJP.getBoardId());


        if (optionalWikiPageJP.isPresent()) {
            WikiPageJP wikiPageJP = optionalWikiPageJP.get();

            CommentEntityJP commentEntityJP = CommentEntityJP.toSaveEntity(commentDTOJP, wikiPageJP);
            return commentRepositoryJP.save(commentEntityJP).getId();
        } else {
            return null;
        }
    }


    // 저장의 과정임 일단
    public List<CommentDTOJP> findAll(Long boardId) {
        WikiPageJP wikiPageJP = wikiRepositoryJP.findById(boardId).get();
        List<CommentEntityJP> commentEntityListJP = commentRepositoryJP.findAllByWikiPageJPOrderByIdDesc(wikiPageJP);
        /* EntityList -> DTOList */
        List<CommentDTOJP> commentDTOList = new ArrayList<>();
        for (CommentEntityJP commentEntityJP : commentEntityListJP) {
            CommentDTOJP commentDTOJP = CommentDTOJP.toCommentDTO(commentEntityJP, boardId);
            commentDTOList.add(commentDTOJP);
        }
        return commentDTOList;
    }

    //댓글 삭제 기능
    public void deleteComment(Long commentId) {
        Optional<CommentEntityJP> commentOptionalJP = commentRepositoryJP.findById(commentId);

        if (commentOptionalJP.isPresent()) {
            commentRepositoryJP.deleteById(commentId);
        } else {
            // 해당 ID에 대한 댓글이 없을 경우 예외 처리
            throw new CommentNotFoundException("Comment with id " + commentId + " not found.");
        }
    }

    //댓글 조회 기능
    public List<CommentDTOJP> getAllComments() {
        return commentRepositoryJP.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    //id 로 댓글 조회
    public CommentDTOJP getCommentById(Long id) {
        CommentEntityJP commentEntityJP = commentRepositoryJP.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment 를 찾을 수 없습니다.. id : " + id));
        return convertToDTO(commentEntityJP);
    }
}
