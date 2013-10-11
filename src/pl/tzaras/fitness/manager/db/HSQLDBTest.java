/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.tzaras.fitness.manager.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hsqldb.Server;

/**
 *
 * @author tommi
 */
public class HSQLDBTest {
    
    public static int main (String [] args) {
        DatabaseEngine dbEngine = new DatabaseEngine();
        dbEngine.start();
        
        Connection connection = null;
        ResultSet rs = null;
        try {

            connection = DriverManager.getConnection("jdbc:hsqldb:file:testdb","SA", "");
        } catch (SQLException ex) {
            Logger.getLogger(HSQLDBTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        dbEngine.stop();
        return 0;
    }
    
}
