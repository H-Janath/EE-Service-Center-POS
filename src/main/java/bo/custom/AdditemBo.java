package bo.custom;

import bo.SuperBo;
import dto.ItemDto;
import javafx.collections.ObservableList;

import java.util.List;

public interface AdditemBo<T> extends SuperBo {
    public boolean saveItem(T itemDto);
    public ObservableList<String> getCategory();
    public String genertateItemID();
    public List<ItemDto> allItems();
}
