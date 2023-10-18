package lk.ise.pos.dao.custom.Impl;

import lk.ise.pos.dao.CrudUtil;
import lk.ise.pos.dao.custom.UserDao;
import lk.ise.pos.dao.util.PasswordConfig;
import lk.ise.pos.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static lk.ise.pos.dao.CrudUtil.execute;

public class UserDaoImpl implements UserDao {
    @Override
    public boolean save(User user) throws Exception {
        return false;
    }

    @Override
    public boolean update(User user) throws Exception {
        return false;
    }

    @Override
    public User find(String username) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM user WHERE username=?", username);

        if (rst.next()){
            return new User(rst.getString(1),rst.getString(2));
        }
        return null;
    }

    @Override
    public boolean delete(String s) throws Exception {
        return false;
    }

    @Override
    public List<User> findAll() throws Exception {
        return null;
    }

    @Override
    public void initializeUsers() throws SQLException, ClassNotFoundException {
        ResultSet countSet = CrudUtil.execute("SELECT COUNT(*) FROM user");
        if (countSet.next()){
            int count = countSet.getInt(1);
            if (count==0){
                User user1 = new User("linda",new PasswordConfig().encrypt("1234"));
                execute("INSERT INTO user VALUES(?,?)",user1.getUsername(),user1.getPassword());

                User user2 = new User("anna",new PasswordConfig().encrypt("1234"));
                execute("INSERT INTO user VALUES(?,?)",user2.getUsername(),user2.getPassword());

                User user3 = new User("tom",new PasswordConfig().encrypt("1234"));
                execute("INSERT INTO user VALUES(?,?)",user3.getUsername(),user3.getPassword());
            }
        }
    }
}
