package com.umc.pol.domain.template.repository;

import com.umc.pol.domain.template.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateRepository extends JpaRepository<Template, Long> {
    //tagId로 검색
    List<Template> findAllByTagId(long tagId);
}
