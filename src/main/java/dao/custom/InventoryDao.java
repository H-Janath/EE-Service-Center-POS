package dao.custom;

import dao.CrudDao;
import entity.Inventory;

import java.util.List;

public interface InventoryDao extends CrudDao<Inventory> {
    public boolean saveList(List<Inventory> inventoryItems, String customId);
    public Inventory getLastItem();
    public List<Inventory> getItem(int orderId);
}
