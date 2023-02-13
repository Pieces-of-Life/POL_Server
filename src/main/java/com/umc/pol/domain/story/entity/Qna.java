package com.umc.pol.domain.story.entity;

import com.umc.pol.global.entity.Tag;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "qna")
public class Qna {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "story_id")
  private Story story;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "tag_id")
  private Tag tag;

  @NotNull
  @Column(name = "question")
  private String question;

  @NotNull
  @Column(name = "answer")
  private String answer;

  @Builder
  public Qna (
    Long id,
    Story story,
    Tag tag,
    String question,
    String answer
  ){
    this.id = id;
    this.story = story;
    this.tag = tag;
    this.question = question;
    this.answer = answer;
  }

  public void setStory(Story story) {
    this.story = story;
  }

}
