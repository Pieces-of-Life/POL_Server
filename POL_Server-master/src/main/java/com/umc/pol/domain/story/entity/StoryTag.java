package com.umc.pol.domain.story.entity;

import com.umc.pol.global.entity.Tag;

import com.umc.pol.global.entity.BaseEntity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "story_tag")
public class StoryTag extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "story_id")
  private Story story;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "tag_id")
  private Tag tag;

  @NotNull
  @Column(name = "content")
  private String content;

  @Builder
  public StoryTag (
    Long id,
    Story story,
    Tag tag,
    String content
  ){
    this.id = id;
    this.story = story;
    this.tag = tag;
    this.content = content;
  }

}
