package lk.ise.pos.bo;

import lk.ise.pos.bo.custom.Impl.CustomerBoImpl;
import lk.ise.pos.bo.custom.Impl.ItemBoImpl;
import lk.ise.pos.bo.custom.Impl.OrderBoImpl;
import lk.ise.pos.bo.custom.Impl.UserBoImpl;
import lk.ise.pos.enums.BoType;



public class BoFactory {
    private static BoFactory boFactory;
    private BoFactory(){}
    public static BoFactory getInstance(){
        return boFactory==null?(boFactory=new BoFactory()):boFactory;
    }

    public <T> T getBo(BoType type){
        switch (type){
            case CUSTOMER:  return (T) new CustomerBoImpl();
            case USER:  return (T) new UserBoImpl();
            case ITEM:  return (T) new ItemBoImpl();
            case ORDER:  return (T) new OrderBoImpl();
            default: return null;
        }
    }
}
