package com.yeepay.g3.core.soa.center.facade;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;

import com.caucho.hessian.client.HessianProxyFactory;
import com.yeepay.g3.facade.soa.center.facade.AddressFacade;
import com.yeepay.g3.facade.soa.center.facade.DeployRecoverFacade;

/**
 * @author by menghao.chen
 */
@ContextConfiguration(locations = {
		"classpath:/soa-center-core-spring/applicationContext-soa-center-datasource.xml",
		"classpath:/testContext-jdbc.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class AddressFacadeImplTest extends AbstractJUnit4SpringContextTests {
	private static AddressFacade addressFacade;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@BeforeClass
	public static void before() throws Exception {
		System.setProperty("dbconfigpath", "/apps/dbconfig/");
		HessianProxyFactory factory = new HessianProxyFactory();
		String serviceUrl = "http://localhost:8002/soa-center-hessian/soa/hessian/"
				+ DeployRecoverFacade.class.getName();
		addressFacade = (AddressFacade) factory.create(AddressFacade.class,
				serviceUrl);
		Assert.assertNotNull(addressFacade);
	}

	@Test
	public void testAddressMatch() {
		AntPathMatcher matcher = new AntPathMatcher();
		Assert.assertTrue(matcher.match("172.31.47.2", "172.31.47.2"));
		Assert.assertTrue(matcher.match("*.47.2", "172.31.47.2"));
		Assert.assertTrue(matcher.match("*.47.*", "172.31.47.2"));
		Assert.assertTrue(matcher.match("172.31.47.*", "172.31.47.2"));
		Assert.assertTrue(matcher.match("172.31.47.2*", "172.31.47.2"));
		Assert.assertFalse(matcher.match("172.31.47.2:*", "172.31.47.2"));
	}
}
