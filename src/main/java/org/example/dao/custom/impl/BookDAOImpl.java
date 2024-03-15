package org.example.dao.custom.impl;

import org.example.dao.SQLUtil;
import org.example.dao.custom.BookDAO;
import org.example.db.DbConnection;
import org.example.dto.BooksManagementDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO {

    @Override
    public ArrayList<BooksManagementDTO> getAll() throws SQLException, ClassNotFoundException {
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
                            resultSet.getDouble(5),
                            resultSet.getString(6),
                            resultSet.getString(7)
                    )
            );
        }
        return dtoList;
    }

    @Override
    public boolean save(BooksManagementDTO dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO book VALUES(?, ?, ?, ?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getTitle());
        pstm.setString(3, dto.getAuthor());
        pstm.setString(4, dto.getGenre());
        pstm.setDouble(5, dto.getPrice());
        pstm.setString(6, dto.getAvailability_status());
        pstm.setString(7, dto.getUser_id());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("DELETE FROM book WHERE id=?");
        pstm.setString(1,id);
        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean update(BooksManagementDTO dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("UPDATE book SET title=?, author=?, genre=?, price=?, user_id=? WHERE id=?");
        pstm.setString(1,dto.getTitle());
        pstm.setString(2, dto.getAuthor());
        pstm.setString(3, dto.getGenre());
        pstm.setDouble(4, dto.getPrice());
//        pstm.setString(5, dto.getAvailability_status());
        pstm.setString(5, dto.getUser_id());
        pstm.setString(6, dto.getId());
        return pstm.executeUpdate()> 0;

    }

    @Override
    public BooksManagementDTO search(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM book WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        BooksManagementDTO dto = null;

        if (resultSet.next()) {
            String BId = resultSet.getString(1);
            String title = resultSet.getString(2);
            String author = resultSet.getString(3);
            String genre = resultSet.getString(4);
            double price = resultSet.getDouble(5);
            String availability = resultSet.getString(6);
            String userId = resultSet.getString(7);

            dto = new BooksManagementDTO(BId, title, author, genre, price, availability, userId);
        }
        return dto;
    }
    public String generateNexBookId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT Id FROM book ORDER BY id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            return splitBookId(resultSet.getString(1));
        }
        return splitBookId(null);
    }

    private String splitBookId(String currentUserId) {
        if (currentUserId != null) {
            String[] split = currentUserId.split("B0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "B00" + id;
        } else {
            return "B001";
        }
    }
}
