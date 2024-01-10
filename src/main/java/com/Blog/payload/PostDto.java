package com.Blog.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private long id;

    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;
    // post description should be not null or empty
    // post description should have at least 10 characters

    @NotEmpty
    @Size(min = 5, message = "Post description should have at least 5 characters")
    private String Description;

    @NotEmpty
    @Size(min = 5, message = "Post content should have at least 5 characters")
    private String content;

}
