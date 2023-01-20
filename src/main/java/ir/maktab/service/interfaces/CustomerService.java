package ir.maktab.service.interfaces;

import ir.maktab.data.model.entity.BasicJob;
import ir.maktab.data.model.entity.Customer;
import ir.maktab.data.model.entity.OrderRegistration;
import ir.maktab.data.model.entity.SubJob;

import java.util.List;

public interface CustomerService {
    Customer login(String userName, String password);

    void changePassword(String userName, String passwordOld, String newPassword);

    Customer findByUserName(String userName);

    void update(Customer customer);

    void save(OrderRegistration orderRegistration);

    List<BasicJob> viewAllBasicJobs();

    List<SubJob> viewSubJobsABasicJob(String nameBasicJob);

    void save(Customer customer);

}
