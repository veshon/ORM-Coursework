package org.example.dao.custom;

import org.example.dao.CrudDAO;
import org.example.dto.UserRegistrationDTO;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO<UserRegistrationDTO> {

}