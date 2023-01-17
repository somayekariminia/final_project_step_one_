package ir.maktab.service;

import ir.maktab.data.model.entity.SubJob;

import java.util.List;

public interface BasicJobService {
    void save(SubJob subJob);

    List<SubJob> getAll();

    void updateServices(SubJob services);

    void deleteServices(SubJob services);

}
