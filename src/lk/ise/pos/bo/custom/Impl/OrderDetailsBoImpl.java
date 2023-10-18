package lk.ise.pos.bo.custom.Impl;

import lk.ise.pos.bo.custom.OrderDetailsBo;
import lk.ise.pos.dao.DaoFactory;
import lk.ise.pos.dao.custom.OrderDetailsDao;
import lk.ise.pos.dto.OrderDetailsDto;
import lk.ise.pos.entity.OrderDetails;
import lk.ise.pos.enums.DaoType;

public class OrderDetailsBoImpl implements OrderDetailsBo {

    private OrderDetailsDao orderDetailsDao = DaoFactory.getInstance().getDao(DaoType.ORDER_DETAILS);
    @Override
    public boolean saveOrderDetails(OrderDetailsDto dto) throws Exception {
        return orderDetailsDao.save(
                new OrderDetails(dto.getCode(),dto.getOrderId(),dto.getUnitPrice(),dto.getQty())
        );
    }
}
