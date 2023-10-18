package lk.ise.pos.bo.custom.Impl;

import lk.ise.pos.bo.custom.ItemBo;
import lk.ise.pos.dao.DaoFactory;
import lk.ise.pos.dao.custom.Impl.ItemDaoImpl;
import lk.ise.pos.dao.custom.ItemDao;
import lk.ise.pos.dto.ItemDto;
import lk.ise.pos.entity.Item;
import lk.ise.pos.enums.DaoType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBoImpl implements ItemBo {

    private ItemDao itemDao = DaoFactory.getInstance().getDao(DaoType.ITEM);
    @Override
    public boolean saveCItem(ItemDto dto) throws Exception {
        return itemDao.save(
                new Item(dto.getCode(),dto.getDescription(),dto.getQtyOnHand(),dto.getUnitPrice())
        );
    }

    @Override
    public boolean updateItem(ItemDto dto) throws Exception {
        return itemDao.update(
                new Item(dto.getCode(),dto.getDescription(),dto.getQtyOnHand(),dto.getUnitPrice())
        );
    }

    @Override
    public ItemDto findItem(String id) throws Exception {
        Item item = itemDao.find(id);
        if (item!=null){
            return new ItemDto(item.getCode(),item.getDescription(),item.getQtyOnHand(),item.getUnitPrice());
        }
        return null;
    }

    @Override
    public boolean deleteItem(String id) throws Exception {
        return itemDao.delete(id);
    }

    @Override
    public List<ItemDto> findAllItems(String id) throws Exception {
        List<ItemDto> list = new ArrayList<>();
        for(Item item: itemDao.findAll()){
            list.add(
                    new ItemDto(item.getCode(),item.getDescription(),item.getQtyOnHand(),item.getUnitPrice())
            );
        }
        return list;
    }

    @Override
    public List<String> loadItemCodes() throws SQLException, ClassNotFoundException {
        return itemDao.loadItemCodes();
    }

    @Override
    public boolean updateQty(String code, int qty) throws SQLException, ClassNotFoundException {
        return itemDao.updateQty(code,qty);
    }
}
