package util.pool;

import util.MySqlConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class ConnectionPoolIMPL implements ConnectionPool {
    private static final ResourceBundle rs= ResourceBundle.getBundle("DatabaseInformation");
    private static final LinkedList<Object> connectionInues = new LinkedList<>();
    private static final int MAX_CONNECTION = Integer.parseInt(rs.getString("connection.threadpool"));
    private synchronized void initializeConnectionPool() {  //Phuong thuc khoi tao connection
        while (!checkIfConnectionPoolIsFull()){  //check neu khac tru thi` tao 1 connection nua vao add vao list
            Connection newConnection = MySqlConnectionUtil.getConnection();
            connectionInues.add(newConnection);
        }
        notifyAll();
    }
    private boolean checkIfConnectionPoolIsFull() {
        return connectionInues.size()>MAX_CONNECTION; // Kiem tra xem co vuot qua so luong cho phep ko.
    }
    @Override
    public synchronized Connection getConnection() {
        if (connectionInues.size() == MAX_CONNECTION){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return (Connection) connectionInues.poll(); //tra ra connection dau tien liskedlist
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        try {
            if (connection.isClosed()){
                initializeConnectionPool();  //neu connection nay dong khoi tao connection moi
            }
            else {
                boolean isRealease = connectionInues.offer(connection); // them moi 1 connection
                notifyAll();    //danh thuc tat ca các connection đang ngủ
                return isRealease;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
