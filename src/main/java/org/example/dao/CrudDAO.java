package org.example.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T>{

    ArrayList<T> getAll() throws SQLException, ClassNotFoundException ;

    boolean save(T dto) throws SQLException ;

    boolean delete(String id) throws SQLException ;

    boolean update(T dto) throws SQLException ;

    T search(String id) throws SQLException, ClassNotFoundException;

}
