package ru.yandex.practicum.filmorate.storage.film;

import lombok.Data;

@Data
public class Mpa {

    private int id;
    private String name;

    public Mpa(String name, int id) {
        this.id = id;
        this.name = name;
    }

    public Mpa(int id) {
        this.id = id;
    }

}
