package org.example.dao.custom.impl;

import org.example.dao.custom.UserDAO;
import org.example.db.DbConnection;
import org.example.dto.UserRegistrationDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {
    @Override
    public ArrayList<UserRegistrationDTO> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(UserRegistrationDTO dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO user VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getAddress());
        pstm.setString(4, dto.getEmail());
        pstm.setString(5, dto.getPassword());

        return pstm.executeUpdate() > 0;

    }

    @Override
    public boolean delete(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("DELETE FROM user WHERE id=?");
        pstm.setString(1,id);
        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean update(UserRegistrationDTO dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE user SET name = ?, address = ?, email = ?, password = ? WHERE id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(4, dto.getPassword());
        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getAddress());
        pstm.setString(3, dto.getEmail());
        pstm.setString(5, dto.getId());

        return pstm.executeUpdate() > 0;
    }

    @Override
    public UserRegistrationDTO search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    public String generateNexUserId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT Id FROM user ORDER BY id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            return splitUserId(resultSet.getString(1));
        }
        return splitUserId(null);
    }

    private String splitUserId(String currentUserId) {
        if (currentUserId != null) {
            String[] split = currentUserId.split("U0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "U00" + id;
        } else {
            return "U001";
        }
    }
}
