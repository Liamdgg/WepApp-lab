package util;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordTest {
    public static void main(String[] args) {
        // Replace this with the hash from your database
        String hashedPasswordFromDB = "$2a$10$MFSpuyjYlQ0GZ1GQUd1T2.PezcFf2lmc0AjAukFkB0Q9FHlFb3ifC";

        // Plain password to test
        String plainPassword = "password123";

        boolean matches = BCrypt.checkpw(plainPassword, hashedPasswordFromDB);

        System.out.println("Password verification result: " + matches);
    }
}
