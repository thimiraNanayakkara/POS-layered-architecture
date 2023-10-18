package lk.ise.pos.dao.custom.Impl;

import lk.ise.pos.dao.CrudUtil;
import lk.ise.pos.dao.custom.ItemDao;
import lk.ise.pos.entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl implements ItemDao {
    @Override
    public boolean save(Item item) throws Exception {
        return CrudUtil.execute("INSERT INTO item VALUES(?,?,?,?)",
                item.getCode(),item.getDescription(),item.getQtyOnHand(),item.getUnitPrice());
    }

    @Override
    public boolean update(Item item) throws Exception {
        return CrudUtil.execute(
                "UPDATE item SET description=?, qty_on_hand=?,unit-price=? WHERE code=?",
                item.getDescription(),item.getQtyOnHand(),item.getUnitPrice(),item.getCode());
    }

    @Override
    public Item find(String code) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM item WHERE code=?",code);
        if (rst.next()){
            return new Item(rst.getString(1), rst.getString(2), rst.getInt(3), rst.getDouble(4));
        }
        else {
            return null;
        }
    }

    @Override
    public boolean delete(String code) throws Exception {
        return CrudUtil.execute("DELETE FROM item WHERE code=?",code);
    }

    @Override
    public List<Item> findAll() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM item");
        List<Item> list = new ArrayList<>();
        while (rst.next()){
            Item i = new Item(rst.getString(1),rst.getString(2), rst.getInt(3), rst.getDouble(4));
            list.add(i);
        }

        return list;
    }

    @Override
    public List<String> loadItemCodes() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT code FROM item");
        List<String> list = new ArrayList<>();
        while (resultSet.next()){
            list.add(resultSet.getString(1));
        }
        return list;
    }

    @Override
    public boolean updateQty(String code, int qty) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE item SET qty_on_hand=(qty_on_hand-?) WHERE code=?",qty,code);
    }
}
