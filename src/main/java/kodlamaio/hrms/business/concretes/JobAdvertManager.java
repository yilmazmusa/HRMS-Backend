package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import kodlamaio.hrms.business.abstracts.JobAdvertService;
import kodlamaio.hrms.business.businessRules.BusinessRules;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.CityDao;
import kodlamaio.hrms.dataAccess.abstracts.EmployerDao;
import kodlamaio.hrms.dataAccess.abstracts.JobAdvertDao;
import kodlamaio.hrms.entities.concretes.JobAdvert;
import kodlamaio.hrms.entities.dtos.JobAdvertDto;



@Service
public class JobAdvertManager implements JobAdvertService {

	private JobAdvertDao jobAdvertDao;
	private EmployerDao employerDao;
	private CityDao cityDao;

	@Autowired
	public JobAdvertManager(JobAdvertDao jobAdvertDao,EmployerDao employerDao,CityDao cityDao) {
		super();
		this.jobAdvertDao = jobAdvertDao;
		this.employerDao = employerDao;
		this.cityDao = cityDao;
	}
	
	@Override
	public DataResult<List<JobAdvert>> getAll() {
		return new SuccessDataResult<List<JobAdvert>>(this.jobAdvertDao.findAll(),"data listelendi");
	}

	@Override
	public Result add(JobAdvert jobAdvertisement) {
		Result result =BusinessRules.run( employerControl(jobAdvertisement.getEmployer().getId()),
				cityControl(jobAdvertisement.getCity().getId()),				
				salaryControl(jobAdvertisement));		
		if(result.isSuccess()) {				
			jobAdvertDao.save(jobAdvertisement);
			return new SuccessResult("JobAdvert added");			
		}
		return result;
	}

	@Override
	public Result update(JobAdvert jobAdvertisement) {
			jobAdvertDao.save(jobAdvertisement);
			return new SuccessResult("JobAdvert updated");	
	}
	
	@Override
	public Result delete(int jobAdvertId) {
		this.jobAdvertDao.deleteById(jobAdvertId);
		return new SuccessResult("JobAdvert deleted");
	}

	@Override
	public DataResult<List<JobAdvert>> getByIsActiveTrueOrderByDeadlineAsc() {
		return new SuccessDataResult<List<JobAdvert>>(this.jobAdvertDao.getByIsActiveTrueOrderByDeadlineAsc(),"Data listelendi");
	}

	@Override
	public DataResult<List<JobAdvert>> getByisActiveTrueAndEmployerId(int id) {
		return new SuccessDataResult<List<JobAdvert>>(jobAdvertDao.getByIsActiveTrueAndEmployer_Id(id));
	}

	@Override
	public DataResult<List<JobAdvert>> findAllByIsActiveTrue() {
		return new SuccessDataResult<List<JobAdvert>>(jobAdvertDao.findAllByIsActiveTrue());
	}
	
	@Override
	public DataResult<List<JobAdvertDto>> getJobAdvertDetails() {
		return new SuccessDataResult<List<JobAdvertDto>>(this.jobAdvertDao.getJobAdvertDetails());
	}

	

								//METOD KURALLARI  
	
	private Result employerControl(int id) {
		if(!employerDao.existsById(id)) {
			return new ErrorResult("B??yle bir kullan??c?? yok");
		}
		return new SuccessResult();
	}
	
	private Result cityControl(int id) {
		if(!cityDao.existsById(id)) {
			return new ErrorResult("B??yle bir ??ehir yok");
		}
		return new SuccessResult();
	}
	
		
	private Result salaryControl(JobAdvert jobAdvert) {
		if(jobAdvert.getSalaryMin()>=jobAdvert.getSalaryMax()) {
			return new ErrorResult("Minimum maa?? maximum maa??tan b??y??k veya e??it olamaz");
		}
		return new SuccessResult();
	}	

 	
	
	

}
