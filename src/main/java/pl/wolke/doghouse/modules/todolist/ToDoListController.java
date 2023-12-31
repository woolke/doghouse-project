package pl.wolke.doghouse.modules.todolist;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.wolke.doghouse.modules.todolist.models.TodoListCategory;
import pl.wolke.doghouse.modules.todolist.models.TodoListMapper;
import pl.wolke.doghouse.modules.todolist.models.TodoListRequest;
import pl.wolke.doghouse.modules.todolist.models.TodoListResponse;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

//@PreAuthorize("hasRole('ROLE_USER')")
@CrossOrigin(originPatterns = "*", maxAge = 3600, allowCredentials = "true")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/ng-girls/todolist")
class ToDoListController {

    private final ToDoListService service;

    @GetMapping("/")
    public ResponseEntity<List<TodoListResponse>> get() {
        return ResponseEntity.ok(service.getCurrentUserItems().stream()
                .map(TodoListMapper::toResponse)
                .collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoListResponse> edit(@PathVariable UUID id, @RequestBody TodoListRequest item) {
        try {
            return ResponseEntity.ok(TodoListMapper.toResponse(service.edit(id, item)));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        }
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<TodoListResponse> complete(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(TodoListMapper.toResponse(service.complete(id)));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<TodoListResponse> add(@RequestBody TodoListRequest item) {
        return ResponseEntity.ok(TodoListMapper.toResponse(service.add(item)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable UUID id) {
        service.remove(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categories")
    public ResponseEntity<TodoListCategory[]> getCategories() {
        return ResponseEntity.ok(TodoListCategory.values());
    }

}
