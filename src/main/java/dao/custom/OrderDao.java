package dao.custom;

import dao.CrudDao;
import entity.Orders;
import java.util.List;

public interface OrderDao extends CrudDao<Orders> {
    public boolean saveOrder(Orders order, String customId);
    public Orders getLastOrder();
    public List<Orders> findOrderList(String contactNo);
}
