package com.KHH.novelsite.episode.entity;

import com.KHH.novelsite.novel.entity.Novel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"novel_id", "episode_no"})
        }
)
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "epno")
    private Long epno;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "episode_no")
    private Long episodeno;

    @Column(name = "view_count", nullable = false)
    private Long viewCount = 0L;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "novel_id")
    @JsonBackReference
    private Novel novel;
}
