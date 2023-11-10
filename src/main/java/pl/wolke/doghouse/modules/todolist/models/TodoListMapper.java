package pl.wolke.doghouse.modules.todolist.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.wolke.doghouse.modules.auth.models.User;

import java.util.Date;
import java.util.UUID;

public class TodoListMapper {
    public static TodoListResponse toResponse(TodoList item) {
        return TodoListResponse.builder()
                .id(item.getId().toString())
                .color(item.getColor())
                .title(item.getTitle())
                .category(item.getCategory())
                .createdAt(item.getCreatedAt())
                .completedAt(item.getCompletedAt())
                .build();
    }
}
