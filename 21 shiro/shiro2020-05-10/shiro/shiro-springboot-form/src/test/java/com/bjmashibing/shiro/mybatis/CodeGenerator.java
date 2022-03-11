package com.bjmashibing.shiro.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;

/**
 * 〈一句话功能简述〉<br>
 * 代码生成器测试
 *
 * @author:孙志强
 * @create:2018-06-11 11:07
 * @Modified BY:
 **/

public class CodeGenerator {
	@Test
	public void generateCode() {
		String packageName = "com.bjmashibing.shiro.moduler.system";
		generateByTables( packageName, "sys_module","sys_role","sys_role_module_ref","sys_user","sys_user_role_ref");
	}

	private void generateByTables(boolean serviceNameStartWithI, String packageName, String... tableNames) {
		GlobalConfig config = new GlobalConfig();
		String dbUrl = "jdbc:mysql://127.0.0.1:3306/db_shiro";
		DataSourceConfig dataSourceConfig = new DataSourceConfig();
		dataSourceConfig.setDbType(DbType.MYSQL)
				.setUrl(dbUrl)
				.setUsername("root")
				.setPassword("admin")
				.setDriverName("com.mysql.jdbc.Driver")
				.setTypeConvert(new MySqlTypeConvert() {
					@Override
					public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
						System.out.println("转换类型：" + fieldType);
						//将数据库中datetime转换成date
						if ( fieldType.toLowerCase().contains( "datetime" ) ) {
							return DbColumnType.DATE;
						}
						if ( fieldType.toLowerCase().contains( "timestamp" ) ) {
							return DbColumnType.DATE;
						}
						return (DbColumnType) super.processTypeConvert(globalConfig, fieldType);
					}
				});
		StrategyConfig strategyConfig = new StrategyConfig();
		strategyConfig
				.setCapitalMode(true)
				.setEntityLombokModel(true)
				.setNaming(NamingStrategy.underline_to_camel)
				.setInclude(tableNames).setTablePrefix("base_","test_","sys_","flow_","tbl_","jd_");//修改替换成你需要的表名，多个表名传数组
		config.setActiveRecord(false)
				.setAuthor("孙志强")
				.setEnableCache(false)
				.setOutputDir("D:\\export")
				.setFileOverride(true);
		//user -> UserService, 设置成true: user -> IUserService
		if (!serviceNameStartWithI) {
			config.setServiceName("%sService");
		}
		new AutoGenerator()
				.setTemplateEngine(new FreemarkerTemplateEngine())
				.setGlobalConfig(config)
				.setDataSource(dataSourceConfig)
				.setStrategy(strategyConfig)
				.setPackageInfo(
						new PackageConfig()
								.setParent(packageName)
								.setController("controller")
								.setEntity("entity")
				).execute();
	}

	private void generateByTables(String packageName, String... tableNames) {
		generateByTables(false, packageName, tableNames);
	}


}
