package ir.maktab.service.interfaces;

import ir.maktab.data.model.entity.BasicJob;
import ir.maktab.data.model.entity.SubJob;

import java.util.List;

public interface BasicJobService {
    void save(BasicJob subJob);

    void updateServices(BasicJob services);

    void deleteServices(BasicJob services);

    BasicJob findByName(String name);

    List<SubJob> findAllSubJobsABasicJob(String nameBasicJob);

    List<BasicJob> findAllBasicJobs();
}
