package org.bearluxury.reservation;

import org.bearluxury.DAO;
import org.bearluxury.RoomResDAO;
import org.bearluxury.reservation.Reservation;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class ReservationJDBCDAO implements DAO<Reservation>, RoomResDAO<Reservation> {

    private Connection connection;

    private static String JDBC_URL = "jdbc:h2:~/test";

    /*You can use H2 database in in-memory mode, which means the database is created in memory when the first connection is established,
     and is removed when all connections are closed. The database engine runs in the same process of the application.
     Using H2 in-memory database is preferred for quick testing in which data is not need to be stored permanently on disk.
     To connect to a H2 in-memory database, you need to use JDBC URLs look like follows:
     jdbc:h2:mem:<databaseName> again it can be whatever like jdbc:h2:mem:reservation
     will only temporarily save it on each iteration
    */
    /*In embedded mode, a H2 database is stored permanently on disk and the database engine runs in the same process that runs the application.
      To connect to an H2 embedded database, you need to use one of the following JDBC URLs:
      jdbc:h2:~/test ‘test’ database in the user home directory
      can be named whatever test is just an example
      these below also work and store it in different places on the disk
      jdbc:h2:./test the ‘test’ database in the current directory
      jdbc:h2:/data/test the ‘test’ database in the /data directory (Unix)
      jdbc:h2:D:/data/test the ‘test’ database in the D:/data directory (Windows)
     */



    public ReservationJDBCDAO() {
        try {
            connection = DriverManager.getConnection(JDBC_URL);
            createReservationTableIfNotExists();
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
        }
    }

    private void createReservationTableIfNotExists() {
        try (Statement stmt = connection.createStatement()) {
            boolean tableExists = false;
            try {
                stmt.executeQuery("SELECT * FROM reservations");
                tableExists = true;
            } catch (SQLException e) {
            }

            if (!tableExists) {
                String createTableSQL = "CREATE TABLE reservations (" +
                        "roomNumber INT primary key, " +
                        "firstName VARCHAR(255), " +
                        "lastName VARCHAR(255), " +
                        "email VARCHAR(255), " +
                        "numberOfGuests INT, " +
                        "startDate DATE, " +
                        "endDate DATE" +
                        ")";


                stmt.executeUpdate(createTableSQL);
                System.out.println("table made");
            } else {
                System.out.println("Reservation table already exists.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Set<Reservation> list() {
        Set<Reservation> reservations = new TreeSet<>(Comparator.comparing(Reservation::getEmail));
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM reservations");

            while (resultSet.next()) {
                int roomNumber = resultSet.getInt("roomNumber");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                int numberOfGuests = resultSet.getInt("numberOfGuests");
                Date startDate = resultSet.getDate("startDate");
                Date endDate = resultSet.getDate("endDate");

                Reservation reservation = new Reservation(roomNumber, firstName, lastName, email, numberOfGuests, startDate, endDate);
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    @Override
    public void insert(Reservation reservation) {
        try {
            String sql = "INSERT INTO reservations (roomNumber, firstName, lastName, email, numberOfGuests, startDate, endDate) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, reservation.getRoomNumber());
            preparedStatement.setString(2, reservation.getFirstName());
            preparedStatement.setString(3, reservation.getLastName());
            preparedStatement.setString(4, reservation.getEmail());
            preparedStatement.setInt(5, reservation.getNumberOfGuests());
            preparedStatement.setDate(6, new java.sql.Date(reservation.getStartDate().getTime()));
            preparedStatement.setDate(7, new java.sql.Date(reservation.getEndDate().getTime()));

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new reservation was inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Reservation> get(int roomNumber) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM reservations WHERE roomNumber = ?");
            preparedStatement.setInt(1, roomNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int roomNumberResult = resultSet.getInt("roomNumber");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                int numberOfGuests = resultSet.getInt("numberOfGuests");
                Date startDate = resultSet.getDate("startDate");
                Date endDate = resultSet.getDate("endDate");

                Reservation reservation = new Reservation(roomNumberResult, firstName, lastName, email, numberOfGuests, startDate, endDate);
                return Optional.of(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void update(Reservation reservation, int roomNumber) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE reservations SET roomNumber = ?, firstName = ?, lastName = ?, email = ?, numberOfGuests = ?, startDate = ?, endDate = ? WHERE roomNumber = ?");
            preparedStatement.setInt(1, reservation.getRoomNumber());
            preparedStatement.setString(2, reservation.getFirstName());
            preparedStatement.setString(3, reservation.getLastName());
            preparedStatement.setString(4, reservation.getEmail());
            preparedStatement.setInt(5, reservation.getNumberOfGuests());
            preparedStatement.setDate(6, new java.sql.Date(reservation.getStartDate().getTime()));
            preparedStatement.setDate(7, new java.sql.Date(reservation.getEndDate().getTime()));
            preparedStatement.setInt(8, roomNumber);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Reservation updated successfully!");
            } else {
                System.out.println("No reservation found with room number: " + roomNumber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean delete(int roomNumber){
        try {
            PreparedStatement statement= connection.prepareStatement("DELETE FROM reservations WHERE roomNumber=?");
            statement.setLong(1, roomNumber);
            int rowsAffected = statement.executeUpdate();
            if(rowsAffected > 0){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public void close() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection closed successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Failed to close connection!");
            e.printStackTrace();
        }
    }
    @Override
    public void clear() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM reservations");
            int rowsDeleted = preparedStatement.executeUpdate();
            System.out.println(rowsDeleted + " rows deleted from reservations table.");
        } catch (SQLException e) {
            System.out.println("Failed to clear the database!");
            e.printStackTrace();
        }
    }
}