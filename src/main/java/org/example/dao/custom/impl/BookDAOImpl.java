package org.example.dao.custom.impl;

import org.example.db.DbConnection;
import org.example.dto.BooksManagementDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl {

    public boolean saveBook(BooksManagementDTO dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO book VALUES(?, ?, ?, ?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getTitle());
        pstm.setString(3, dto.getAuthor());
        pstm.setString(4, dto.getGenre());
        pstm.setString(5, dto.getAvailability_status());
        pstm.setString(6, dto.getUser_id());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public List<BooksManagementDTO> getAllBook() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM book";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<BooksManagementDTO> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new BooksManagementDTO(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6)
                    )
            );
        }
        return dtoList;
    }
}