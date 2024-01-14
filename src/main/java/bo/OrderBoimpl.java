package bo;

import dao.impl.OrderDaoImpl;
import entity.Item;
import entity.Orders;

public class OrderBoimpl {
    private OrderDaoImpl orderDao = new OrderDaoImpl();
    public String genertateID() {
        Orders dto = orderDao.getLastOrder();
        if (dto != null) {
            String id = dto.getCustomId();
            int num = Integer.parseInt(id.split("[OD]")[1]);
            num++;
            return String.format("OD%04d", num);
        } else {
            return "OD0001";
        }
    }
}
