package ru.yandex.practicum.filmorate.storage.film;

import lombok.Data;

@Data
public class Genre {

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Genre(int id) {
        this.id = id;
    }

    public Genre() {
    }

    int id;
    String name;
}
