package ir.maktab.service;

import ir.maktab.data.model.entity.Expert;
import ir.maktab.data.model.entity.SubJob;
import ir.maktab.data.model.enums.SpecialtyStatus;

public class AdminService {
    private final PersonService<Expert> expertService = new ExpertServiceImpl();
    private final BasicJobService basicJobService = new BasicJobsService();

    public void addExpertToSubJob(Expert expert, SubJob subJob) {
        Expert expertDb = expertService.findByUserName(expert.getEmail());
        expertDb.getServicesList().add(subJob);
        expertService.update(expert);
    }

    public void deleteExpertOfSubJob(Expert expert,SubJob subJob){
        Expert expertDb = expertService.findByUserName(expert.getEmail());
        expertDb.getServicesList().remove(subJob);
        expertService.update(expert);
    }

    public void isConfirmExpertByAdmin(Expert expert){
        Expert expertDb=expertService.findByUserName(expert.getEmail());
        if(expertDb.getSpecialtyStatus().equals(SpecialtyStatus.NewState));
        expertDb.setSpecialtyStatus(SpecialtyStatus.Confirmed);
        expertService.update(expertDb);
    }

}
