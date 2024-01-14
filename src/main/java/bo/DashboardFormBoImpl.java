package bo;

import dao.AdditemDao;
import dao.impl.AdditemDaoImpl;
import dto.ItemDto;
import entity.Item;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardFormBoImpl {
    AdditemDao additemDao = new AdditemDaoImpl();


    public List<ItemDto> getElectricItem() {
        List<ItemDto> dtoList = allItems();

        return dtoList.stream()
                .filter(itemDto -> "ELECTRICALE".equals(itemDto.getCategory()))
                .collect(Collectors.toList());
    }

    public List<ItemDto> getElectronicItem() {
        List<ItemDto> dtoList = allItems();

        return dtoList.stream()
                .filter(itemDto -> "ELECTRONIC".equals(itemDto.getCategory()))
                .collect(Collectors.toList());
    }


    public List<ItemDto> allItems(){
        List<Item> itemList =  additemDao.getAll();
        List<ItemDto> dtoList = new ArrayList<>();
        for (Item item:itemList){
            dtoList.add(
                    new ItemDto(
                            item.getItem_code(),
                            item.getName(),
                            item.getCategory()
                    )
            );
        }
        return dtoList;
    }

}
