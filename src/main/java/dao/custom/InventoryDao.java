package dao.custom;

import dao.CrudDao;
import entity.Inventory;
import entity.Parts;

import java.util.List;

public interface InventoryDao extends CrudDao<Inventory> {
    public boolean saveList(List<Inventory> inventoryItems, String customId);
    public Inventory getLastItem();
    public List<Inventory> getItem(int orderId);
    public boolean updateStatus(Long inventoryid, String status);
    public List<Parts> getParts(int invetoryId);
}
