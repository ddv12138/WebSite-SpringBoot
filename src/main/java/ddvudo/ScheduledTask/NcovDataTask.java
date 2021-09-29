package ddvudo.ScheduledTask;

import ddvudo.GlobalUtils.Global;
import ddvudo.ORM.POJO.Ncov;
import ddvudo.Service.Services.NcovService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

@Component
@Profile("server")
public class NcovDataTask {
	//commons-csv官方文档
	//https://commons.apache.org/proper/commons-csv/user-guide.html#Example:_Parsing_an_Excel_CSV_File
	@Value("${file.tmp.location}")
	String location;

	NcovService ncovService;
	RedisTemplate redisTemplate;

	public NcovDataTask(NcovService ncovService, RedisTemplate redisTemplate) {
		this.ncovService = ncovService;
		this.redisTemplate = redisTemplate;
	}

//	@Scheduled(fixedRate = 1000 * 60 * 30, initialDelay = 1 * 60 * 1000)
	public void downloadData() {
		try {
//			Global.downLoadFromUrl("https://raw.githubusercontent.com/canghailan/Wuhan-2019-nCoV/master/Wuhan-2019-nCoV.json", "nCoVData.json", location);
			Global.downLoadFromUrl("https://raw.githubusercontent.com/canghailan/Wuhan-2019-nCoV/master/Wuhan-2019-nCoV.csv", "nCoVData.csv", location);
		} catch (Exception e) {
			Global.logger().error(e);
		}
		try {
			File csvFile = new File(location + "/nCoVData.csv");
			if (csvFile.exists()) {
				Reader reader = new FileReader(csvFile);
				Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(reader);
				SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
				List<Ncov> ncovList = new LinkedList<>();
				for (CSVRecord record : records) {
					Ncov ncov = new Ncov();
					ncov.setProvince(record.isMapped("date") && record.isSet("date") ? record.get("province") : null);
					ncov.setCountrycode(record.isMapped("date") && record.isSet("date") ? record.get("countryCode") : null);
					ncov.setCountry(record.isMapped("date") && record.isSet("date") ? record.get("country") : null);
					ncov.setConfirmed(record.isMapped("date") && record.isSet("date") ? record.get("confirmed") : null);
					ncov.setCitycode(record.isMapped("date") && record.isSet("date") ? record.get("cityCode") : null);
					ncov.setCity(record.isMapped("date") && record.isSet("date") ? record.get("city") : null);
					ncov.setCured(record.isMapped("date") && record.isSet("date") ? record.get("cured") : null);
					ncov.setDate(record.isMapped("date") && record.isSet("date") ? ymd.parse(record.get("date")) : null);
					ncov.setDead(record.isMapped("dead") && record.isSet("dead") ? record.get("dead") : null);
					ncov.setProvincecode(record.isMapped("provinceCode") && record.isSet("provinceCode") ? record.get("provinceCode") : null);
					ncov.setSuspected(record.isMapped("suspected") && record.isSet("suspected") ? record.get("suspected") : null);
					ncovList.add(ncov);
				}
				ncovService.insertAll(ncovList);
			}
		} catch (Exception e) {
			Global.logger().error(e);
		}
	}
}
