package bo;

import dao.impl.InventoryDaoImpl;
import entity.Inventory;
import entity.Item;

public class InventoryBoimpl {
    InventoryDaoImpl inventoryDao = new InventoryDaoImpl();
    public String genertateID() {
        Inventory dto = inventoryDao.getLastItem();
        if (dto != null) {
            String id = dto.getCustomId();
            int num = Integer.parseInt(id.split("IV")[1]);
            num++;
            return String.format("IV%04d", num);
        } else {
            return "IV0001";
        }
    }
}
