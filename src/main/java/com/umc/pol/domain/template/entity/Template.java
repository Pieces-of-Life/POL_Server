package com.umc.pol.domain.template.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private long id;

    @Column(nullable = false, name = "tag_id")
    private long tagId;

    @Column(nullable = false, name = "template_content")
    private String templateContent;

    @Builder
    public Template(long tagId, String templateContent) {
        this.tagId = tagId;
        this.templateContent = templateContent;
    }
}
