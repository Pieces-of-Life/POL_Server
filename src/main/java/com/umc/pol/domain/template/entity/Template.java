package com.umc.pol.domain.template.entity;
import com.umc.pol.global.entity.Tag;
import javax.validation.constraints.NotNull;
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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @NotNull
    @Column(name = "template_content")
    private String templateContent;

    @Builder
    public Template(Long id, Tag tag, String templateContent) {
        this.id = id;
        this.tag = tag;
        this.templateContent = templateContent;
    }
}
