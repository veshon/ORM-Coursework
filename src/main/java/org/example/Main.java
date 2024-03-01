package org.example;

import org.example.config.FactoryConfiguration;
import org.example.entity.Book;
import org.example.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Book book = new Book();
        book.setId("b001");
        book.setTitle("Harry Potter");
        book.setAuthor("Emma");
        book.setGenre("adventure");
        book.setAvailability_status("available");

        User user = new User();
        user.setId("u001");
        user.setName("Veshon");
        user.setAddress("UK");
        user.setEmail("veshonmabima17@gmail.com");
        user.setPassword("1234");

        session.save(user);
        session.save(book);

        book.setUser(user);

        transaction.commit();
        session.close();
    }
}