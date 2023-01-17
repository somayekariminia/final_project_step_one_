package ir.maktab.service;

import ir.maktab.data.model.entity.BasicJob;
import ir.maktab.data.model.entity.SubJob;
import ir.maktab.exception.NotFoundException;
import ir.maktab.exception.ValidationException;
import ir.maktab.repository.BasicJobRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BasicJobsService implements BasicJobService {
    private final BasicJobRepository subJobRepository = BasicJobRepository.getInstance();

    @Override
    public void save(BasicJob services) {
        try {
            existServiceInDb(services);
            subJobRepository.save(services);
        } catch (ValidationException ve) {
            System.err.println(ve.getMessage());
        }
    }

    @Override
    public List<BasicJob> getAll() {
        List<BasicJob> subJobList = subJobRepository.getAllBas();
        if (subJobList.isEmpty()) throw new NotFoundException("list is empty");
        return subJobList;
    }

    public Set<String> findAllBasicJobs() {
        return subJobRepository.getAllBas().stream().map(BasicJob::getNameBase).collect(Collectors.toSet());
    }

    public List<BasicJob> findAllSubJobs(String nameBasicJob) {
        List<BasicJob> subJobList = getAll();
        return subJobList.stream().filter(basicJob -> basicJob instanceof SubJob).filter(subJob -> subJob.getNameBase().equals(nameBasicJob)).collect(Collectors.toList());
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
        List<BasicJob> list = new ArrayList<>();
        try {
            list = getAll();
            if (basicJob instanceof SubJob) {
                if (list.stream().noneMatch(subJob -> subJob.getNameBase().equals(basicJob.getNameBase())))
                    throw new ValidationException("basicService Is not exist");
                if (list.stream().filter(basicJob1 -> basicJob1 instanceof SubJob).anyMatch((subJob -> ((SubJob) subJob).getSubJobName().equals(((SubJob) basicJob).getSubJobName()))))
                    throw new ValidationException("this subService Already saved");
            } else {
                if (list.stream().noneMatch(subJob -> basicJob.getNameBase().equals(basicJob.getNameBase())))
                    throw new ValidationException("basicService Is not exist");
            }
        }
        catch (NotFoundException e) {
            System.err.println(e.getMessage());
        }
    }


    @Override
    public SubJob findByName(String name) {
        return subJobRepository.getByName(name).orElseThrow(() -> new NotFoundException("SubJob is Null!!!"));
    }
}
