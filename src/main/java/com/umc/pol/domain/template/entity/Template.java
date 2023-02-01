package com.umc.pol.domain.template.entity;
import lombok.*;
import javax.validation.constraints.NotNull;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private long id;

    @NotNull
    @Column(name = "tag_id")
    private long tagId;
    @NotNull
    @Column(name = "template_content")

    private String templateContent;

    @Builder
    public Template(long tagId, String templateContent) {
        this.tagId = tagId;
        this.templateContent = templateContent;
    }
}
