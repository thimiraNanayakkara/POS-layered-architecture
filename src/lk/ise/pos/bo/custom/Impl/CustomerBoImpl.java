package lk.ise.pos.bo.custom.Impl;

import lk.ise.pos.bo.custom.CustomerBo;
import lk.ise.pos.dao.DaoFactory;
import lk.ise.pos.dao.custom.CustomerDao;
import lk.ise.pos.dao.custom.Impl.CustomerDaoImpl;
import lk.ise.pos.dto.CustomerDto;
import lk.ise.pos.entity.Customer;
import lk.ise.pos.enums.DaoType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBoImpl implements CustomerBo {

    private CustomerDao customerDao = DaoFactory.getInstance().getDao(DaoType.CUSTOMER);
    @Override
    public boolean saveCustomer(CustomerDto dto) throws Exception {
       return customerDao.save(
                new Customer(dto.getId(), dto.getName(),dto.getAddress(), dto.getSalary())
       );
    }

    @Override
    public boolean updateCustomer(CustomerDto dto) throws Exception {
        return customerDao.update(
                new Customer(dto.getId(), dto.getName(),dto.getAddress(), dto.getSalary())
        );
    }

    @Override
    public CustomerDto findCustomer(String id) throws Exception {
        Customer customer = customerDao.find(id);
        if (customer!=null){
            return new CustomerDto(
                    customer.getId(),customer.getName(),customer.getAddress(),customer.getSalary()
            );
        }
        return null;
    }

    @Override
    public boolean deleteCustomer(String id) throws Exception {
        return customerDao.delete(id);
    }

    @Override
    public List<CustomerDto> findAllCustomers() throws Exception {
        List<Customer> list = customerDao.findAll();
        List<CustomerDto> dtos = new ArrayList<>();
        for(Customer customer:list){
            dtos.add(
                    new CustomerDto(customer.getId(),customer.getName(),customer.getAddress(),customer.getSalary())
            );
        }
        return dtos;
    }

    @Override
    public List<String> loadCustomerIds() throws SQLException, ClassNotFoundException {
        return customerDao.loadCustomerIds();
    }
}
