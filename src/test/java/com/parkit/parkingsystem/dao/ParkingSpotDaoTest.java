package com.parkit.parkingsystem.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;

class ParkingSpotDaoTest {

	private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
	private static TicketDAO ticketdao;
    private static ParkingSpotDAO parkingSpotdao;
    private static DataBasePrepareService dataBasePrepareService;
    
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		ticketdao = new TicketDAO();
    	parkingSpotdao = new ParkingSpotDAO();
    	ticketdao.dataBaseConfig = dataBaseTestConfig;
    	parkingSpotdao.dataBaseConfig = dataBaseTestConfig;
    	
    	dataBasePrepareService = new DataBasePrepareService();
    	dataBasePrepareService.clearDataBaseEntries();
	}

	@Test
	void testUdapteParking() {
		ParkingSpot parking = new ParkingSpot(1, ParkingType.CAR, false);
		parkingSpotdao.updateParking(parking);
		assertTrue(parkingSpotdao.updateParking(parking));
	}
	
	@Test
	void testFailedUdapteParking() {
		ParkingSpot parking = new ParkingSpot(0, null, false);
		//parkingSpotdao.updateParking(parking);
		assertFalse(parkingSpotdao.updateParking(parking));
	}
	
	@Test
	void testGetNextAvailableSlot() {
		ParkingSpot parking = new ParkingSpot(1, ParkingType.CAR, true);
		
		ParkingSpot parkingDis = new ParkingSpot(1, ParkingType.CAR, true);
		
		assertEquals(parking, parkingDis);
		
		
	}
	

}
