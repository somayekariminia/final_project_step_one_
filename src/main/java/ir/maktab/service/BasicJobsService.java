package ir.maktab.service;

import ir.maktab.data.model.entity.BasicJob;
import ir.maktab.data.model.entity.SubJob;
import ir.maktab.exception.NotFoundException;
import ir.maktab.exception.NullableException;
import ir.maktab.exception.RepeatException;
import ir.maktab.exception.ValidationException;
import ir.maktab.repository.BasicJobRepository;

import java.util.List;
import java.util.Objects;

public class BasicJobsService implements BasicJobService {
    private final BasicJobRepository basicJobRepository = BasicJobRepository.getInstance();
    private static BasicJobsService instance = new BasicJobsService();
    private static SubJobServiceImpl subJobService = SubJobServiceImpl.getInstance();

    private BasicJobsService() {

    }

    public static BasicJobsService getInstance() {
        return instance;
    }

    @Override
    public void save(BasicJob services) {
        try {
            existServiceInDb(services.getNameBase());
            basicJobRepository.save(services);
        } catch (ValidationException ve) {
            System.err.println(ve.getMessage());
        }
    }

    public List<SubJob> findAllSubJobsABasicJob(String nameBasicJob) {
        List<SubJob> listSubJob = subJobService.findAll().stream().filter(subJob -> subJob.getBasicJob()
                .getNameBase().equals(nameBasicJob)).toList();
        if (listSubJob.isEmpty())
            throw new NotFoundException("is not exist any subJob to nameBasic");
        else
            return listSubJob;
    }

    @Override
    public List<BasicJob> findAllBasicJobs() {
        List<BasicJob> listBasicJob = basicJobRepository.getAll();
        return listBasicJob;
    }


    @Override
    public void updateServices(BasicJob basicJob) {
        if (Objects.isNull(basicJob))
            throw new NullableException("basic is null");
        basicJobRepository.update(basicJob);
    }

    @Override
    public void deleteServices(BasicJob basicJob) {
        if (Objects.isNull(basicJob))
            throw new NullableException("basic is null");
        subJobService.findAll().stream().filter(subJob -> subJob.getBasicJob().getNameBase().equals(basicJob.getNameBase())).forEach(subJob -> subJobService.deleteSubJob(subJob));
        basicJobRepository.delete(basicJob);
    }

    private void existServiceInDb(String nameBasicJob) {
        if (!findAllBasicJobs().isEmpty())
            if (findAllBasicJobs().stream().anyMatch(basicJob1 -> basicJob1.getNameBase().equals(nameBasicJob)))
                throw new RepeatException("this basicService already in db");
    }


    @Override
    public BasicJob findByName(String name) {
        return basicJobRepository.getByName(name).orElseThrow(() -> new NotFoundException("SubJob is Null!!!"));
    }
}
