
import Exceptions.UserAlreadyExists;
import Models.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BikeSharingTest {

    @Test
    public void registerUser_AlreadyExist(){
        assertThrows(UserAlreadyExists.class,
                ()->{
                    BikeRentalSystem i = new BikeRentalSystem(10);
                    i.registerUser(0,"nome", 2);
                    i.registerUser(0,"nome", 2);
                });
    }

    @Test
    public void registerUser_Add() throws UserAlreadyExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(0,"nome", 2);
        i.registerUser(1,"nome", 2);
        assertEquals(2, i.getUsers().size());
    }

    @Test
    public void credit_add() throws UserAlreadyExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(1, "nome", 1);
        i.addCredit(1, 20);
        i.addCredit(1, 30);
        List<User> list = i.getUsers();
        assertEquals(50, list.get(0).getCredit());

    }

    @Test
    public void verifycreditzero() throws UserAlreadyExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(1, "nome", 1);
        assertFalse(i.verifyCredit(1));
    }

    @Test
    public void verifycreditposivite() throws UserAlreadyExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(1, "nome", 1);
        i.addCredit(1, 500);
        assertTrue(i.verifyCredit(1));
    }
}
