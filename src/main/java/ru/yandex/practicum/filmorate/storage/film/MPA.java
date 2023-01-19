package ru.yandex.practicum.filmorate.storage.film;

import lombok.Data;

@Data
public class MPA {

    public MPA(String name, int id) {
        this.id = id;
        this.name = name;
    }

    public MPA(int id) {
        this.id = id;
    }

    public MPA() {
    }

    int id;
    String name;
}
