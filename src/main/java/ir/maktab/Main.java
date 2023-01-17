package ir.maktab;

import ir.maktab.data.model.entity.SubJob;

import java.io.IOException;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws IOException {
        SubJobRepository subJobRepository=SubJobRepository.getInstance();
       SubJob basicJob=new SubJob();
        basicJob.setNameBase("latry");
        subJobRepository.saveone(basicJob);
        Optional<SubJob>subJob = subJobRepository.getById(1L);
        subJob.get().setSubJobName("capter");
        subJobRepository.update1(subJob.get());
  /*      List<SubJob> basicJobList=subJobRepository.getAll();
        basicJobList.forEach(basicJob1 -> System.out.println(basicJob1.getNameBase()));*/

    }


}