package it.sidigroup.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class SensorDAO {

	private PreparedStatement getSensorLastReadings;
	private PreparedStatement getSensorsLastReadings;
	
	private DataSource dataSource;

	/**
	 * Create new data access object with data source.
	 */
	public SensorDAO(DataSource newDataSource) throws SQLException {
		setDataSource(newDataSource);
	}

	/**
	 * Get data source which is used for the database operations.
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * Set data source to be used for the database operations.
	 */
	public void setDataSource(DataSource newDataSource) throws SQLException {
		this.dataSource = newDataSource;
	}

	public List<Sensor> selectSensorsLastReadings() throws SQLException {
        Connection connection = dataSource.getConnection();
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM Sensor s full outer join SingleMeasurement sm where s.inventoryID = sm.inventoryID");
            ResultSet rs = pstmt.executeQuery();
            ArrayList<Sensor> list = new ArrayList<Sensor>();
            while (rs.next()) {
                Sensor s = new Sensor();
                /*p.setId(rs.getString(1));
                p.setFirstName(rs.getString(2));
                p.setLastName(rs.getString(3));*/
                list.add(s);
            }
            return list;
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
