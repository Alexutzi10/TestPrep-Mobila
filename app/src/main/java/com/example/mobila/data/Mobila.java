package com.example.mobila.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.Objects;

@Entity(tableName = "mobila")
public class Mobila {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private int weight;
    private String producer;
    private Date date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Mobila(long id, int weight, String producer, Date date) {
        this.id = id;
        this.weight = weight;
        this.producer = producer;
        this.date = date;
    }

    @Ignore
    public Mobila(int weight, String producer, Date date) {
        this.weight = weight;
        this.producer = producer;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mobila mobila = (Mobila) o;
        return weight == mobila.weight && Objects.equals(producer, mobila.producer) && Objects.equals(date, mobila.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, producer, date);
    }

    @Override
    public String toString() {
        return "Mobila{" +
                "id=" + id +
                ", wight=" + weight +
                ", producer='" + producer + '\'' +
                ", date=" + date +
                '}';
    }
}
