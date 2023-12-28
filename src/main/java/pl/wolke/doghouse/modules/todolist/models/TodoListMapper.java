package pl.wolke.doghouse.modules.todolist.models;

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
