package ir.maktab.service.interfaces;

import ir.maktab.data.model.entity.SubJob;

import java.util.List;

public interface SubJobService {
    void save(SubJob subJob);

    List<SubJob> findAll();

    void updateSubJob(SubJob subJob);

    void deleteSubJob(SubJob subJob);

    SubJob finByName(String name);
}
