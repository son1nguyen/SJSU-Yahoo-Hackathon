
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.ir.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author sonnguyen
 */
public class CentralComputer {

    private Connection conn;
    ArrayList<ErrorMessage> errorMessages;
    private HashMap<String, String> nodeRecords;

    public CentralComputer() {
	errorMessages = new ArrayList<>();
	nodeRecords = new HashMap<>();
    }

    /*public String getSolution(String node, String summary) {

     }*/
    public String reportError(String message) {
	ErrorMessage err = new ErrorMessage(message);

	insertDatabase(err);

	synchronized (errorMessages) {
	    errorMessages.add(err);
	}

	if (nodeRecords.containsKey(err.getNode())) {
	    String requestRecord = nodeRecords.get(err.getNode());
	    if (requestRecord.contains(err.getProperty() + err.getSummary())) {
		System.out.println("Don't ask twice for the same error");
		return null;
	    } else {
		System.out.println("Sent solution");
		nodeRecords.replace(err.getNode(), nodeRecords.get(err.getNode()) + err.getProperty() + err.getSummary());
		return "Solution: " + err.getProperty() + err.getSummary();
	    }
	} else {
	    nodeRecords.put(err.getNode(), err.getProperty() + err.getSummary());
	    return err.getProperty() + err.getSummary();
	}
    }

    public void insertDatabase(ErrorMessage err) {

	try {
	    String sql = "insert into message (severity, count, node, property, firstOccur, summary) values(?, ?, ?, ?, ?, ?)";

	    if (conn == null) {
		try {
		    conn = DriverManager.getConnection("jdbc:mysql://localhost/hackathon", "root", "");
		} catch (SQLException ex) {
		    System.err.println("Connection problem");
		}
	    }
	    //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

	    //System.out.println("test");
	    PreparedStatement preparedStatement = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);

	    preparedStatement.setInt(1, err.getSeverity());
	    preparedStatement.setInt(2, err.getCount());
	    preparedStatement.setString(3, err.getNode());
	    preparedStatement.setString(4, err.getProperty());
	    preparedStatement.setString(5, err.getFirstOccur());
	    preparedStatement.setString(6, err.getSummary());

	    preparedStatement.execute();
	    //conn.commit();
	} catch (SQLException ex) {
	    Logger.getLogger(CentralComputer.class.getName()).log(Level.SEVERE, null, ex);
	}

    }
}
