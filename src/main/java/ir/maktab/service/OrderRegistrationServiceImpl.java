package ir.maktab.service;

import ir.maktab.data.model.entity.OrderRegistration;
import ir.maktab.data.model.enums.OrderStatus;
import ir.maktab.data.repository.OrderRegistrationRepository;
import ir.maktab.exception.RepeatException;
import ir.maktab.exception.ValidationException;
import ir.maktab.service.interfaces.OrderRegistrationService;
import ir.maktab.util.UtilDate;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


public class OrderRegistrationServiceImpl implements OrderRegistrationService {
    private final OrderRegistrationRepository orderRegistrationRepository = OrderRegistrationRepository.getInstance();
    private static OrderRegistrationServiceImpl orderRegistrationService = new OrderRegistrationServiceImpl();

    private OrderRegistrationServiceImpl() {
    }

    public static OrderRegistrationServiceImpl getInstance() {
        return orderRegistrationService;
    }

    @Override
    public void saveOrder(OrderRegistration orderRegistration) {
        checkOrderRepeat(orderRegistration);
        Date today = UtilDate.changeLocalDateToDate(LocalDate.now());
        if (orderRegistration.getOfferPrice().compareTo(orderRegistration.getSubJob().getPrice()) < 0)
            throw new ValidationException("priceOffer lower of basic price");
        if (UtilDate.compareTwoDate(orderRegistration.getDoWorkDate(), today) < 0)
            throw new ValidationException("You can't order before today ");
        orderRegistration.setOrderStatus(OrderStatus.WaitingForTheExperts);
        orderRegistrationRepository.save(orderRegistration);
    }

    @Override
    public List<OrderRegistration> findAll() {
        return orderRegistrationRepository.getAll();
    }

    private void checkOrderRepeat(OrderRegistration orderRegistration) {
        if (!findAll().isEmpty()) {
            if (findAll().stream().anyMatch(order -> order.getCodeOrder().equals(orderRegistration.getCodeOrder())))
                throw new RepeatException("this order already is exist");
        }
    }

}
