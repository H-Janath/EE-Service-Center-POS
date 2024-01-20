package bo;

import dao.impl.OrderDaoImpl;
import dto.CustIDOrderDto;
import dto.OrderDto;
import entity.Orders;

import java.util.ArrayList;
import java.util.List;

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
    public List<CustIDOrderDto> allOrders(){
        List<Orders> all = orderDao.getAll();
        List<CustIDOrderDto> custIDOrderDtos = new ArrayList<>();
        for(Orders orders: all){
            custIDOrderDtos.add(
                    new CustIDOrderDto(
                            orders.getCustomId(),
                            orders.getCustomer().getCustomId(),
                            orders.getDescription(),
                            orders.getStatus(),
                            orders.getAmount(),
                            orders.getDate()
                    )
            );
        }
        return custIDOrderDtos;
    }
}
