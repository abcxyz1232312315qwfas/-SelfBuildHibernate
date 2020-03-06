package util;

import java.sql.*;
import java.util.ResourceBundle;

public class MySqlConnectionUtil {
    private static final ResourceBundle rb =ResourceBundle.getBundle("DatabaseInformation");
    private static final String user_name= rb.getString("datasource.user");
    private static final String pass_word = rb.getString("datasource.password");
    private static final String URL=rb.getString("datasource.url");
    private static final String DriverName=rb.getString("datasource.driver");
    public static Connection getConnection(){
        try {
            Class.forName(DriverName);
            return DriverManager.getConnection(URL,user_name,pass_word);
        } catch (ClassNotFoundException e) {
            return null;
        }   catch (SQLException e){
            return null;
        }
    }

    public static void main(String[] args) throws SQLException {
       /* Connection connection = getConnection();
        String sql = "Select * from maybay";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        List<Plane> planes = new ArrayList<>();
        while (resultSet.next()){
           // Plane plane = new Plane();
            plane.setId(resultSet.getString("MaMB"));
            plane.setType(resultSet.getString("Loai"));
            plane.setTamBay(resultSet.getLong("TamBay"));
            planes.add(plane);
        }
        planes.stream().forEach(System.out::println);*/
    }

}
