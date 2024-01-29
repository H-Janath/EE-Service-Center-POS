package bo;

import dao.custom.impl.OrderDaoImpl;
import dto.CustIDOrderDto;
import dto.OrderDto;
import entity.Orders;

import java.util.ArrayList;
import java.util.List;

public class OrderBoimpl {
    private OrderDaoImpl orderDao = new OrderDaoImpl();

    public boolean saveOrder(OrderDto orderDto,String customId){
        return orderDao.saveOrder(
                new Orders(
                        orderDto.getCustomId(),
                        orderDto.getDescription(),
                        orderDto.getStatus(),
                        orderDto.getAmount(),
                        orderDto.getDate()
                ),customId);
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
                            orders.getDate(),
                            orders.getInventoryList()
                    )
            );
        }
        return custIDOrderDtos;
    }


    public List<OrderDto> findOrders(String phone) {
        List<Orders> ordersList = orderDao.findOrderList(phone);
        if(ordersList!=null){
            List<OrderDto> dtoList = new ArrayList<>();
            for(Orders orders : ordersList){
                dtoList.add(
                        new OrderDto(
                                orders.getCustomId(),
                                orders.getDescription(),
                                orders.getStatus(),
                                orders.getAmount(),
                                orders.getDate()
                        )
                );
            }
            return dtoList;
        }else{
            return null;
        }
    }
}
