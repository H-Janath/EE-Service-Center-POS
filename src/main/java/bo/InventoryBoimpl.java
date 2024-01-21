package bo;

import dao.impl.InventoryDaoImpl;
import dto.InventoryDto;
import entity.Inventory;

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

    public List<InventoryDto> getOrderDetails(int orderId) {
            List<Inventory> inventoryList = inventoryDao.getItem(orderId);
            List<InventoryDto> dtoList = new ArrayList<>();
            if(inventoryList!=null){
                for(Inventory inventoryDto: inventoryList){
                    dtoList.add(
                            new InventoryDto(
                                    inventoryDto.getCustomId(),
                                    inventoryDto.getName(),
                                    inventoryDto.getFault(),
                                    inventoryDto.getStatus(),
                                    inventoryDto.getCategory()
                            )
                    );
                }
                return dtoList;
            }
            return null;
    }
}
