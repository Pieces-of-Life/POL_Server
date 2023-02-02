package com.umc.pol.domain.template.service;

import com.umc.pol.domain.template.dto.TemplateResDto;
import com.umc.pol.domain.template.entity.Template;
import com.umc.pol.domain.template.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final TemplateRepository templateRepository;

    public TemplateResDto findTemplate(long tagId){

        List<Template> templates = templateRepository.findAllByTagId(tagId);

        // tagId 맞는 Template 중에 랜덤으로 1개 고르기
        Random random = new Random();
        String templateContent = templates.get(random.nextInt(templates.size()))
                .getTemplateContent();

        return TemplateResDto.builder()
                .questionTemplate(templateContent)
                .build();

    }
}
