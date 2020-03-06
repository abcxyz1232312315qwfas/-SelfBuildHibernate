package dao.IMPL;

import com.mysql.cj.MysqlConnection;
import dao.JpaRepository;
import dao.TripDao;
import model.Plane;
import model.Trip;
import paging.PageRequest;
import util.MySqlConnectionUtil;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TripDaoImpl extends BasicQuery<Trip, String > implements TripDao {
//    @Override
//    public void insert(Trip trip) {
//        Connection connection = MySqlConnectionUtil.getConnection();
//        String sql = "insert into CHUYENBAY values(?,?,?,?,?,?,?)";
//        try {
//            connection.setAutoCommit(false);
//            PreparedStatement ps = connection.prepareStatement(sql);
//            ps.setString(1,trip.getId());
//            ps.setString(2,trip.getStart());
//            ps.setString(3,trip.getDestination());
//            ps.setLong(4,trip.getLength());
//            ps.setTime(5, Time.valueOf(trip.getStratedTime()));
//            ps.setTime(6, Time.valueOf(trip.getDestinationTime().plusHours(2)));
//            ps.setInt(7,trip.getPrice());
//            ps.executeUpdate();
//            connection.commit();
//        } catch (SQLException e) {
//            try {
//                connection.rollback();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    public void update(String id, Trip trip) {
//        Connection connection = MySqlConnectionUtil.getConnection();
//        String sql = "UPDATE CHUYENBAY SET GaDi=?, GaDen=?, DoDai=?, GioDi=?, GioDen=?, ChiPhi=? WHERE MaCB=?";
//        try {
//            connection.setAutoCommit(false);
//            PreparedStatement ps = connection.prepareStatement(sql);
//            ps.setString(1,trip.getStart());
//            ps.setString(2,trip.getDestination());
//            ps.setLong(3,trip.getLength());
//            ps.setTime(4, Time.valueOf(trip.getStratedTime()));
//            ps.setTime(5, Time.valueOf(trip.getDestinationTime().plusHours(2)));
//            ps.setInt(6,trip.getPrice());
//            ps.setString(7,trip.getId());
//            ps.executeUpdate();
//            connection.commit();
//        } catch (SQLException e) {
//            try {
//                connection.rollback();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    public void delete(String id) {
//        Connection connection = MySqlConnectionUtil.getConnection();
//        try {
//            connection.setAutoCommit(false);
//            String sql = "DELETE FROM CHUYENBAY WHERE MaCB=?";
//            PreparedStatement ps = connection.prepareStatement(sql);
//            ps.setString(1,id);
//            ps.executeUpdate();
//            connection.commit();
//        } catch (SQLException e) {
//            try {
//                connection.rollback();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        }
//
//    }
//
//    @Override
//    public Trip findById(String id) {
//        Connection connection = MySqlConnectionUtil.getConnection();
//        Trip trip = new Trip();
//        try {
//            connection.setAutoCommit(false);
//            String sql = "select * from CHUYENBAY where MaCB = ?";
//            PreparedStatement ps = connection.prepareStatement(sql);
//            ps.setString(1,id);
//            ResultSet resultSet = ps.executeQuery();
//            while (resultSet.next()){
//                trip= convertToTrip(resultSet);
//            }
//            connection.commit();
//        } catch (SQLException e) {
//            try {
//                connection.rollback();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        }
//        return trip;
//    }
//
//    @Override
//    public List<Trip> findAll() {
//        Connection connection = MySqlConnectionUtil.getConnection();
//        List<Trip> trips = new ArrayList<>();
//        try {
//            connection.setAutoCommit(false);
//            String sql = "select * from CHUYENBAY";
//            PreparedStatement ps = connection.prepareStatement(sql);
//            ResultSet resultSet = ps.executeQuery();
//            while (resultSet.next()){
//                Trip trip = new Trip();
//                trip = convertToTrip(resultSet);
//                trips.add(trip);
//            }
//            connection.commit();
//        } catch (SQLException e) {
//            try {
//                connection.rollback();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        }
//        return trips;
//    }
//
//
//    public Trip convertToTrip(ResultSet resultSet) throws SQLException {
//        Trip trip = new Trip();
//        trip.setId(resultSet.getString(1));
//        trip.setStart(resultSet.getString(2));
//        trip.setDestination(resultSet.getString(3));
//        trip.setLength(resultSet.getLong(4));
//        trip.setStratedTime(resultSet.getTime(5).toLocalTime());
//        trip.setDestinationTime(resultSet.getTime(6).toLocalTime());
//        trip.setPrice(resultSet.getInt(7));
//        return trip;
//    }
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, SQLException, IllegalAccessException, NoSuchFieldException, InstantiationException {
        TripDao tripDao = new TripDaoImpl();
        //System.out.println(tripDao.findById("VN216"));
       List<Trip> trips = tripDao.findAll(PageRequest.of(1,10));
       trips.forEach(System.out::println);
        System.out.println(tripDao.count());
    }
}
