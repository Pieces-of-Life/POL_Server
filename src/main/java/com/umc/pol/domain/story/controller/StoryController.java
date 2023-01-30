package com.umc.pol.domain.story.controller;

import com.umc.pol.domain.story.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/storys")
public class StoryController {
  private final StoryService storyService;
}
