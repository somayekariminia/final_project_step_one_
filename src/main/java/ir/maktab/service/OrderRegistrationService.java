package ir.maktab.service;

import ir.maktab.data.model.entity.OrderRegistration;

import java.util.List;

public interface OrderRegistrationService {
    void saveOrder(OrderRegistration orderRegistration);

    List<OrderRegistration> findAll();
}
