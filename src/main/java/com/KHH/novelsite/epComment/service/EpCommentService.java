package com.KHH.novelsite.epComment.service;

import com.KHH.novelsite.epComment.entity.EpComment;
import com.KHH.novelsite.epComment.repository.EpCommentRepository;
import com.KHH.novelsite.epComment.request.EpCommentCreateRequest;
import com.KHH.novelsite.epComment.request.EpCommentUpdateRequest;
import com.KHH.novelsite.epComment.response.EpCommentResponse;
import com.KHH.novelsite.episode.entity.Episode;
import com.KHH.novelsite.episode.repository.EpisodeRepository;
import com.KHH.novelsite.user.entity.User;
import com.KHH.novelsite.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EpCommentService {

    private final EpCommentRepository epCommentRepository;
    private final UserRepository userRepository;
    private final EpisodeRepository episodeRepository;

    @Transactional
    public EpComment createComment(Long uno, Long epno, EpCommentCreateRequest request) {
        User user = userRepository.findById(uno).orElseThrow();
        Episode episode = episodeRepository.findById(epno).orElseThrow();

        EpComment comment = new EpComment();
        comment.setContent(request.getContent());
        comment.setUser(user);
        comment.setEpisode(episode);

        return epCommentRepository.save(comment);
    }

    @Transactional
    public List<EpCommentResponse> getComments(Long epno) {
        return epCommentRepository.findByEpisode_EpnoOrderByCreatedAtDesc(epno).stream()
                .map(c -> {
                    User user = c.getUser(); // Lazy 프록시 객체 꺼내기
                    String nickname = (user != null && user.getNickname() != null) ? user.getNickname() : "익명";
                    return new EpCommentResponse(
                            c.getEpcommentno(),
                            c.getContent(),
                            nickname,
                            c.getCreatedAt()
                    );
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateComment(Long uno, EpCommentUpdateRequest request) {
        EpComment comment = epCommentRepository.findById(request.getEpCommentNo())
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        if (!comment.getUser().getUno().equals(uno)) {
            throw new SecurityException("본인의 댓글만 수정할 수 있습니다.");
        }

        comment.setContent(request.getContent());
    }

    @Transactional
    public void deleteComment(Long uno, Long epCommentNo) {
        EpComment comment = epCommentRepository.findById(epCommentNo)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        if (!comment.getUser().getUno().equals(uno)) {
            throw new SecurityException("본인의 댓글만 삭제할 수 있습니다.");
        }

        epCommentRepository.delete(comment);
    }

    @Transactional
    public String getNicknameByUno(Long uno) {
        return userRepository.findById(uno)
                .map(user -> user.getNickname())
                .orElse("GUEST");
    }

}
