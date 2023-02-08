package com.umc.pol.domain.template.controller;

import com.umc.pol.domain.template.service.TemplateService;
import com.umc.pol.global.response.ResponseService;
import com.umc.pol.global.response.SingleResponse;
import com.umc.pol.domain.template.dto.TemplateResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/question")
public class TemplateController {

    private final TemplateService templateService;
    private final ResponseService responseService;

    //tagId로 질문 template 반환
    @GetMapping("/{tagId}")
    public SingleResponse<TemplateResDto> searchTemplate(@PathVariable long tagId){
        TemplateResDto template = templateService.findTemplate(tagId);

        return responseService.getSingleResponse(template);

    }


}