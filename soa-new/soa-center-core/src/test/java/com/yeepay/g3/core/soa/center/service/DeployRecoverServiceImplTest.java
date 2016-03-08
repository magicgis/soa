package com.yeepay.g3.core.soa.center.service;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.yeepay.g3.facade.soa.center.dto.DeployRecordDTO;

/**
 * @author by menghao.chen
 */
@ContextConfiguration(locations = {
		"classpath:/soa-center-core-spring/applicationContext-soa-center-datasource.xml",
		"classpath:/testContext-jdbc.xml",
		"classpath:/soa-center-core-spring/applicationContext-soa-center-dao.xml",
		"classpath:/soa-center-core-spring/applicationContext-soa-center-query.xml",
		"classpath:/soa-center-core-spring/applicationContext-soa-center-service.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class DeployRecoverServiceImplTest extends
		AbstractJUnit4SpringContextTests {
	@Autowired
	private DeployRecoverService deployRecoverService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@BeforeClass
	public static void before() {
		System.setProperty("dbconfigpath", "/apps/dbconfig/");
	}

	@Test
	public void testDeployAndFind() {
		DeployRecordDTO record = new DeployRecordDTO();
		record.setAddress("50.1.1.29");
		record.setOperator("wang.bao");
		record.setReqAddress("127.0.0.1");
		record.setDeployTime(new Date());
		Long id = deployRecoverService.deploy(record);
		Assert.assertNotNull(id);
		DeployRecordDTO record2 = deployRecoverService.findById(id);
		Assert.assertNotNull(record2);
	}

	@Test
	public void testFindAllUnRecovered() {
		jdbcTemplate
				.execute("INSERT INTO TBL_SOA_DEPLOY_RECORD ( ADDRESS, OPERATOR, REQ_ADDRESS, DEPLOY_TIME, RECOVER_TIME ) VALUES ( '172.17.102.171', 'wang.bao', '172.19.100.100', '2015-12-08 00:00:00', NULL )");
		List<Long> record2 = deployRecoverService.findAllUnRecovered();
		Assert.assertNotNull(record2);
		Assert.assertTrue(!record2.isEmpty());
	}
}
