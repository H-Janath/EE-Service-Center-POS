package bo;

import dao.impl.OrderDaoImpl;
import entity.Item;
import entity.Orders;
import dto.OrderDto;
public class OrderBoimpl {
    private OrderDaoImpl orderDao = new OrderDaoImpl();

    public boolean saveOrder(OrderDto orderDto,String customId){
        return orderDao.save(orderDto,customId);
    }
    public String genertateID() {
        Orders dto = orderDao.getLastOrder();
        if (dto != null) {
            String id = dto.getCustomId();
            int num = Integer.parseInt(id.split("OD")[1]);
            num++;
            return String.format("OD%04d", num);
        } else {
            return "OD0001";
        }
    }
}
