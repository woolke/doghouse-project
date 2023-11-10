package pl.wolke.doghouse.modules.playlists;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.wolke.doghouse.core.security.services.UserService;
import pl.wolke.doghouse.modules.auth.models.User;
import pl.wolke.doghouse.modules.playlists.models.Playlist;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@AllArgsConstructor
class PlaylistsServiceImpl implements PlaylistsService {

    public final PlaylistRepository repository;
    public final UserService userService;

    @Override
    public Playlist getPlaylist(Long id) {
        return getPlaylist(id, userService.getCurrentUser());
    }
    @Override
    public List<Playlist> getPlaylists() {
        return repository.findAll();
    }

    @Override
    public List<Playlist> getCurrentUserPlaylists() {
        return repository.findByUser(userService.getCurrentUser());
    }

    @Override
    public Playlist add(Playlist playlist) {
        playlist.setUser(userService.getCurrentUser());
        return repository.save(playlist);
    }

    @Override
    public Playlist edit(Playlist playlist) {
        if (playlist.getId() == null) {
            throw new ResponseStatusException(BAD_REQUEST, "Id is requierd");
        } else {
            Optional<Playlist> byId = repository.findById(playlist.getId());
            if (byId.isPresent()) {
                return repository.save(playlist);
            } else {
                throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
            }
        }
    }

    @Override
    public void remove(Long playlistId) {
        if (playlistId == null) {
            throw new ResponseStatusException(BAD_REQUEST, "Id is requierd");
        } else {
            Optional<Playlist> byId = repository.findById(playlistId);
            if (byId.isPresent()) {
                repository.deleteById(playlistId);
            } else {
                throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
            }
        }
    }

    private Playlist getPlaylist(Long id, User user) {
        return repository.findByIdAndUser(id, user).orElseThrow(
                () -> new ResponseStatusException(NOT_FOUND, "Unable to find resource")
        );
    }
}
