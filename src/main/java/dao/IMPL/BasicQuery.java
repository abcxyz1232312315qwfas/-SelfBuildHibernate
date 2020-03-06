package dao.IMPL;

import Annotation.PK;
import dao.JpaRepository;
import paging.Pageable;
import util.AnnotationUtil;
import util.MySqlConnectionUtil;
import util.ObjectUtil;
import util.pool.ConnectionPool;
import util.pool.ConnectionPoolIMPL;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BasicQuery<T, ID> implements JpaRepository<T,ID> {
    private ConnectionPool connectionPool;
    private Class<T> tClass;
    private Connection connection;
    public BasicQuery() {
        connectionPool = new ConnectionPoolIMPL();
        connection = MySqlConnectionUtil.getConnection();
        tClass = (Class<T>)((ParameterizedType) (getClass().getGenericSuperclass())).getActualTypeArguments()[0];
    }

    @Override
    public <S extends T> S insert(T entity) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, SQLException {
        StringBuilder sql = new StringBuilder(Query.INSERT);
        sql.append(AnnotationUtil.getTableName(tClass));
        Field[] fields = entity.getClass().getDeclaredFields();

        sql.append(Query.VALUES).append("(");

        Arrays.stream(fields).forEach(field -> {
//            try {
//                sql.append(ObjectUtil.getMethod(entity, field)).append(",");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            sql.append("?").append(",");
        });
        sql.deleteCharAt(sql.length() - 1).append(")");
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
        try {
            for (int i = 0; i < fields.length; i++) {
                if (fields[i].getType().getName().equals("java.time.LocalTime")) {
                    preparedStatement.setObject(i + 1, Time.valueOf((LocalTime) ObjectUtil.getMethod(entity, fields[i])));
                } else {
                    preparedStatement.setObject(i + 1, ObjectUtil.getMethod(entity, fields[i]));
                }
            }
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        }
        return (S) entity;
    }

    @Override
    public void update(T entity) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, SQLException {
        StringBuilder sql = new StringBuilder(Query.UPDATE);
        sql.append(AnnotationUtil.getTableName(tClass)).append(Query.SET);
        Field[] fields = entity.getClass().getDeclaredFields();
        Arrays.stream(fields).forEach(field -> {
            if (!field.isAnnotationPresent(PK.class)){
                try {
                    sql.append(AnnotationUtil.getFields(tClass, field.getName())).append("= ?").append(" ,");
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        });
        sql.deleteCharAt(sql.length()-1);
        try {
            sql.append(Query.WHERE).append(AnnotationUtil.getPrimarykey(tClass , fields[0].getName())).append(" = ?");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        connection.setAutoCommit(false);
        try {
            PreparedStatement ps = connection.prepareStatement(sql.toString());
            for (int i = 0; i < fields.length; i++) {
                if (!fields[i].isAnnotationPresent(PK.class)) {
                    if (fields[i].getType().getName().equals("java.time.LocalTime")) {
                        ps.setObject(i, Time.valueOf((LocalTime) ObjectUtil.getMethod(entity, fields[i])));
                    } else {
                        ps.setObject(i, ObjectUtil.getMethod(entity, fields[i]));
                    }
                }
            }
            ps.setObject(fields.length, ObjectUtil.getMethod(entity , fields[0]));
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        }
    }

    @Override
    public void delete(String Id) throws NoSuchFieldException, SQLException {
        StringBuilder sql = new StringBuilder(Query.DELETE);
        sql.append(AnnotationUtil.getTableName(tClass)).append(Query.WHERE).append(AnnotationUtil.getPrimarykey(tClass , "id")).append(" = ?");
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
            preparedStatement.setObject(1,Id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        }
    }

    @Override
    public <S extends T> S findById(ID id) {
        try {
            StringBuilder sql = new StringBuilder(Query.SELECT).append(AnnotationUtil.getTableName(tClass)).append(Query.WHERE).append(AnnotationUtil.getPrimarykey(tClass,"id")).append(" =?");
            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
            preparedStatement.setObject(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Object object = null;
            while (resultSet.next()){
                object = ObjectUtil.map(tClass,resultSet);
            }
            return (S) object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <S extends T> List<S> findAll(Pageable pageable) throws IllegalAccessException, NoSuchFieldException, InstantiationException, SQLException {
        StringBuilder sql = new StringBuilder(Query.SELECT).append(AnnotationUtil.getTableName(tClass)).append(Query.LIMIT).append(pageable.getOffset()).append(Query.OFFSET).append(pageable.getSize());
            PreparedStatement ps = connection.prepareStatement(sql.toString());
            ResultSet resultSet = ps.executeQuery();
            List<T> list = new ArrayList<>();
            while (resultSet.next()){
                T t = (T) ObjectUtil.map(tClass,resultSet);
                list.add(t);
            }
            return (List<S>) list;
    }

    @Override
    public long count() throws SQLException {
        StringBuilder sql = new StringBuilder(Query.COUNT).append(AnnotationUtil.getTableName(tClass));
        PreparedStatement ps = connection.prepareStatement(sql.toString());
        ResultSet rs = ps.executeQuery();
        long result = 0;
        while (rs.next()) {
            result = rs.getLong(1);
        }

        return result;
    }
    public interface Query {
        String SELECT = "SELECT * FROM ";
        String COUNT = " SELECT COUNT(*) FROM ";
        String WHERE = " WHERE ";
        String AND = "AND";
        String OR = "OR";
        String LIKE = "LIKE";
        String INSERT = "INSERT INTO ";
        String UPDATE = "UPDATE ";
        String DELETE = "DELETE FROM ";
        String SET = " SET ";
        String ORDER_BY = "ORDER BY";
        String VALUES = " VALUES ";
        String LIMIT = " LIMIT ";
        String OFFSET = " OFFSET ";
    }

}
