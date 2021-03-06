package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.CityService;
import kodlamaio.hrms.business.businessRules.BusinessRules;
import kodlamaio.hrms.core.utilities.results.DataResult;
import kodlamaio.hrms.core.utilities.results.ErrorResult;
import kodlamaio.hrms.core.utilities.results.Result;
import kodlamaio.hrms.core.utilities.results.SuccessDataResult;
import kodlamaio.hrms.core.utilities.results.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.CityDao;
import kodlamaio.hrms.entities.concretes.City;

@Service
public class CityManager implements CityService {

	private CityDao cityDao;

	@Autowired
	public CityManager(CityDao cityDao) {
		super();
		this.cityDao = cityDao;
	}

	@Override
	public DataResult<List<City>> getAll() {
		return new SuccessDataResult<List<City>>(this.cityDao.findAll(), "Data listelendi");
	}

	@Override
	public Result add(City city) {
		Result result = BusinessRules.run(cityNameExists(city));
		if (result.isSuccess()) {
			String cityNameUpperCase = city.getName().toUpperCase();
			city.setName(cityNameUpperCase);
			this.cityDao.save(city);
			return new SuccessResult("City added");
		}
		return result;
	}

	@Override
	public Result update(City city) {
		Result result = BusinessRules.run(cityNameExists(city));
		if (result.isSuccess()) {
			this.cityDao.save(city);
			return new SuccessResult("City updated");
		}
		return result;
	}

	@Override
	public Result delete(int cityId) {
			this.cityDao.deleteById(cityId);;
			return new SuccessResult("City deleted");
	}
			
			
			
			
									//METOD KURALLARI  
			
			private Result cityNameExists(City city) {
				if(cityDao.findAllByName(city.getName().toUpperCase()).stream().count()!=0) {
					return new ErrorResult("This CityName is available");
				}
				return new SuccessResult();
			}
	
	
}
