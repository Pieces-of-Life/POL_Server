package com.umc.pol.domain.story.controller;

import com.umc.pol.domain.story.service.StoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/storys")
@SecurityRequirement(name = "Bearer")
@Tag(name = "storys", description = "이야기 API")
public class StoryController {
  private final StoryService storyService;
}
