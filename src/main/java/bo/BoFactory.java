package bo;

import bo.custom.impl.*;
import bo.util.BoType;

public class BoFactory {
    private static BoFactory boFactory;
    private BoFactory(){

    }
    public static BoFactory getInstance(){
        return boFactory!=null?boFactory:(boFactory=new BoFactory());
    }

    public <T extends SuperBo>T getBo(BoType boType){
        switch (boType){
            case ITEM:return (T) new AddItemBoImpl();
            case CUSTOMER:return (T) new CustomerBoImpl();
            case USER:return (T) new UsersBoImpl();
            case INVENTORY:return (T) new InventoryBoimpl();
            case ORDER:return (T)new OrderBoimpl();
            case DASHBOARD:return (T) new DashboardFormBoImpl();
        }
        return null;
    }
}
