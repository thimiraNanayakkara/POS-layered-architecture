package lk.ise.pos.dao.custom.Impl;

import lk.ise.pos.dao.CrudUtil;
import lk.ise.pos.dao.custom.OrderDao;
import lk.ise.pos.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    @Override
    public boolean save(Order order) throws Exception {
       return CrudUtil.execute("INSERT INTO orders VALUES(?,?,?,?)",
                order.getOrderId(), order.getCustomer(),order.getDate(),order.getTotal());
    }

    @Override
    public boolean update(Order order) throws Exception {
        return false;
    }

    @Override
    public Order find(String s) throws Exception {
        return null;
    }

    @Override
    public boolean delete(String s) throws Exception {
        return false;
    }

    @Override
    public List<Order> findAll() throws Exception {
        return null;
    }

    @Override
    public String generateOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1");
        if (rst.next()){
            String selectedId = rst.getString(1);
            String splitId = selectedId.split("[A-Z]")[1];
            int i = Integer.parseInt(splitId);
            i++;
            return "D"+i;
        }else{
            return "D1";
        }

    }
}
