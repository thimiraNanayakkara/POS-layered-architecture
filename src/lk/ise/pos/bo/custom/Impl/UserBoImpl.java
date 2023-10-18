package lk.ise.pos.bo.custom.Impl;

import lk.ise.pos.bo.custom.UserBo;
import lk.ise.pos.dao.DaoFactory;
import lk.ise.pos.dao.custom.Impl.UserDaoImpl;
import lk.ise.pos.dao.custom.UserDao;
import lk.ise.pos.dto.UserDto;
import lk.ise.pos.entity.User;
import lk.ise.pos.enums.DaoType;

import java.sql.SQLException;

public class UserBoImpl implements UserBo {
    UserDao userDao = DaoFactory.getInstance().getDao(DaoType.USER);
    @Override
    public void initializeUsers() throws SQLException, ClassNotFoundException {
            userDao.initializeUsers();
    }

    @Override
    public UserDto findUser(String username) throws Exception {
        User user= userDao.find(username);
        if (user!=null){
            return new UserDto(user.getUsername(),user.getPassword());
        }
        return null;
    }
}
