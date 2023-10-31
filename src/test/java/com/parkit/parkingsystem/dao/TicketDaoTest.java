package com.parkit.parkingsystem.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;


class TicketDaoTest {

	private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	private static TicketDAO ticketdao;
    private static ParkingSpotDAO parkingSpotdao;
    private static DataBasePrepareService dataBasePrepareService;
    
    @BeforeAll
    private static void setUp() throws Exception{
    	ticketdao = new TicketDAO();
    	parkingSpotdao = new ParkingSpotDAO();
    	ticketdao.dataBaseConfig = dataBaseTestConfig;
    	parkingSpotdao.dataBaseConfig = dataBaseTestConfig;
    	
    	dataBasePrepareService = new DataBasePrepareService();
    	dataBasePrepareService.clearDataBaseEntries();
    }
    
	@Test 
	public void testSaveVehicle() {
	   Ticket ticketToSave = new Ticket();
	   ticketToSave.setInTime(new Date());
	   ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, true);
	   ticketToSave.setParkingSpot(parkingSpot);
	   ticketToSave.setVehicleRegNumber("ABCDEFXYP");
	   ticketToSave.setPrice(20);
	   ticketToSave.setOutTime(new Date());
	   
	   ticketdao.saveTicket(ticketToSave);
	   
	   Ticket ticketReturned = ticketdao.getTicket("ABCDEFXYP");
	   assertEquals(ticketToSave.getPrice(), ticketReturned.getPrice());
	   assertEquals(ticketToSave.getVehicleRegNumber(), ticketReturned.getVehicleRegNumber());
	   assertEquals(ticketToSave.getParkingSpot().getId(), ticketReturned.getParkingSpot().getId());
	   
	}
	
	@Test
	public void testFailedSaved() {
		Ticket ticketToSave = new Ticket();
		   ticketToSave.setInTime(new Date());
		   ticketToSave.setParkingSpot(null);
		   ticketToSave.setVehicleRegNumber("ABCDEFXYP");
		   ticketToSave.setPrice(20);
		   ticketToSave.setOutTime(new Date());
		   
		   assertFalse(ticketdao.saveTicket(ticketToSave));
	}
	
	@Test
	public void testUpdatTicket() {
		  Ticket ticketToUpdate = new Ticket();
		  ticketToUpdate.setInTime(new Date());
		  ticketToUpdate.setParkingSpot(null);
		  ticketToUpdate.setVehicleRegNumber("ABCDEFXYP");
		  ticketToUpdate.setPrice(20);
		  ticketToUpdate.setOutTime(new Date());
		   
		   ticketdao.updateTicket(ticketToUpdate);
		   assertTrue(ticketdao.updateTicket(ticketToUpdate)); 
	}
	
	@Test
	public void testFailedUpdatTicket() {
		  Ticket ticketToUpdate = new Ticket();
		  ticketToUpdate.setInTime(new Date());
		  ticketToUpdate.setParkingSpot(null);
		  ticketToUpdate.setVehicleRegNumber(null);
		  ticketToUpdate.setPrice(0);
		  ticketToUpdate.setOutTime(new Date());
		   
		   ticketdao.updateTicket(ticketToUpdate);
		   assertThrows(Exception.class, () -> ticketdao.updateTicket(ticketToUpdate));
		 
	}
	
	@Test
	public void testNombreTicket() {
		//Arrange
		String vehicleReg = "AAA";
		int nbre = 0;
		//ACT
		int newNbre = ticketdao.getNbTicket(vehicleReg);
		
		//Assert
		assertEquals(nbre, newNbre);
		
	}

}
