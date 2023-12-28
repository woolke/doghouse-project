package pl.wolke.doghouse.modules.todolist;

import pl.wolke.doghouse.modules.todolist.models.TodoList;
import pl.wolke.doghouse.modules.todolist.models.TodoListRequest;

import java.util.List;
import java.util.UUID;

interface ToDoListService {

    List<TodoList> getCurrentUserItems();

    TodoList add(TodoListRequest item);

    TodoList complete(UUID itemId);

    TodoList edit(UUID id, TodoListRequest item);

    void remove(UUID itemId);
}
