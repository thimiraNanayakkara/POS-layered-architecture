package lk.ise.pos.dao;

import lk.ise.pos.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public class CrudUtil {
    public static <T> T  execute(String sql,Object...params) throws SQLException, ClassNotFoundException {

        PreparedStatement prst = DBConnection.getInstance().getConnection().prepareStatement(sql);

        for (int i=0;i<params.length;i++){
            prst.setObject((i+1),params[i]);
        }

        if (sql.startsWith("SELECT")){
            return (T) prst.executeQuery();
        }

        return (T)(Boolean)(prst.executeUpdate()>0);

    }
}

