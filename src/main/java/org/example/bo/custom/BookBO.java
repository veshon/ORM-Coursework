package org.example.bo.custom;

import org.example.dto.BooksManagementDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BookBO {
    ArrayList getAllBook() throws SQLException, ClassNotFoundException ;

    boolean saveBook(BooksManagementDTO dto) throws SQLException ;

    boolean deleteBook(String id) throws SQLException ;

    boolean updateBook(BooksManagementDTO dto) throws SQLException ;
}
