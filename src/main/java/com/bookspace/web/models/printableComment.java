package com.bookspace.web.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class printableComment {
    private String comment;
    private String userImg;
    private String userNickname;
}
