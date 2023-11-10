package pl.wolke.doghouse.modules.todolist.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Builder
public class TodoListResponse {
    private String id;
    private String color;
    private String title;
    private TodoListCategory category;
    private Date createdAt;
    private Date completedAt;
}
