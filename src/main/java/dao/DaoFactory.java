package dao;

import dao.custom.impl.*;
import dao.utill.DaoType;

public class DaoFactory {
    private DaoFactory(){

    }
    private static DaoFactory daoFactory;

    public static DaoFactory getInstance(){
        return daoFactory!=null?daoFactory:new DaoFactory();
    }

    public <T extends SuperDao>T getDao(DaoType daoType){
        switch (daoType){
            case ITEM:return (T)new AdditemDaoImpl();
            case ORDER:return (T)new OrderDaoImpl();
            case INVENTORY:return (T)new InventoryDaoImpl();
            case CUSTOMER:return (T)new CustomerDaoImpl();
            case USER:return (T)new UserDaoImpl();
        }
        return null;
    }
}
