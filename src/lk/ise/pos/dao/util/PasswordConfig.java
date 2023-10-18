package lk.ise.pos.dao.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordConfig {
    public String encrypt(String rawpassword){
        //encrypt password
        return BCrypt.hashpw(rawpassword,BCrypt.gensalt());

    }
}
