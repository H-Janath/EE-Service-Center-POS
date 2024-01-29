package bo.custom;

import bo.SuperBo;
import dto.ItemDto;

import java.util.List;

public interface DashboardFormBo<T> extends SuperBo {
    public List<ItemDto> getElectricItem();
    public List<ItemDto> getElectronicItem();
    public List<ItemDto> allItems();
}
