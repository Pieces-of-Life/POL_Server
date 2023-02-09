package com.umc.pol.domain.story.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class GetStoryDto {
    private Long id;
    private String title;
    private String description;
    private String color;
    private boolean isMain;
    private boolean isOpen;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate date;
}
