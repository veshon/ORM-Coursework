package org.example.bo.custom.impl;

import org.example.bo.custom.BookBO;
import org.example.dao.custom.BookDAO;
import org.example.dao.custom.impl.BookDAOImpl;
import org.example.dto.BooksManagementDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class BookBOImpl implements BookBO {

    BookDAO bookDAO = new BookDAOImpl();

    @Override
    public ArrayList getAllBook() throws SQLException, ClassNotFoundException {
        return bookDAO.getAll();
    }

    @Override
    public boolean saveBook(BooksManagementDTO dto) throws SQLException {
        return bookDAO.save(dto);
    }

    @Override
    public boolean deleteBook(String id) throws SQLException {
        return bookDAO.delete(id);
    }

    @Override
    public boolean updateBook(BooksManagementDTO dto) throws SQLException {
        return bookDAO.update(dto);
    }

}
