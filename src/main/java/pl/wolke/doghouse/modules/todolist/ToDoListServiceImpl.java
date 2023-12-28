package pl.wolke.doghouse.modules.todolist;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.wolke.doghouse.core.security.services.UserService;
import pl.wolke.doghouse.modules.auth.models.User;
import pl.wolke.doghouse.modules.playlists.models.Playlist;
import pl.wolke.doghouse.modules.todolist.models.TodoList;
import pl.wolke.doghouse.modules.todolist.models.TodoListCategory;
import pl.wolke.doghouse.modules.todolist.models.TodoListRequest;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@AllArgsConstructor
class ToDoListServiceImpl implements ToDoListService {

    public final ToDoListRepository repository;
    public final UserService userService;

    @Override
    public List<TodoList> getCurrentUserItems() {
        return repository.findByUser(userService.getCurrentUser());
    }

    @Override
    public TodoList add(TodoListRequest item) {
        return repository.save(TodoList.builder()
                .createdAt(new Date())
                .user(userService.getCurrentUser())
                .color(item.getColor())
                .title(item.getTitle())
                .category(mapCategory(item.getCategory()))
                .build());
    }

    @Override
    public TodoList complete(UUID itemId) {
        if (itemId == null) {
            throw new ResponseStatusException(BAD_REQUEST, "Id is requierd");
        } else {
            Optional<TodoList> byId = repository.findById(itemId);
            if (byId.isPresent()) {
                return repository.save(byId.get().toBuilder()
                        .completedAt(new Date())
                        .build());
            } else {
                throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
            }
        }
    }

    @Override
    public TodoList edit(UUID id, TodoListRequest item) {
        if (id == null) {
            throw new ResponseStatusException(BAD_REQUEST, "Id is requierd");
        } else {
            Optional<TodoList> byId = repository.findById(id);
            if (byId.isPresent()) {
                return repository.save(byId.get().toBuilder()
                        .color(item.getColor())
                        .title(item.getTitle())
                        .category(mapCategory(item.getCategory()))
                        .build());
            } else {
                throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
            }
        }
    }

    @Override
    public void remove(UUID itemId) {
        if (itemId == null) {
            throw new ResponseStatusException(BAD_REQUEST, "Id is requierd");
        } else {
            Optional<TodoList> byId = repository.findById(itemId);
            if (byId.isPresent()) {
                repository.deleteById(itemId);
            } else {
                throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
            }
        }
    }

    private TodoListCategory mapCategory(String category) {
        try {
            return TodoListCategory.valueOf(category);
        } catch (Exception e) {
            return TodoListCategory.OTHER;
        }
    }
}
