package spring.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by olulrich on 09.12.17.
 */

@Entity
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String species;

    @Column(nullable = false)
    private int length;

    @Column(nullable = false)
    private int width;

    @Column(nullable = false)
    private int weight;

    public Animal() {};

    public Animal(String species, int width, int length, int weight) {
        this.species = species;
        this.width = width;
        this.length = length;
        this.weight = weight;
    }

    public int getSurfaceArea() {
        return width * length;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return getId() == animal.getId() &&
                getLength() == animal.getLength() &&
                getWidth() == animal.getWidth() &&
                getWeight() == animal.getWeight() &&
                Objects.equals(getSpecies(), animal.getSpecies());
    }

}
