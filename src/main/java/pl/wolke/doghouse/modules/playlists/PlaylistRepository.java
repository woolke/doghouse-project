package pl.wolke.doghouse.modules.playlists;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wolke.doghouse.modules.auth.models.User;
import pl.wolke.doghouse.modules.playlists.models.Playlist;

import java.util.List;
import java.util.Optional;

@Repository

interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    Optional<Playlist> findByIdAndUser(Long id, User user);

    List<Playlist> findByUser(User user);
}
