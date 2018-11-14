
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import Exceptions.UserAlreadyExists;
import org.junit.jupiter.api.Test;

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
        assertEquals(i.getUsers().size(),2);
    }

}
