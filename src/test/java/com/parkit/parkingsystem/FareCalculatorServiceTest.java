package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

@Tag("FareCalutorserviceTest")
@DisplayName("Test de la classe FareCalculatorService")
public class FareCalculatorServiceTest {

    private static FareCalculatorService fareCalculatorService;
    private Ticket ticket;

    @BeforeAll
    private static void setUp() {
        fareCalculatorService = new FareCalculatorService();
    }

    @BeforeEach
    private void setUpPerTest() {
        ticket = new Ticket();
    }

    
    @Test
    @DisplayName("Test sur le calcul du prix lorsque la voiture à fait une heure dans le parking")
    public void calculateFareCar(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals(ticket.getPrice(), 1.5);
    }

  
    @Test
    @DisplayName("Test sur le calcul du prix lorsque la moto à fait une heure dans le parking")
    public void calculateFareBike(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals(ticket.getPrice(), 1.0);
    }

  
    @Test
    @DisplayName("Test sur le calcul du prix lorsque la nature du vehicule est inconnu ")
    public void calculateFareUnkownType(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, null,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket));
    }

  
    @Test
    @DisplayName("Test sur le calcul du prix lorsque la moto à doit sortir après une heure dans le parking")
    public void calculateFareBikeWithFutureInTime(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() + (  60 * 60 * 1000) );
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket));
    }

  
    @Test
    @DisplayName("Test sur le calcul du prix lorsque la voiture à fait 45 minunites dans le parking")
    public void calculateFareBikeWithLessThanOneHourParkingTime(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  45 * 60 * 1000) );//45 minutes parking time should give 3/4th parking fare
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals((0.75 * 1.0), ticket.getPrice() );
    }

  
    @Test
    @DisplayName("Test sur le calcul du prix lorsque la moto à fait 45 minunites dans le parking")
    public void calculateFareCarWithLessThanOneHourParkingTime(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  45 * 60 * 1000) );//45 minutes parking time should give 3/4th parking fare
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals( (0.75 * 1.5) , ticket.getPrice());
    }

  
    @Test
    @DisplayName("Test sur le calcul du prix lorsque la voiture à fait 24H minunites dans le parking")
    public void calculateFareCarWithMoreThanADayParkingTime(){
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  24 * 60 * 60 * 1000) );//24 hours parking time should give 24 * parking fare per hour
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket);
        assertEquals( (24 * 1.5) , ticket.getPrice());
    }
    
    //----------------------------------------------------------------------------
    @Nested
    @Tag("Vehicle ayant fait moins de 30 minutes au parking ")
    @DisplayName("Réussir à attribuer un ticket gratuit aux véhicles qui font moins de 30 minutes au parking")
    public class TestGratuitéParking{
	  @Test
	    @DisplayName("Test sur le calcul du prix lorsque la voiture à fait 29 minunites dans le parking")
	    public void calculateFareCarWithLessThan30minutesParkingTime(){
	        Date inTime = new Date();
	        inTime.setTime( System.currentTimeMillis() - (  29 * 60 * 1000) );//25 minutes parking time should give 3/4th parking fare
	        Date outTime = new Date();
	        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);

	        ticket.setInTime(inTime);
	        ticket.setOutTime(outTime);
	        ticket.setParkingSpot(parkingSpot);
	        fareCalculatorService.calculateFare(ticket);
	        assertEquals(0  , ticket.getPrice());
	    }
	    
	  
	    @Test
	    @DisplayName("Test sur le calcul du prix lorsque la moto à fait 29 minunites dans le parking")
	    public void calculateFareBikeWithLessThan30minutesParkingTime(){
	        Date inTime = new Date();
	        inTime.setTime( System.currentTimeMillis() - (  29 * 60 * 1000) );//25 minutes parking time should give 3/4th parking fare
	        Date outTime = new Date();
	        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);

	        ticket.setInTime(inTime);
	        ticket.setOutTime(outTime);
	        ticket.setParkingSpot(parkingSpot);
	        fareCalculatorService.calculateFare(ticket);
	        assertEquals(0 , ticket.getPrice());
	    }
    }

   @Nested
   @Tag("Vehicule reccurent avec rabais")
   @DisplayName("Réussir à attribuer un rabais à un véhicule reccurent")
   public class TestVehicleReccurent {
	   
	   @Test
	    @DisplayName("Test sur le calcul du prix lorsque la voiture à fait 45 minunites dans le parking et possède un rabais (voiture reccurent)")
	    public void calculateFareCarWithDiscount(){
	        Date inTime = new Date();
	        inTime.setTime( System.currentTimeMillis() - (45 * 60 * 1000) );
	        Date outTime = new Date();
	        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
	        
	        ticket.setInTime(inTime);
	        ticket.setOutTime(outTime);
	        ticket.setParkingSpot(parkingSpot);
	        
	        fareCalculatorService.calculateFare(ticket, true);
	        assertEquals( (0.75 * 1.5)*0.95 , ticket.getPrice());
	    }
	    
	  
	   
	    @Test
	    @DisplayName("Test sur le calcul du prix lorsque la moto à fait 45 minunites dans le parking et possède un rabais (moto reccurent)")
	    public void calculateFareBikeWithDiscount(){
	        Date inTime = new Date();
	        inTime.setTime( System.currentTimeMillis() - (  45 * 60 * 1000) );//45 minutes parking time should give 3/4th parking fare
	        Date outTime = new Date();
	        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);
	        
	        ticket.setInTime(inTime);
	        ticket.setOutTime(outTime);
	        ticket.setParkingSpot(parkingSpot);
	       
	        fareCalculatorService.calculateFare(ticket, true);
	        assertEquals( (0.75 * 1.0)*0.95 , ticket.getPrice());
	    }
   }
  
    
}
