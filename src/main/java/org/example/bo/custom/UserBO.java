package org.example.bo.custom;

import org.example.dto.UserRegistrationDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBO {
    ArrayList getAllUser() throws SQLException, ClassNotFoundException ;

    boolean saveUser(UserRegistrationDTO dto) throws SQLException ;

    boolean deleteUser(String id) throws SQLException ;

    boolean updateUser(UserRegistrationDTO dto) throws SQLException ;
}
