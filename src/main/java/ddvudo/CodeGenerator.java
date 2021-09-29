package ddvudo;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述: 代码生成器
 *
 * @author zhouwei
 * @date 2019-06-11 09:00:34
 */
public class CodeGenerator {

	public static void main(String[] args) {
		String[] tables = {"sys_file"};
		String moduleName = "";
		boolean isOverwrite = false;
		boolean isXml = true;
		boolean isController = false;
		boolean isDaoAndService = true;
		generate(tables, isOverwrite, isXml, isDaoAndService, isController, moduleName);
	}

	/**
	 * 功能描述: 可根据表名生成entity/dao/xml/service/serviceImpl/controller
	 * <p>模板在resources/templates/generator下，可重写模板</p>
	 * <p>更多自定义参考mybatis-plus代码生成器官方文档</p>
	 *
	 * @param tables       需要生成的表名
	 * @param isOverwrite  是否覆盖已经存在的文件
	 * @param isXml        是否生成mapper.xml文件
	 * @param isController 是否生成Controller类
	 * @param module       模块名，没有传空
	 */
	public static void generate(String[] tables, boolean isOverwrite, boolean isXml, boolean isDaoAndService, boolean isController, String module) {
		String projectPath = System.getProperty("user.dir").replaceAll("\\\\", "/");
		AutoGenerator generator = new AutoGenerator();

		GlobalConfig globalConfig = new GlobalConfig();
		globalConfig.setOutputDir(projectPath + "/src/main/java");
		globalConfig.setAuthor("ddvudo");
		globalConfig.setOpen(false);
		globalConfig.setFileOverride(isOverwrite);
		globalConfig.setMapperName("%sDao");
		globalConfig.setServiceName("%sService");
		globalConfig.setServiceImplName("%sServiceImpl");
		generator.setGlobalConfig(globalConfig);

		DataSourceConfig dataSourceConfig = new DataSourceConfig();
		dataSourceConfig.setUrl("jdbc:postgresql://ddvudo.buzz:5432/how2jdb?characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false&allowMultiQueries=true&useSSL=false&serverTimezone=GMT%2B8");
		// dataSourceConfig.setSchemaName("");
		dataSourceConfig.setDriverName("org.postgresql.Driver");
		dataSourceConfig.setUsername("dev");
		dataSourceConfig.setPassword("dev");
		generator.setDataSource(dataSourceConfig);

		PackageConfig packageConfig = new PackageConfig();
		packageConfig.setModuleName(module);
		packageConfig.setParent("ddvudo");
		packageConfig.setMapper("ORM.Mapper");
		packageConfig.setEntity("POJO");
		packageConfig.setController("Controller");
		packageConfig.setService("Service.Services");
		packageConfig.setService("Service");
		generator.setPackageInfo(packageConfig);

		InjectionConfig injectionConfig = new InjectionConfig() {
			@Override
			public void initMap() {
			}
		};

		List<FileOutConfig> fileOutConfigs = new ArrayList<>();
		// xml
		if (isXml) {
			fileOutConfigs.add(new FileOutConfig("/generator/mapper.xml.ftl") {
				@Override
				public String outputFile(TableInfo tableInfo) {
					return projectPath + "/src/main/resources/ddvudo/ORM/Mapper/" + packageConfig.getModuleName()
							+ "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
				}
			});
		}

		injectionConfig.setFileOutConfigList(fileOutConfigs);
		generator.setCfg(injectionConfig);

		TemplateConfig templateConfig = new TemplateConfig();
		String templateBase = "/generator/";
		templateConfig.setXml(null);
		templateConfig.setEntity(templateBase + ".java");
		if (isDaoAndService) {
			templateConfig.setMapper(templateBase + "Mapper.java");
			templateConfig.setService(templateBase + "service.java");
			templateConfig.setServiceImpl(templateBase + "serviceImpl.java");
		} else {
			templateConfig.setMapper(null);
			templateConfig.setService(null);
			templateConfig.setServiceImpl(null);
		}

		if (isController) {
			templateConfig.setController(templateBase + "controller.java");
		} else {
			templateConfig.setController(null);
		}
		generator.setTemplate(templateConfig);

		StrategyConfig strategy = new StrategyConfig();
		strategy.setNaming(NamingStrategy.underline_to_camel);
		strategy.setColumnNaming(NamingStrategy.underline_to_camel);
		strategy.setEntityLombokModel(true);
		strategy.setRestControllerStyle(true);
		strategy.setInclude(tables);
		strategy.setRestControllerStyle(true);
		strategy.entityTableFieldAnnotationEnable(true);
//		strategy.setTablePrefix("t", "sys");
		generator.setStrategy(strategy);


		generator.setTemplateEngine(new FreemarkerTemplateEngine());

		generator.execute();
	}
}
