package bo.custom;

import bo.SuperBo;
import dto.InventoryDto;

import java.util.List;

public interface InventoryBo<T> extends SuperBo {
    public boolean saveInventoryItems(List<T> inventories, String customid);
    public String genertateInventoryID();
    public List<InventoryDto> getOrderDetails(int orderId);
}
