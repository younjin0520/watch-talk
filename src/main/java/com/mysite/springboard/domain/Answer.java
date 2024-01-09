package com.mysite.springboard.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne  // 하나의 질문에 답변 여러개가 달릴 수 있다. - 실제 DB에서 Fk 관계가 생성된다.
    private Question question;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    private SiteUser author;

    private LocalDateTime modifyDate;   // 수정일시

    @ManyToMany
    Set<SiteUser> voter;

    Integer voterCount;
}
