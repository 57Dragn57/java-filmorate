package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.storage.film.Genre;
import ru.yandex.practicum.filmorate.storage.film.Mpa;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

@Data
public class Film {

    private LinkedHashSet<Genre> genres = new LinkedHashSet<>();
    @NotNull
    private Mpa mpa;
    private int id;
    @NotBlank
    private String name;
    @Size(max = 200)
    @NotNull
    private String description;
    @NotNull
    private LocalDate releaseDate;
    @Positive
    private int duration;

    public void addGenres(Genre genre){
        genres.add(genre);
    }

    public void addGenres(LinkedHashSet<Genre> genre){
        this.genres = genre;
    }

    public Film(int id, String name, String description, int duration, LocalDate releaseDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.releaseDate = releaseDate;
    }

    public Film() {
    }

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
