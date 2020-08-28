package com.agh.iet.ubooku.payload;

import com.agh.iet.ubooku.model.book.Categories;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class BookUploadRequest {

    @NotNull
    private String author;

    @NotNull
    private String title;

    @NotNull
    @NotEmpty
    private List<Categories> categories;
}
