package org.example.bo.custom.impl;

import org.example.bo.custom.UserBO;
import org.example.dao.custom.UserDAO;
import org.example.dao.custom.impl.UserDAOImpl;
import org.example.dto.UserRegistrationDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = new UserDAOImpl();

    @Override
    public ArrayList getAllUser() throws SQLException, ClassNotFoundException {
        return userDAO.getAll();
    }

    @Override
    public boolean saveUser(UserRegistrationDTO dto) throws SQLException {
        return userDAO.save(dto);
    }


    @Override
    public boolean deleteUser(String id) throws SQLException {
        return userDAO.delete(id);
    }

    @Override
    public boolean updateUser(UserRegistrationDTO dto) throws SQLException {
        return userDAO.update(dto);
    }

}
