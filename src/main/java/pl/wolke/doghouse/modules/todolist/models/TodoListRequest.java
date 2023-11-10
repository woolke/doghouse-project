package pl.wolke.doghouse.modules.todolist.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Builder
public class TodoListRequest {
    private String color;
    private String title;
    private String category;
}
