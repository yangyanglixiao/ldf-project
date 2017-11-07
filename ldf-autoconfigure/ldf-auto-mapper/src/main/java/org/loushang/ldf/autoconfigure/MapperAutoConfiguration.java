package org.loushang.ldf.autoconfigure;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.ibatis.session.SqlSessionFactory;
import org.loushang.framework.mybatis.mapper.EntityMapper;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import tk.mybatis.mapper.mapperhelper.MapperHelper;

@Configuration
@ConditionalOnBean(SqlSessionFactory.class)
@EnableConfigurationProperties(MapperProperties.class)
@AutoConfigureAfter(MybatisAutoConfiguration.class)
public class MapperAutoConfiguration {

	@Autowired
	private List<SqlSessionFactory> sqlSessionFactoryList;
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private MapperProperties properties;

	@PostConstruct
	public void addPageInterceptor() {
		MapperHelper mapperHelper = new MapperHelper();
		mapperHelper.setConfig(properties);
		if (properties.getMappers().size() > 0) {
			for (Class<?> mapper : properties.getMappers()) {
				// 提前初始化MapperFactoryBean,注册mappedStatements
				applicationContext.getBeansOfType(mapper);
				mapperHelper.registerMapper(mapper);
			}
		} else {
			// 提前初始化MapperFactoryBean,注册mappedStatements
			// applicationContext.getBeansOfType(Mapper.class);
			// mapperHelper.registerMapper(Mapper.class);
			applicationContext.getBeansOfType(EntityMapper.class);
			mapperHelper.registerMapper(EntityMapper.class);
		}
		for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
			mapperHelper.processConfiguration(sqlSessionFactory.getConfiguration());
		}
	}
}
