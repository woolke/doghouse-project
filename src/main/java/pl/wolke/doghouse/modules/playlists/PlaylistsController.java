package pl.wolke.doghouse.modules.playlists;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.wolke.doghouse.modules.playlists.models.Playlist;

import java.util.List;

//for Angular Client (withCredentials)
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/playlists")
class PlaylistsController {

    private final PlaylistsService service;

    @GetMapping("/")
    public ResponseEntity<List<Playlist>> get() {
        return ResponseEntity.ok(service.getCurrentUserPlaylists());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Playlist> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getPlaylist(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Playlist> edit(@PathVariable Long id, @RequestBody Playlist playlist) {
        try {
            return ResponseEntity.ok(service.edit(playlist));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        }

    }

    @PostMapping("/")
    public ResponseEntity<Playlist> add(@RequestBody Playlist playlist) {
        return ResponseEntity.ok(service.add(playlist));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        service.remove(id);
        return ResponseEntity.noContent().build();
    }

}
