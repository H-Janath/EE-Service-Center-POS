package bo;

import dao.impl.AdditemDaoImpl;
import dto.ItemDto;
import entity.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class AddItemBoImpl {
    AdditemDaoImpl additemDao = new AdditemDaoImpl();

    public boolean saveItem(ItemDto itemDto){
        return additemDao.save(itemDto);
    }

    public ObservableList<String> getCategory() {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("ELECTRONIC");
        list.add("ELECTRICALE");
        return list;
    }

    public String genertateID() {
        Item dto = additemDao.getLastItem();
        if (dto != null) {
            String id = dto.getItem_code();
            int num = Integer.parseInt(id.split("[I]")[1]);
            num++;
            return String.format("I%03d", num);
        } else {
            return "I001";
        }
    }
}