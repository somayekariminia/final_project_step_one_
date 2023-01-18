package ir.maktab.service;

import ir.maktab.data.model.entity.BasicJob;
import ir.maktab.data.model.entity.SubJob;
import ir.maktab.exception.NotFoundException;
import ir.maktab.exception.NullableException;
import ir.maktab.exception.RepeatException;
import ir.maktab.repository.SubJobRepository;

import java.util.List;
import java.util.Objects;

public class SubJobServiceImpl {

    private SubJobRepository subJobRepository = SubJobRepository.getInstance();
    private BasicJobService basicJobService = BasicJobsService.getInstance();
   private static SubJobServiceImpl instance = new SubJobServiceImpl();

    private SubJobServiceImpl() {
    }

    public static SubJobServiceImpl getInstance() {
        return instance;
    }


    public void save(SubJob subJob) {
        checkSubJob(subJob);
        subJobRepository.save(subJob);
    }

    private void checkSubJob(SubJob subJob) {
        List<BasicJob> basicJobList = basicJobService.findAll();
        if (basicJobService.findAll().stream().noneMatch(basicJob -> basicJob.getNameBase().equals(subJob.getBasicJob().getNameBase())))
            throw new NotFoundException("There are no basic services for this sub-service");
        if (findAll().stream().anyMatch(subJob1 -> subJob.getSubJobName().equals(subJob.getSubJobName())))
            throw new RepeatException("this subService Already saved");
    }

    public List<SubJob> findAll() {
        List<SubJob> subJobList = subJobRepository.getAll();
        if (subJobList.isEmpty())
            throw new NotFoundException("subjob list is null");
        return subJobList;
    }

    public void updateSubJob(SubJob subJob) {
        if (Objects.isNull(subJob))
            throw new NullableException("it not can update subJob is null");
        subJobRepository.update(subJob);
    }

    public void deleteSubJob(SubJob subJob) {
        if (Objects.isNull(subJob))
            throw new NullableException("it not can delete subJob is null");
        subJobRepository.delete(subJob);
    }

    public SubJob finByName(String name) {
        return subJobRepository.getByName("name").orElseThrow(() -> new NotFoundException("SubJob is null"));
    }

}
