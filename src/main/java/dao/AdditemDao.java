package dao;

import entity.Item;

import java.util.List;

public interface AdditemDao {
    public Item getLastItem();
    public List<Item> getAll();
}
