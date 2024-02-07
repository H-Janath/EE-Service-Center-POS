package bo.custom.impl;

import bo.custom.InventoryBo;
import dao.custom.impl.InventoryDaoImpl;
import dto.InventoryDto;
import entity.Inventory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.List;

public class InventoryBoimpl implements InventoryBo<InventoryDto> {
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
        return inventoryDao.saveList(inventorielist,customid);
    }
    public boolean updateInventoryItem(String id,String status){
        Long uniquid = Long.parseLong(id.split("IV")[1]);
        return inventoryDao.updateStatus(uniquid,status);
    }
    public String genertateInventoryID() {
        Inventory dto = inventoryDao.getLastItem();
        if (dto != null) {
            Long num = dto.getInventoryId();
            System.out.println(num);
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

    @Override
    public ObservableList<String> getStatus() {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("PENDING");
        list.add("PROCESSING");
        list.add("COMPLETED");
        return list;
    }
}
