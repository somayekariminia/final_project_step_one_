package ir.maktab.service;

import ir.maktab.data.model.entity.BasicJob;
import ir.maktab.data.model.entity.SubJob;
import ir.maktab.exception.NotFoundException;
import ir.maktab.exception.ValidationException;
import ir.maktab.repository.BasicJobRepository;

import java.util.List;

public class BasicJobsService implements BasicJobService {
    private final BasicJobRepository subJobRepository = BasicJobRepository.getInstance();

    @Override
    public void save(SubJob services) {
        try {
            existServiceInDb(services);
            subJobRepository.save(services);
        } catch (ValidationException ve) {
            System.err.println(ve.getMessage());
        }
    }

    @Override
    public List<SubJob> getAll() {
        List<SubJob> subJobList = subJobRepository.getAll();
        if (subJobList.isEmpty())
            throw new NotFoundException("list is empty");
        return subJobList;
    }

    @Override
    public void updateServices(SubJob newSubJob) {
        subJobRepository.update(newSubJob);
    }

    @Override
    public void deleteServices(SubJob subJob) {
        subJobRepository.delete(subJob);
    }

    private void existServiceInDb(BasicJob basicJob) {
        List<SubJob> list = getAll();
        if (list.stream().noneMatch(subJob -> subJob.getNameBase().equals(basicJob.getNameBase())))
            throw new ValidationException("basicService Is not exist");
        if (basicJob instanceof SubJob)
            if (list.stream().anyMatch((subJob -> subJob.getSubJobName().equals(((SubJob) basicJob).getSubJobName()))))
                throw new ValidationException("this subService Already saved");
    }

    public SubJob findByName(String name){
        return subJobRepository.getByName(name).orElseThrow(()->new NotFoundException("SubJob is Null!!!"));
    }
}
