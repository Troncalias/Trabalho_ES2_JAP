
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
    public void testId_AC1() throws UserAlreadyExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(1, "nome", 1);
        List<User> list = i.getUsers();
        list.get(0).setCredit(10);
        i.addCredit(1, 20);
        i.addCredit(1, 30);
        assertEquals(60, list.get(0).getCredit());
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
    @Test
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
        i.addBicycle(1, 1, 1);
        i.registerUser(1, "nome", 1);
        i.addCredit(1, 20);
        assertEquals(1, i.getBicycle(1, 1, 1));
    }

    /**
     * TESTE F_RU1
     *
     * Testa se não adiciona user com id -1
     *
     * @throws UserAlreadyExists Lança exceção quando se tenta criar um user que já existe
     */
    @Test
    public void testId_F_RU1() throws UserAlreadyExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(-1,"nome", 2);
        assertEquals(0, i.getUsers().size());
    }

    /**
     * TESTE F_RU2
     *
     * Testa se adiciona user com id 0
     *
     * @throws UserAlreadyExists Lança exceção quando se tenta criar um user que já existe
     */
    @Test
    public void testId_F_RU2() throws UserAlreadyExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(0,"nome", 2);
        assertEquals(1, i.getUsers().size());
    }

    /**
     * TESTE F_RU3
     *
     * Testa se adiciona user com id 1
     *
     * @throws UserAlreadyExists Lança exceção quando se tenta criar um user que já existe
     */
    @Test
    public void testId_F_RU3() throws UserAlreadyExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(1,"nome", 2);
        assertEquals(1, i.getUsers().size());
    }

    /**
     * TESTE F_RU4
     *
     * Testa se adiciona user com name null
     *
     * @throws UserAlreadyExists Lança exceção quando se tenta criar um user que já existe
     */
    @Test
    public void testId_F_RU4() throws UserAlreadyExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(1,null, 2);
        assertEquals(0, i.getUsers().size());
    }

    /**
     * TESTE F_RU5
     *
     * Testa se adiciona user com name "nome"
     *
     * @throws UserAlreadyExists Lança exceção quando se tenta criar um user que já existe
     */
    @Test
    public void testId_F_RU5() throws UserAlreadyExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(1,"nome", 2);
        assertEquals(1, i.getUsers().size());
    }

    /**
     * TESTE F_RU6
     *
     * Testa se adiciona user com rentalProgram 0
     *
     * @throws UserAlreadyExists Lança exceção quando se tenta criar um user que já existe
     */
    @Test
    public void testId_F_RU6() throws UserAlreadyExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(1,"nome", 0);
        assertEquals(0, i.getUsers().size());
    }

    /**
     * TESTE F_RU7
     *
     * Testa se adiciona user com rentalProgram 1
     *
     * @throws UserAlreadyExists Lança exceção quando se tenta criar um user que já existe
     */
    @Test
    public void testId_F_RU7() throws UserAlreadyExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(1,"nome", 1);
        assertEquals(1, i.getUsers().size());
    }

    /**
     * TESTE F_RU8
     *
     * Testa se adiciona user com rentalProgram 2
     *
     * @throws UserAlreadyExists Lança exceção quando se tenta criar um user que já existe
     */
    @Test
    public void testId_F_RU8() throws UserAlreadyExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(1,"nome", 2);
        assertEquals(1, i.getUsers().size());
    }

    /**
     * TESTE F_RU9
     *
     * Testa se adiciona user com rentalProgram 3
     *
     * @throws UserAlreadyExists Lança exceção quando se tenta criar um user que já existe
     */
    @Test
    public void testId_F_RU9() throws UserAlreadyExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(1, "nome", 3);
        assertEquals(0, i.getUsers().size());
    }

    /**
     * TESTE F_BRF1
     *
     * Testa se o rentalProgram é permitido com o valor -1
     *
     */
    @Test
    public void testID_F_BRF1(){
        BikeRentalSystem i = new BikeRentalSystem(10);
        int rentalvalue = i.bicycleRentalFee(-1, 10, 30, 4);
        assertEquals(rentalvalue, 0);
    }

    /**
     * TESTE F_BRF2
     *
     * Testa se o rentalProgram é permitido com o valor 0
     *
     */
    @Test
    public void testID_F_BRF2(){
        BikeRentalSystem i = new BikeRentalSystem(10);
        int rentalvalue = i.bicycleRentalFee(0, 10, 30, 4);
        assertEquals(rentalvalue, 0);
    }

    /**
     * TESTE F_BRF3
     *
     * Testa se o rentalProgram é permitido com o valor 3
     *
     */
    @Test
    public void testID_F_BRF3(){
        BikeRentalSystem i = new BikeRentalSystem(10);
        int rentalvalue = i.bicycleRentalFee(3, 10, 30, 4);
        assertEquals(rentalvalue, 0);
    }

    /**
     * TESTE F_BRF4
     *
     * Testa se o startime é permitido com o valor -1
     *
     */
    @Test
    public void testID_F_BRF4(){
        BikeRentalSystem i = new BikeRentalSystem(10);
        int rentalvalue = i.bicycleRentalFee(1, -1, 30, 4);
        assertEquals(rentalvalue, 0);
    }

    /**
     * TESTE F_BRF5
     *
     * Testa se o startime é permitido com o valor 0
     *
     */
    @Test
    public void testID_F_BRF5(){
        BikeRentalSystem i = new BikeRentalSystem(10);
        int rentalvalue = i.bicycleRentalFee(1, 0, 30, 4);
        assertEquals(rentalvalue, (30 - 0) * 10);
    }

    /**
     * TESTE F_BRF6
     *
     * Testa se o endtime é permitido com o valor -1
     *
     */
    @Test
    public void testID_F_BRF6(){
        BikeRentalSystem i = new BikeRentalSystem(10);
        int rentalvalue = i.bicycleRentalFee(1, 10, -1, 4);
        assertEquals(rentalvalue, 0);
    }

    /**
     * TESTE F_BRF7
     *
     * Testa se o endtime é permitido com o valor 0
     *
     */
    @Test
    public void testID_F_BRF7(){
        BikeRentalSystem i = new BikeRentalSystem(10);
        int rentalvalue = i.bicycleRentalFee(1, 0, 0, 4);
        assertEquals(rentalvalue, 0);
    }

    /**
     * TESTE F_BRF8
     *
     * Testa se é permitido que o startime seja maior que o endtime
     *
     */
    @Test
    public void testID_F_BRF8(){
        BikeRentalSystem i = new BikeRentalSystem(10);
        int rentalvalue = i.bicycleRentalFee(1, 30, 10, 4);
        assertEquals(rentalvalue, 0);
    }

    /**
     * TESTE F_BRF9
     *
     * Testa se é permitido que o startime seja igual ao endtime
     *
     */
    @Test
    public void testID_F_BRF9(){
        BikeRentalSystem i = new BikeRentalSystem(10);
        int rentalvalue = i.bicycleRentalFee(1, 10, 10, 4);
        assertEquals(rentalvalue, (10 - 10) * 10);
    }

    /**
     * TESTE F_BRF10
     *
     * Testa se é permitido que o startime seja menor ao endtime
     *
     */
    @Test
    public void testID_F_BRF10(){
        BikeRentalSystem i = new BikeRentalSystem(10);
        int rentalvalue = i.bicycleRentalFee(1, 10, 30, 4);
        assertEquals(rentalvalue, (30 - 10) * 10);
    }

    /**
     * TESTE F_BRF11
     *
     * Testa se o nRentals é permitido com o valor -1
     *
     */
    @Test
    public void testID_F_BRF11(){
        BikeRentalSystem i = new BikeRentalSystem(10);
        int rentalvalue = i.bicycleRentalFee(1, 10, 30, -1);
        assertEquals(rentalvalue, 0);
    }

    /**
     * TESTE F_BRF12
     *
     * Testa se o nRentals é permitido com o valor 0
     *
     */
    @Test
    public void testID_F_BRF12(){
        BikeRentalSystem i = new BikeRentalSystem(10);
        int rentalvalue = i.bicycleRentalFee(1, 10, 30, 0);
        assertEquals(rentalvalue, (30 - 10) * 10);
    }

    /**
     * TESTE F_GB1
     *
     * Testa se não executa com um user com id -1
     *
     * @throws UserAlreadyExists
     * @throws UserDoesNotExists
     */
    @Test
    public void testId_F_GB1() throws UserAlreadyExists, UserDoesNotExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.addBicycle(1,1,1);
        i.registerUser(-1,"nome",1);
        i.addCredit(-1,20);
        assertEquals(-1, i.getBicycle(1,-1,1));
    }

    /**
     * TESTE F_GB2
     *
     * Testa se executa com um user com id 0
     *
     * @throws UserAlreadyExists
     * @throws UserDoesNotExists
     */
    @Test
    public void testId_F_GB2() throws UserAlreadyExists, UserDoesNotExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.addBicycle(1,1,1);
        i.registerUser(0,"nome",1);
        i.addCredit(0,20);
        assertEquals(1, i.getBicycle(1,0,1));
    }

    /**
     * TESTE F_GB3
     *
     * Testa se executa com um user com id 1
     *
     * @throws UserAlreadyExists
     * @throws UserDoesNotExists
     */
    @Test
    public void testId_F_GB3() throws UserAlreadyExists, UserDoesNotExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.addBicycle(1,1,1);
        i.registerUser(1,"nome",1);
        i.addCredit(1,20);
        assertEquals(1, i.getBicycle(1,1,1));
    }

    /**
     * TESTE F_GB4
     *
     * Testa se não executa com um deposit com id -1
     *
     * @throws UserAlreadyExists
     * @throws UserDoesNotExists
     */
    @Test
    public void testId_F_GB4() throws UserAlreadyExists, UserDoesNotExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.addBicycle(-1,1,1);
        i.registerUser(1,"nome",1);
        i.addCredit(1,20);
        assertEquals(-1, i.getBicycle(-1,1,1));
    }

    /**
     * TESTE F_GB5
     *
     * Testa se executa com um deposit com id 0
     *
     * @throws UserAlreadyExists
     * @throws UserDoesNotExists
     */
    @Test
    public void testId_F_GB5() throws UserAlreadyExists, UserDoesNotExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.addBicycle(0,1,1);
        i.registerUser(1,"nome",1);
        i.addCredit(1,20);
        assertEquals(-1, i.getBicycle(0,1,1));
    }

    /**
     * TESTE F_GB6
     *
     * Testa se executa com um deposit com id 1
     *
     * @throws UserAlreadyExists
     * @throws UserDoesNotExists
     */
    @Test
    public void testId_F_GB6() throws UserAlreadyExists, UserDoesNotExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.addBicycle(1,1,1);
        i.registerUser(1,"nome",1);
        i.addCredit(1,20);
        assertEquals(1, i.getBicycle(1,1,1));
    }

    /**
     * TESTE F_GB7
     *
     * Testa se não executa com o startTime -1
     *
     * @throws UserAlreadyExists
     * @throws UserDoesNotExists
     */
    @Test
    public void testId_F_GB7() throws UserAlreadyExists, UserDoesNotExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.addBicycle(1,1,1);
        i.registerUser(1,"nome",1);
        i.addCredit(1,20);
        assertEquals(-1, i.getBicycle(1,1,-1));
    }

    /**
     * TESTE F_GB8
     *
     * Testa se executa com o startTime 0
     *
     * @throws UserAlreadyExists
     * @throws UserDoesNotExists
     */
    @Test
    public void testId_F_GB8() throws UserAlreadyExists, UserDoesNotExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.addBicycle(1,1,1);
        i.registerUser(1,"nome",1);
        i.addCredit(1,20);
        assertEquals(1, i.getBicycle(1,1,0));
    }

    /**
     * TESTE F_GB9
     *
     * Testa se executa com o startTime 1
     *
     * @throws UserAlreadyExists
     * @throws UserDoesNotExists
     */
    @Test
    public void testId_F_GB9() throws UserAlreadyExists, UserDoesNotExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.addBicycle(1,1,1);
        i.registerUser(1,"nome",1);
        i.addCredit(1,20);
        assertEquals(1, i.getBicycle(1,1,1));
    }

    /**
     * TESTE F_AC1
     *
     * Testa se adiciona credit a um user com id -1
     *
     * @throws UserAlreadyExists Lança exceção quando se tenta criar um user que já existe
     */
    @Test
    public void testId_F_AC1() throws UserAlreadyExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(-1, "nome", 1);
        List<User> list = i.getUsers();
        list.get(0).setCredit(10);
        i.addCredit(-1, 20);
        i.addCredit(-1, 30);
        assertEquals(0, list.get(0).getCredit());
    }

    /**
     * TESTE F_AC2
     *
     * Testa se adiciona credit a um user com id 0
     *
     * @throws UserAlreadyExists Lança exceção quando se tenta criar um user que já existe
     */
    @Test
    public void testId_F_AC2() throws UserAlreadyExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(0, "nome", 1);
        List<User> list = i.getUsers();
        list.get(0).setCredit(10);
        i.addCredit(0, 20);
        i.addCredit(0, 30);
        assertEquals(60, list.get(0).getCredit());
    }

    /**
     * TESTE F_AC3
     *
     * Testa se adiciona credit a um user com id 1
     *
     * @throws UserAlreadyExists Lança exceção quando se tenta criar um user que já existe
     */
    @Test
    public void testId_F_AC3() throws UserAlreadyExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(1, "nome", 1);
        List<User> list = i.getUsers();
        list.get(0).setCredit(10);
        i.addCredit(1, 20);
        i.addCredit(1, 30);
        assertEquals(60, list.get(0).getCredit());
    }

    /**
     * TESTE F_AC4
     *
     * Testa se adiciona credit com amount -1
     *
     * @throws UserAlreadyExists Lança exceção quando se tenta criar um user que já existe
     */
    @Test
    public void testId_F_AC4() throws UserAlreadyExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(1, "nome", 1);
        List<User> list = i.getUsers();
        list.get(0).setCredit(10);
        i.addCredit(1, -1);
        assertEquals(10, list.get(0).getCredit());
    }

    /**
     * TESTE F_AC5
     *
     * Testa se adiciona credit com amount 0
     *
     * @throws UserAlreadyExists Lança exceção quando se tenta criar um user que já existe
     */
    @Test
    public void testId_F_AC5() throws UserAlreadyExists {
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(1, "nome", 1);
        List<User> list = i.getUsers();
        list.get(0).setCredit(10);
        i.addCredit(1, 0);
        assertEquals(10, list.get(0).getCredit());
    }

    /**
     * TESTE F_AC6
     *
     * Testa se adiciona credit com amount 1
     *
     * @throws UserAlreadyExists Lança exceção quando se tenta criar um user que já existe
     */
    @Test
    public void testId_F_AC6() throws UserAlreadyExists { //ver se adicionar credit com amount 1 (passou)
        BikeRentalSystem i = new BikeRentalSystem(10);
        i.registerUser(1, "nome", 1);
        List<User> list = i.getUsers();
        list.get(0).setCredit(10);
        i.addCredit(1, 1);
        assertEquals(11, list.get(0).getCredit());
    }
}
