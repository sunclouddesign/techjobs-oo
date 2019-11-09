package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job someJob = jobData.findById(id);
        String jobName = someJob.getName();
        Employer jobEmployer = someJob.getEmployer();
        model.addAttribute("title", "Job Details");
        model.addAttribute("name", jobName);
        model.addAttribute("employer",jobEmployer);
        model.addAttribute("location",someJob.getLocation());
        model.addAttribute("positionType",someJob.getPositionType());
        model.addAttribute("coreCompetency",someJob.getCoreCompetency());

        // Cleaner method (from Github user ngallion):
        //JobFieldType[] fields = JobFieldType.values();
        //model.addAttribute("fields", fields);
        //model.addAttribute("job", jobData.findById(id));

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("title", "Add Job");
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Job");
            model.addAttribute(jobForm);
            return "new-job";
        }

        String aName = jobForm.getName();
        Employer anEmployer = jobData.getEmployers().findById(jobForm.getEmployerId());
        Location aLocation = jobData.getLocations().findById(jobForm.getLocationId());
        PositionType aPositionType = jobData.getPositionTypes().findById(jobForm.getPositionTypeId());
        CoreCompetency aCoreCompetency = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId());

        Job newJob = new Job(aName,anEmployer,aLocation,aPositionType,aCoreCompetency);

        jobData.add(newJob);
        //int id = newJob.getId();

        //Employer jobEmployer = newJob.getEmployer();
        //model.addAttribute("title", "Job Detail");
        //model.addAttribute("name", aName);
        //model.addAttribute("employer",jobEmployer);
        //model.addAttribute("location",newJob.getLocation());
        //model.addAttribute("positionType",newJob.getPositionType());
        //model.addAttribute("coreCompetency",newJob.getCoreCompetency());

        return "redirect:?id=" + (newJob.getId());
        //return "job-detail";

    }


}
