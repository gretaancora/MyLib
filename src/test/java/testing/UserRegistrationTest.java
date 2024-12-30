package testing;

import it.uniroma2.dicii.ispw.mylib.controller.LoginController;
import it.uniroma2.dicii.ispw.mylib.controller.RegisterController;
import it.uniroma2.dicii.ispw.mylib.engineering.bean.LoginBean;
import it.uniroma2.dicii.ispw.mylib.engineering.bean.RegisterBean;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.EmailAlreadyInUseException;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.WrongCredentialsException;
import org.junit.Assert;
import org.junit.Test;

public class UserRegistrationTest {
    /**
     * Verifica che venga sollevata l'eccezione adeguata se si inseriscono credenziali esistenti
     */
    @Test
    public void testRegistrationWithExistingCredentials(){

        var registerBean = new RegisterBean("user1", "user1", "user1@gmail.com", "user1");

        var registerController = new RegisterController();
        Assert.assertThrows(
                EmailAlreadyInUseException.class,
                () -> registerController.registerCostumer(registerBean)
        );
    }

    /**
     * Verifica che venga sollevata l'eccezione adeguata se si inseriscono credenziali errate
     */
    @Test
    public void testLoggingWithWrongCredentials(){

        var loginBean = new LoginBean("user1@gmail.com", "user2");

        var loginController = new LoginController();
        Assert.assertThrows(
                WrongCredentialsException.class,
                () -> loginController.start(loginBean)
        );
    }

    /**
     * Verifica che non venga ammessa la registrazione dell'utente se non vengono forniti dati
     */
    @Test
    public void testRegistrationWithNullBean() throws EmailAlreadyInUseException, DAOException {
        var registerController = new RegisterController();
        Assert.assertFalse(registerController.registerCostumer(null));
    }

}
