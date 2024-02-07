package bo.custom;

import bo.SuperBo;
import dto.InventoryDto;
import javafx.collections.ObservableList;

import java.util.List;

public interface InventoryBo<T> extends SuperBo {
    public boolean saveInventoryItems(List<T> inventories, String customid);
    public String genertateInventoryID();
    public List<InventoryDto> getOrderDetails(int orderId);
    ObservableList<String> getStatus();
    public boolean updateInventoryItem(String id, String pending);
}
