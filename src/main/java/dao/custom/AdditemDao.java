package dao.custom;

import dao.CrudDao;
import entity.Item;

public interface AdditemDao extends CrudDao<Item> {
    public Item getLastItem();

}
