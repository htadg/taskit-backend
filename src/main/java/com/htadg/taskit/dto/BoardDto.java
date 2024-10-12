package com.htadg.taskit.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BoardDto {
    private Long id;
    private String name;
    private String description;
    private List<String> adminList;
    private List<String> memberList;
    private List<String> taskList;
}
