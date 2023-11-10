package pl.wolke.doghouse.modules.playlists;

import org.springframework.stereotype.Service;
import pl.wolke.doghouse.modules.playlists.models.Playlist;

import java.util.List;

@Service
interface PlaylistsService {

    List<Playlist> getPlaylists();

    Playlist getPlaylist(Long id);

    List<Playlist> getCurrentUserPlaylists();

    Playlist add(Playlist playlist);

    Playlist edit(Playlist playlist);

    void remove(Long playlistId);
}
