package ir.maktab.service;

import ir.maktab.data.model.entity.SubJob;
import ir.maktab.exception.NotFoundException;
import ir.maktab.exception.ValidationException;
import ir.maktab.repository.SubJobRepository;

import java.util.List;

public class SubJobServiceImpl implements SubJobService {
    private SubJobRepository subJobRepository;

    @Override
    public void save(SubJob services) {

    }

    @Override
    public List<SubJob> getAll() {
        List<SubJob> servicesList = subJobRepository.getAll();
        if (servicesList.isEmpty())
            throw new NotFoundException("list is empty");
        return servicesList;
    }

    @Override
    public void updateServices(SubJob newSubJob) {
        subJobRepository.update(newSubJob);
    }

    @Override
    public void deleteServices(SubJob subJob) {
        subJobRepository.delete(subJob);
    }

    private void checkService(String name) {
        List<SubJob> list = getAll();
        if (list.stream().anyMatch(subJob -> subJob.getSubJobName().equals(name))) ;
        throw new ValidationException("this service Already saved");
    }

}
