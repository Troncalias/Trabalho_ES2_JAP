
import Exceptions.UserAlreadyExists;
import Exceptions.UserDoesNotExists;
import Models.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes da classe BikeSharing
 */
public class BikeSharingTest {


    /**
     * TESTE ID RU1
     * Testa se é lançada uma excepção ao criar um utilizador que já exista
     */
    @Test
    public void testId_RU1(){
        assertThrows(UserAlreadyExists.class,
                ()->{
                    BikeRentalSystem i = new BikeRentalSystem(10);
                    i.registerUser(0,"nome", 2);
                    i.registerUser(0,"nome", 2);
                });
    }

    /**
     * TESTE RU2
     *
     * Testa se um utilizador é adicionado
     *
     * @throws UserAlreadyExists Lança exceção quando se tenta criar um user que já existe
     */
    @Test
    public void testId_RU2() throws UserAlreadyExists { //register user add
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(0,"nome", 2);
        i.registerUser(1,"nome", 2);
        assertEquals(2, i.getUsers().size());
    }


    /**
     * TESTE CA1
     *
     * Testa se o crédito do utilizador é somado ao adicionar o crédito
     */
    @Test
    public void testId_CA1() throws UserAlreadyExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(1, "nome", 1);
        i.addCredit(1, 20);
        i.addCredit(1, 30);
        List<User> list = i.getUsers();
        assertEquals(50, list.get(0).getCredit());

    }

    /**
     * TESTE VC1
     *
     * @throws UserAlreadyExists Lança exceção quando se tenta criar um user que já existe
     */
    @Test
    public void testId_VC1() throws UserAlreadyExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(1, "nome", 1);
        assertFalse(i.verifyCredit(1));
    }

    /**
     * Teste VC2
     *
     * @throws UserAlreadyExists Lança exceção quando se tenta criar um user que já existe
     */
    @Test
    public void testId_VC2() throws UserAlreadyExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        assertFalse(i.verifyCredit(1));
    }

    /**
     * TESTE VC3
     *
     * @throws UserAlreadyExists Lança exceção quando se tenta criar um user que já existe
     */
    @Test
    public void testId_VC3() throws UserAlreadyExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(1, "nome", 1);
        i.addCredit(1, 500);
        assertTrue(i.verifyCredit(1));
    }

    /**
     * TESTE BRF1
     *
     * Testa se o valor de aluguer devolvido corresponde ao desejado com rental program = 1
     */
    @Test
    public void testId_BRF1(){
        BikeRentalSystem i = new BikeRentalSystem(10);
        int rentalvalue = i.bicycleRentalFee(1, 10, 30, 4);
        assertEquals(rentalvalue, (30 - 10) * 10);
    }

    /**
     * TESTE BRF2
     *
     * Testa se o valor devolvido corresponde ao desejado com rental program = 2,
     * nRentals % 10 != 0, e (endtime - startime) <= 10
     */
    @Test
    public void testId_BRF2(){
        BikeRentalSystem i = new BikeRentalSystem(30);
        int rentalvalue = i.bicycleRentalFee(2, 10, 15, 5);
        assertEquals(rentalvalue, 30 * (15 - 10));
    }

    /**
     * Teste BRF3
     *
     * Testa se o valor devolvido corresponde ao desejado com rental program = 2,
     * nRentals % 10 != 0, e (endtime - startime) > 10
     */
    @Test
    public void testId_BRF3(){
        BikeRentalSystem i = new BikeRentalSystem(30);
        int rentalvalue = i.bicycleRentalFee(2, 10, 35, 5);
        int valorCaculo = 10*30 + ((35-10)-10) * 30/2;
        assertEquals(rentalvalue, valorCaculo);
    }

    /**
     * TESTE BRF4
     *
     * Testa se o valor devolvido corresponde ao desejado com rental program = 2 e nRentals % 10 = 0
     */
    @Test
    public void testId_BRF4(){
        BikeRentalSystem i = new BikeRentalSystem(10);
        int rentalvalue = i.bicycleRentalFee(2, 10, 30, 10);
        assertEquals(rentalvalue, 0);
    }

    /**
     * TESTE RB1
     *
     * Testa se retorna -1 quando iduser, iddeposit não existem
     */
    @Test
    public void testId_RB1(){ //todos não existem
        BikeRentalSystem i = new BikeRentalSystem(10);
        int retorna = i.returnBicycle(1, 1, 20);
        assertEquals(retorna, -1);
    }

    /**
     * TESTE RB2
     *
     * Testa se retorna -1 quando iduser não existe e iddeposit existe
     *
     */
    @Test
    public void testId_RB2(){ //id user nao existe
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.addBicycle(1,1,1);
        int retorna = i.returnBicycle(1, 1, 20);
        assertEquals(retorna, -1);
    }

    /**
     * TESTE RB3
     *
     * Testa se retorna -1 quando iddeposit não existe e iduser existe
     * @throws UserAlreadyExists Lança exceção quando se tenta criar um user que já existe
     */
    @Test
    public void testId_RB3() throws UserAlreadyExists { //id deposito nao existe
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(1, "nome", 1);
        int retorna = i.returnBicycle(1, 1, 20);
        assertEquals(retorna, -1);
    }

    /**
     * TESTE RB4
     *
     * Testa se retorna -1 quando iduser e iddeposit existe mas bicicleta não está associada
     * @throws UserAlreadyExists Lança exceção quando se tenta criar um user que já existe
     */
    @Test
    public void testId_RB4() throws UserAlreadyExists { //bike nao associada
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(1, "nome", 1);
        i.addBicycle(1,1,1);
        int retorna = i.returnBicycle(1, 1, 20);
        assertEquals(retorna, -1);
    }

    /**
     * TESTE RB5
     *
     * Testa se retorna -1 quando iduser e iddeposit existe, bicicleta está associada,
     * mas não existe lugar livre
     *
     * @throws UserAlreadyExists Lança exceção quando se tenta criar um user que já existe
     * @throws UserDoesNotExists Lança exceção quando user não existe
     */
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

    /**
     * TESTE RB6
     *
     * Testa se o crédito atual do user é igual ao (crédito inicial - valor a pagar) quando
     * iduser e iddeposit existe, bicicleta está associada e existe lugar livre
     *
     * @throws UserAlreadyExists Lança exceção quando se tenta criar um user que já existe
     * @throws UserDoesNotExists Lança exceção quando user não existe
     */
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

    /**
     * TESTE GB1
     *
     * IdUser não registado, espera erro
     */
    @Test
    public void testId_GB1(){
        assertThrows(UserDoesNotExists.class,
                ()->{
                    BikeRentalSystem i = new BikeRentalSystem(10);
                    i.addBicycle(1,1,1);
                    i.getBicycle(1,1,1);
                });
    }

    /**
     * TESTE GB2
     *
     * Deposito não existe, e espera -1
     *
     * @throws UserAlreadyExists Lança exceção quando se tenta criar um user que já existe
     * @throws UserDoesNotExists  Lança exceção quando user não existe
     */
    @Test
    public void testId_GB2() throws UserAlreadyExists, UserDoesNotExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.addBicycle(1,1,1);
        i.registerUser(1,"nome",1);
        i.addCredit(1,20);
        assertEquals(-1, i.getBicycle(2,1,1));
    }

    /**
     * TESTE GB3
     *
     * Falta de credito, e espera -1
     *
     * @throws UserAlreadyExists Lança exceção quando se tenta criar um user que já existe
     * @throws UserDoesNotExists Lança exceção quando user não existe
     */
    @Test //Falta de credito, e espera -1
    public void testId_GB3() throws UserAlreadyExists, UserDoesNotExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.addBicycle(1,1,1);
        i.registerUser(1,"nome",1);
        assertEquals(-1, i.getBicycle(1,1,1));
    }
    /**
     * TESTE GB4
     *
     * Testa que o mesmo utilizador não pode alugar 2 bikes ao mesmo tempo, retorna -1 se acontecer
     *
     * @throws UserAlreadyExists Lança exceção quando se tenta criar um user que já existe
     * @throws UserDoesNotExists Lança exceção quando user não existe
     */
    @Test
    public void testId_GB4() throws UserAlreadyExists, UserDoesNotExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.addBicycle(1,1,1);
        i.addBicycle(1,2,2);
        i.registerUser(1,"nome",1);
        i.addCredit(1, 30);
        i.getBicycle(1,1,1);
        assertEquals(-1, i.getBicycle(1,1,1));
    }

    /**
     * TESTE GB5
     *
     * Falta de bicicleta
     *
     * @throws UserAlreadyExists Lança exceção quando se tenta criar um user que já existe
     * @throws UserDoesNotExists Lança exceção quando user não existe
     *
     */
    @Test
    public void testId_GB5() throws UserAlreadyExists, UserDoesNotExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(1,"nome",1);
        i.addCredit(1,20);
        assertEquals(-1, i.getBicycle(1,1,1));
    }

    /**
     * TESTE GB6
     *
     * Bicicleta associada ao cliente e retorna 1
     *
     * @throws UserAlreadyExists Lança exceção quando se tenta criar um user que já existe
     * @throws UserDoesNotExists Lança exceção quando user não existe
     */
    @Test
    public void testId_GB6() throws UserAlreadyExists, UserDoesNotExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.addBicycle(1,1,1);
        i.registerUser(1,"nome",1);
        i.addCredit(1,20);
        assertEquals(1, i.getBicycle(1,1,1));
    }
}
