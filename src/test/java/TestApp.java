import ir.maktab.data.model.entity.*;
import ir.maktab.data.model.enums.SpecialtyStatus;
import ir.maktab.exception.NotFoundException;
import ir.maktab.service.*;
import ir.maktab.service.interfaces.BasicJobService;
import ir.maktab.service.interfaces.CustomerService;
import ir.maktab.service.interfaces.ExpertService;
import ir.maktab.util.UtilDate;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

public class TestApp {
    @Test
    public  void testAddOrder() {
        CustomerService customerService = new CustomerServiceImpl();
        ExpertService expertService =ExpertServiceImpl.getInstance();
        Customer customer = customerService.findByUserName("somaye@qrt.com");
        OrderRegistrationServiceImpl orderRegistrationService = OrderRegistrationServiceImpl.getInstance();
        SubJobServiceImpl subJobService = SubJobServiceImpl.getInstance();
        SubJob subJob = subJobService.finByName("soft");
        LocalDate localDate = LocalDate.of(2023, 01, 30);
        OrderRegistration orderRegistration = OrderRegistration.builder().address(Address.builder().city("kerman").build()).codeOrder("order1").
                aboutWork("doing to wash soft").offerPrice(new BigDecimal("30e4")).
                doWorkDate(UtilDate.changeLocalDateToDate(localDate)).
                subJob(subJob).build();
        orderRegistrationService.saveOrder(orderRegistration);
    }

    public static byte[] toByteArray(InputStream inputStream) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }
        return outputStream.toByteArray();
    }

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
        subJob.setPrice(new BigDecimal("25e4"));
        subJobService.updateSubJob(subJob);
        subJobService.deleteSubJob(subJob1);

    }

    @Test
    public void testPersonService() throws IOException {
        CustomerService customerService = new CustomerServiceImpl();
        Credit credit = Credit.builder().balance(new BigDecimal("2e6")).build();
        Customer customer = Customer.builder().
                firstName("somaye").
                lastName("karimi").email("somaye@qrt.com").password("Somaye12").build();
        BufferedImage originalImage = ImageIO.read(new File("image.png"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "jpg", bos);

        byte[] image = bos.toByteArray();
        Expert expert = Expert.builder().firstName("ali").
                lastName("akbari").email("ali@akbari.com").password("Ali12345").specialtyStatus(SpecialtyStatus.NewState).build();
        ExpertServiceImpl expertServiceImpl =ExpertServiceImpl.getInstance();
        /* customerService.save(customer);*/
        expertServiceImpl.save(expert, new File("OIF.jpg"));
        List<Expert> all = expertServiceImpl.findAllExpertsIsNotConfirm();
        all.forEach(System.out::println);
        all.forEach(Person::getEmail);

    }

    @Test
    public void testAddAndDelete() {
        ExpertService expertService = ExpertServiceImpl.getInstance();
        AdminServiceImpl adminServiceImpl = new AdminServiceImpl();
        BasicJobService basicJobService = BasicJobsService.getInstance();
        SubJobServiceImpl subJobService = SubJobServiceImpl.getInstance();
        adminServiceImpl.isConfirmExpertByAdmin("ali@akbari.com");
        Expert expert = expertService.findByUserName("ali@akbari.com");
        SubJob subJob = subJobService.finByName("soft");
        adminServiceImpl.addExpertToSubJob(expert, subJob);
        /*adminServiceImpl.deleteExpertOfSubJob((Expert) expert, subJob);*/
    }

    @Test
    public void testimage() throws Exception {
        File file = new File("OIF.jpg");
        final var i = (int) file.length() / 1024;
        FileInputStream fis = new FileInputStream(file);
        InputStream inputStream = Files.newInputStream(Paths.get("image.png"));
        byte[] bytes = toByteArray(inputStream);
        ImageInputStream imageInputStream = ImageIO.createImageInputStream(fis);

        Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(imageInputStream);
        if (!imageReaders.hasNext()) {
            throw new NotFoundException("Image Readers Not Found!!!");
        }
        ImageReader reader = imageReaders.next();
        System.out.println(reader.getFormatName());
    }
}
