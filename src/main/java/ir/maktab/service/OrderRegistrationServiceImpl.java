package ir.maktab.service;
import ir.maktab.data.model.entity.OrderRegistration;

import ir.maktab.data.model.enums.OrderStatus;
import ir.maktab.exception.RepeatException;
import ir.maktab.exception.ValidationException;
import ir.maktab.repository.OrderRegistrationRepository;
import ir.maktab.util.UtilDate;

import java.time.LocalDate;
import java.util.List;


public class OrderRegistrationServiceImpl {
    BasicJobService basicJobService = BasicJobsService.getInstance();
    OrderRegistrationRepository orderRegistrationRepository = OrderRegistrationRepository.getInstance();

    public void saveOrder(OrderRegistration orderRegistration) {
        checkOrderRepeat(orderRegistration);
        LocalDate today = LocalDate.now();
        if (orderRegistration.getOfferPrice().compareTo(orderRegistration.getSubJob().getPrice()) < 0)
            throw new ValidationException("priceOffer lower of basic price");
        LocalDate dateOrder = UtilDate.getLocalDateTime(orderRegistration.getDoWorkDate());
        if (dateOrder.compareTo(today) < 0)
            throw new ValidationException("Your registered date has expired ");
        orderRegistration.setOrderStatus(OrderStatus.WaitingForTheExperts);
        orderRegistrationRepository.save(orderRegistration);
    }

    public List<OrderRegistration> findAll(){
       return orderRegistrationRepository.getAll();
    }

    private void checkOrderRepeat(OrderRegistration orderRegistration){
        if(!findAll().isEmpty())
            if(findAll().stream().anyMatch(orderRegistration1 -> orderRegistration.equals(orderRegistration)))
                throw new RepeatException("this order already is exist");

    }



}
