package pl.wolke.doghouse.modules.playlists.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.wolke.doghouse.modules.auth.models.User;

import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "playlists",
        indexes = {
                @Index(name = "uniqueMulitIndex", columnList = "spotifyId, user_id", unique = true)
        })
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @NotBlank
    @Size(max = 255)
    private String name;

    private Integer tracks;

    private String color;

    private String description;

    private String category;

    private boolean favourite;

    private String spotifyId;

}
