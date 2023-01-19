package ir.maktab.service.interfaces;

import ir.maktab.data.model.entity.OrderRegistration;

import java.util.List;

public interface OrderRegistrationService {
    void saveOrder(OrderRegistration orderRegistration);

    List<OrderRegistration> findAll();
}
