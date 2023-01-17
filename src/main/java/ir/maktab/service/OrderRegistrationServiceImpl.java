package ir.maktab.service;

import ir.maktab.data.model.entity.BasicJob;
import ir.maktab.data.model.entity.SubJob;

import javax.swing.plaf.PanelUI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderRegistrationServiceImpl {
    BasicJobService basicJobService=new BasicJobsService();
    public Set<String> findAllBasicJobs(){
       return basicJobService.getAll().stream().map(BasicJob::getNameBase).collect(Collectors.toSet());
    }

    public List<SubJob>findAllSubJobs(String nameBasicJob){
        List<SubJob>subJobList = basicJobService.getAll();
        return subJobList.stream().filter(subJob -> subJob.getNameBase().equals(nameBasicJob)).collect(Collectors.toList());
    }

}
