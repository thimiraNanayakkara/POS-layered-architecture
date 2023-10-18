package lk.ise.pos.bo.custom;

import lk.ise.pos.bo.SuperBo;
import lk.ise.pos.dto.OrderDetailsDto;

public interface OrderDetailsBo extends SuperBo {
    public boolean saveOrderDetails(OrderDetailsDto dto) throws Exception;
}
