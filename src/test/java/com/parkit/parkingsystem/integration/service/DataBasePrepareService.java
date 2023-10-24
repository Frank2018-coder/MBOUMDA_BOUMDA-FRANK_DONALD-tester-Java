package com.parkit.parkingsystem.integration.service;

import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

public class DataBasePrepareService {

    DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();

    public void clearDataBaseEntries(){
        Connection connection = null;
        try{
            connection = dataBaseTestConfig.getConnection();

            //set parking entries to available
            connection.prepareStatement("update parking set available = true").execute();

            //clear ticket entries;
            connection.prepareStatement("truncate table ticket").execute();

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            dataBaseTestConfig.closeConnection(connection);
        }
    }
    
    //fonction pour test
    public void updateTicket(String vehiculeNumber, Date date) {
        Connection con = null;
        try {
            con = dataBaseTestConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("update ticket set IN_TIME=? where VEHICLE_REG_NUMBER=? AND OUT_TIME is null");
            
            ps.setTimestamp(1, new Timestamp(date.getTime()));
            ps.setString(2,vehiculeNumber);
            ps.execute();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
        	dataBaseTestConfig.closeConnection(con);
        }
    }
    
    //methode pour faire dormir le programme 
    public void sleep(int nombreSeconde) {
    	try {
			Thread.sleep(1000*nombreSeconde);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


}
