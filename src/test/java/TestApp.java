import ir.maktab.data.model.entity.*;
import ir.maktab.data.model.enums.SpecialtyStatus;
import ir.maktab.service.*;
import ir.maktab.util.UtilDate;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class TestApp {
    @Test
    public void testSaveServices() {

        BasicJobService basicJobService = BasicJobsService.getInstance();
        SubJobServiceImpl subJobService = SubJobServiceImpl.getInstance();
        BasicJob basicJob = BasicJob.builder().nameBase("home").build();
        SubJob subJob = SubJob.builder().subJobName("soft").basicJob(basicJob).price(new BigDecimal(100000)).description("wash").build();
        basicJobService.save(basicJob);
        subJobService.save(subJob);
        SubJob subJob1 = SubJob.builder().subJobName("carpet").basicJob(basicJob).price(new BigDecimal(20000)).description("wash").build();
        subJobService.save(subJob1);
        System.out.println("/////////////////////////////////");
        System.out.println(basicJobService.findAllBasicJobs());
        System.out.println("////////////////////////////////////////");
        System.out.println(basicJobService.findAllSubJobsABasicJob("home"));
        subJob.setPrice(new BigDecimal(25e4));
        subJobService.updateSubJob(subJob);
        subJobService.deleteSubJob(subJob1);
        basicJobService.deleteServices(basicJob);
    }

    @Test
    public void testPersonService() throws IOException {
        Credit credit = Credit.builder().balance(new BigDecimal(2e6)).build();
        Person customer = Customer.builder().
                firstName("somaye").
                lastName("karimi").email("somaye@qrt.com").password("Somaye12").build();
        BufferedImage originalImage = ImageIO.read(new File("OIF.jpg"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "jpg", bos);

        byte[] image = bos.toByteArray();
        Expert expert = Expert.builder().firstName("ali").
                lastName("akbari").email("ali@akbari.com").password("Ali12345").specialtyStatus(SpecialtyStatus.NewState).build();
        PersonServiceImPl personService = new PersonServiceImPl();
        personService.save(customer, new File(""));
        personService.save(expert, new File("image.png"));
        List<Person> all = personService.findAllExpertsIsNotConfirm();
        all.forEach(System.out::println);
        all.forEach(Person::getEmail);
    }

    @Test
    public void testAddAndDelete() {
        PersonService expertService = new PersonServiceImPl();
        AdminService adminService = new AdminService();
        BasicJobService basicJobService = BasicJobsService.getInstance();
        SubJobServiceImpl subJobService = SubJobServiceImpl.getInstance();
        Person expert = expertService.findByUserName("ali@akbari.com");
        SubJob subJob = subJobService.finByName("soft");
        adminService.addExpertToSubJob((Expert) expert, subJob);
        adminService.deleteExpertOfSubJob((Expert) expert, subJob);
    }
    @Test
    public void testAddOrder() {
        PersonService personService = new PersonServiceImPl();
       Person customer = personService.findByUserName("somaye@qrt.com");
       OrderRegistrationServiceImpl orderRegistrationService=new OrderRegistrationServiceImpl();
       SubJobServiceImpl subJobService=SubJobServiceImpl.getInstance();
       SubJob subJob=subJobService.finByName("soft");
        LocalDate localDate = LocalDate.of(2023, 01, 30);
        OrderRegistration orderRegistration = OrderRegistration.builder().address("kerman").codeOrder("order1").
                aboutWork("doing to wash soft").offerPrice(new BigDecimal(30e4)).doWorkDate(UtilDate.changeLocalDateToDate(localDate)).subJob(subJob).customer((Customer) customer).build();
        orderRegistrationService.saveOrder(orderRegistration);
    }
}
