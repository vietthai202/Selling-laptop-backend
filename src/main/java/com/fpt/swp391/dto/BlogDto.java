package com.fpt.swp391.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogDto {
    private Long id;
    private String userName;
    private String name;
    private String content;
    private  String image;
    private Date createdAt;
    private String shortContent;
    private String slug;
    private Long categoryId;
}
