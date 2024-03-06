package org.example.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Book {
    @Id
    private String id;
    private String title;
    private String author;
    private String genre;
    private String availability_status;
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;


    public Book() {
    }

    public Book(String id, String title, String author, String genre, String availability_status, User user) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.availability_status = availability_status;
    }

    public Book(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAvailability_status() {
        return availability_status;
    }

    public void setAvailability_status(String availability_status) {
        this.availability_status = availability_status;
    }
}
