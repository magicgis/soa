package com.yeepay.g3.core.soa.center;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.jmock.Mockery;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.yeepay.g3.unittest.ScriptRunnerWrapper;

/**
 *
 * @author：wang.bao
 * @since：2014年8月19日 上午11:23:57
 * @version:
 */
@ContextConfiguration(locations = { "classpath:/testContext.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class BaseBizTest extends AbstractJUnit4SpringContextTests {
	private static ApplicationContext context = null;
	static {
		// 禁用远程调用组件
		// System.setProperty("disablermi","true");
		System.setProperty("mockrmi", "true");
		System.setProperty("mockconfig", "true");
		System.setProperty("disablemsg", "true");
		System.setProperty("disablecache", "true");
	}

	private static ScriptRunnerWrapper scriptRunnerWrapper;

	protected Mockery mockery = new Mockery();

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	@BeforeClass
	public static void initTestData() throws IOException, SQLException {
		context = new ClassPathXmlApplicationContext("/testContext.xml");
		scriptRunnerWrapper = (ScriptRunnerWrapper) context
				.getBean("scriptRunnerWrapper");
		if (scriptRunnerWrapper != null) {
			scriptRunnerWrapper.initTestData();
		}
	}

	@AfterClass
	public static void afterDaoTest() {
		if (scriptRunnerWrapper != null) {
			scriptRunnerWrapper.release();
		}
	}

	public void save(String sql) {
		jdbcTemplate.execute(sql);
	}

	public void update(String sql) {
		jdbcTemplate.execute(sql);
	}

	public void delete(String sql) {
		jdbcTemplate.execute(sql);
	}

	/**
	 * List 里的每个对应是一个Map
	 * */
	@SuppressWarnings("rawtypes")
	public List query(String sql) {
		return jdbcTemplate.queryForList(sql);
	}

	protected static Object getObjectByIoc(String oname) {
		if (context == null) {
			context = new ClassPathXmlApplicationContext("/testContext.xml");
		}
		return context.getBean(oname);
	}
}
