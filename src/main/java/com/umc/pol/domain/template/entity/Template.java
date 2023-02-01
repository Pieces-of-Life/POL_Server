package com.umc.pol.domain.template.entity;
<<<<<<< HEAD

import lombok.*;

=======
import javax.validation.constraints.NotNull;
import lombok.*;


>>>>>>> d6d9ccd7bbb5a0bb8ce4c3b5d5b4c247677f326e
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private long id;

<<<<<<< HEAD
    @Column(nullable = false, name = "tag_id")
    private long tagId;

    @Column(nullable = false, name = "template_content")
=======
    @NotNull
    @Column(name = "tag_id")
    private long tagId;
    @NotNull
    @Column(name = "template_content")
>>>>>>> d6d9ccd7bbb5a0bb8ce4c3b5d5b4c247677f326e
    private String templateContent;

    @Builder
    public Template(long tagId, String templateContent) {
        this.tagId = tagId;
        this.templateContent = templateContent;
    }
}
