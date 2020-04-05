package WebComponent.Service.Services;

import ORM.POJO.Ncov;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface NcovService {
	int insertAll(List<Ncov> ncovs);

	void setLastUpdateTime();

	Date getLastUpdateTime() throws ParseException;

	int insert(Ncov ncov);

	List<Ncov> getWorldNcovDataBy(Date date);
}
