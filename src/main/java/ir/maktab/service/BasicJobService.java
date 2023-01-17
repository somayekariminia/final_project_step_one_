package ir.maktab.service;

import ir.maktab.data.model.entity.BasicJob;
import ir.maktab.data.model.entity.SubJob;

import javax.persistence.Basic;
import java.util.List;
import java.util.Set;

public interface BasicJobService {
    void save(BasicJob subJob);

    List<BasicJob> getAll();

    void updateServices(SubJob services);

    void deleteServices(SubJob services);

    SubJob findByName(String name);
    List<BasicJob> findAllSubJobs(String nameBasicJob);
    public Set<String> findAllBasicJobs();
}
