package pl.wolke.doghouse.modules.todolist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wolke.doghouse.modules.auth.models.User;
import pl.wolke.doghouse.modules.todolist.models.TodoList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository

interface ToDoListRepository extends JpaRepository<TodoList, UUID> {
    Optional<TodoList> findByIdAndUser(UUID id, User user);

    List<TodoList> findByUser(User user);
}
