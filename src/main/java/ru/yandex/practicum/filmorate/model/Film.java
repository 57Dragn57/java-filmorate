package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.storage.film.Genre;
import ru.yandex.practicum.filmorate.storage.film.MPA;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Data
public class Film {
    private final HashSet<Integer> likesList = new HashSet<>();

    public Film(int id, String name, String description, int duration, LocalDate releaseDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.releaseDate = releaseDate;
    }

    public Film() {
    }

    private List<Genre> genres;
    private MPA mpa;
    private int id;
    @NotBlank
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("name", name);
        values.put("description", description);
        values.put("releace_date", releaseDate);
        values.put("duration", duration);
        values.put("rating_id", mpa.getId());
        return values;
    }
}
