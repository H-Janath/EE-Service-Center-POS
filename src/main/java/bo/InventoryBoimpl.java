package bo;
import dto.OrderDto;
import dao.impl.InventoryDaoImpl;
import dto.InventoryDto;
import entity.Inventory;
import entity.Item;
import entity.Orders;

import java.util.ArrayList;
import java.util.List;

public class InventoryBoimpl {
    InventoryDaoImpl inventoryDao = new InventoryDaoImpl();
    public boolean saveInventoryItems(List<InventoryDto> inventories, String customid){
        List<Inventory> inventorielist = new ArrayList<>();
        for(InventoryDto inventoryDto : inventories){
            inventorielist.add(
                    new Inventory(
                            inventoryDto.getCustomId(),
                            inventoryDto.getName(),
                            inventoryDto.getFault(),
                            inventoryDto.getStatus(),
                            inventoryDto.getCategory()
                    )
            );
        }
        return inventoryDao.save(inventorielist,customid);
    }
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
