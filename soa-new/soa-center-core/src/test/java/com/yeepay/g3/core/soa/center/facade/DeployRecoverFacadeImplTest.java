package com.yeepay.g3.core.soa.center.facade;

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

import com.caucho.hessian.client.HessianProxyFactory;
import com.yeepay.g3.facade.soa.center.facade.DeployRecoverFacade;

/**
 * @author by menghao.chen
 */
@ContextConfiguration(locations = {
		"classpath:/soa-center-core-spring/applicationContext-soa-center-datasource.xml",
		"classpath:/testContext-jdbc.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class DeployRecoverFacadeImplTest extends
		AbstractJUnit4SpringContextTests {
	private static DeployRecoverFacade deployRecoverFacade;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@BeforeClass
	public static void before() throws Exception {
		System.setProperty("dbconfigpath", "/apps/dbconfig/");
		HessianProxyFactory factory = new HessianProxyFactory();
		String serviceUrl = "http://localhost:8002/soa-center-hessian/soa/hessian/"
				+ DeployRecoverFacade.class.getName();
		deployRecoverFacade = (DeployRecoverFacade) factory.create(
				DeployRecoverFacade.class, serviceUrl);
		Assert.assertNotNull(deployRecoverFacade);
	}

	@Test
	public void testDeploy() {
		Long id = deployRecoverFacade.deploy("50.1.1.30", "wang.bao",
				"localhost");
		List<?> result = jdbcTemplate
				.queryForList("SELECT * FROM TBL_SOA_DEPLOY_RECORD WHERE ID = "
						+ id);
		Assert.assertNotNull(result);
		Assert.assertTrue(!result.isEmpty());
	}

	@Test
	public void testRecoverAll() {
		try {
			jdbcTemplate
					.execute("INSERT INTO TBL_SOA_DEPLOY_RECORD ( ADDRESS, OPERATOR, REQ_ADDRESS, DEPLOY_TIME, RECOVER_TIME ) VALUES ( '172.17.102.171', 'wang.bao', '172.19.100.100', '2015-12-08 00:00:00', NULL )");

			String sql = "SELECT ID FROM TBL_SOA_DEPLOY_RECORD WHERE RECOVER_TIME IS NULL AND DEPLOY_TIME < CURRENT_TIMESTAMP - 5 MINUTE";
			List<?> result = jdbcTemplate.queryForList(sql);
			Assert.assertNotNull(result);
			Assert.assertTrue(!result.isEmpty());

			deployRecoverFacade.recoverAll();

			Thread.sleep(5000);
			List<?> result2 = jdbcTemplate.queryForList(sql);
			Assert.assertNotNull(result2);
			Assert.assertTrue(result2.isEmpty());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
}
