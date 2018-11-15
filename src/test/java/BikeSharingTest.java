
import Exceptions.UserAlreadyExists;
import Exceptions.UserDoesNotExists;
import Models.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BikeSharingTest {

    @Test
    public void testId_RU1(){
        assertThrows(UserAlreadyExists.class,
                ()->{
                    BikeRentalSystem i = new BikeRentalSystem(10);
                    i.registerUser(0,"nome", 2);
                    i.registerUser(0,"nome", 2);
                });
    }

    @Test
    public void testId_RU2() throws UserAlreadyExists { //register user add
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(0,"nome", 2);
        i.registerUser(1,"nome", 2);
        assertEquals(2, i.getUsers().size());
    }

    @Test
    public void testId_CA1() throws UserAlreadyExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(1, "nome", 1);
        i.addCredit(1, 20);
        i.addCredit(1, 30);
        List<User> list = i.getUsers();
        assertEquals(50, list.get(0).getCredit());

    }

    @Test
    public void testId_VC1() throws UserAlreadyExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(1, "nome", 1);
        assertFalse(i.verifyCredit(1));
    }

    @Test
    public void testId_VC2() throws UserAlreadyExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(1, "nome", 1);
        i.addCredit(1, 500);
        assertTrue(i.verifyCredit(1));
    }
    @Test
    public void testId_BRF1(){
        BikeRentalSystem i = new BikeRentalSystem(10);
        int rentalvalue = i.bicycleRentalFee(1, 10, 30, 4);
        assertEquals(rentalvalue, (30 - 10) * 10);
    }


    @Test
    public void testId_BRF2(){
        BikeRentalSystem i = new BikeRentalSystem(30);
        int rentalvalue = i.bicycleRentalFee(2, 10, 15, 5);
        assertEquals(rentalvalue, 30 * (15 - 10));
    }

    @Test
    public void testId_BRF3(){
        BikeRentalSystem i = new BikeRentalSystem(30);
        int rentalvalue = i.bicycleRentalFee(2, 10, 35, 5);
        int valorCaculo = 10*30 + ((35-10)-10) * 30/2;
        assertEquals(rentalvalue, valorCaculo);
    }

    @Test
    public void testId_BRF4(){
        BikeRentalSystem i = new BikeRentalSystem(10);
        int rentalvalue = i.bicycleRentalFee(2, 10, 30, 10);
        assertEquals(rentalvalue, 0);
    }

    @Test
    public void testId_RB1(){ //todos não existem
        BikeRentalSystem i = new BikeRentalSystem(10);
        int retorna = i.returnBicycle(1, 1, 20);
        assertEquals(retorna, -1);
    }

    @Test
    public void testId_RB2(){ //id user nao existe
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.addBicycle(1,1,1);
        int retorna = i.returnBicycle(1, 1, 20);
        assertEquals(retorna, -1);
    }

    @Test
    public void testId_RB3() throws UserAlreadyExists { //id deposito nao existe
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(1, "nome", 1);
        int retorna = i.returnBicycle(1, 1, 20);
        assertEquals(retorna, -1);
    }

    @Test
    public void testId_RB4() throws UserAlreadyExists { //bike nao associada
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(1, "nome", 1);
        i.addBicycle(1,1,1);
        int retorna = i.returnBicycle(1, 1, 20);
        assertEquals(retorna, -1);
    }

    @Test
    public void testId_RB5() throws UserAlreadyExists, UserDoesNotExists { //User, deposito e bike associada existem e lugar não livre
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(1, "nome", 1);
        i.addCredit(1, 500);
        i.addBicycle(1,1,1);
        i.getBicycle(1,1, 10);
        i.addBicycle(1,1,2);
        int retorna = i.returnBicycle(1, 1, 20);
        assertEquals(retorna, -1);
    }

    @Test
    public void testId_RB6() throws UserAlreadyExists, UserDoesNotExists { //User, deposito e bike associada existem e lugar livre
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(1, "nome", 1);
        i.addCredit(1, 500);
        i.addBicycle(1,1,1);
        i.getBicycle(1,1, 10);
        i.returnBicycle(1, 1, 20);
        List<User> list = i.getUsers();
        assertEquals(list.get(0).getCredit(), 500 - i.bicycleRentalFee(1,10,20,5));
    }

}
