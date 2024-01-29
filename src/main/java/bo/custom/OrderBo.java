package bo.custom;

import bo.SuperBo;
import dto.CustIDOrderDto;
import dto.InventoryDto;
import dto.OrderDto;

import java.util.List;

public interface OrderBo extends SuperBo {

    public String genertatOrderID();
    public List<CustIDOrderDto> allOrders();
    public List<OrderDto> findOrders(String phone);
}
