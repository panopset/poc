package com.panopset.demo.data.test;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.panopset.demo.bean.Store;
import com.panopset.demo.dao.StoreDAO;

/**
 * 
 * Note that, instead of using DispatcherConfig, we use use ContextHsqldb in
 * testing.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = ContextHsqldb.class)
public class StoreAccessTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	protected StoreDAO storeDAO;

	@Before
	public void init() throws SQLException {
		executeSqlScript("schema.sql", false);
	}

	@Test
	public void test() {
		Store store = new Store();
		store.setName("StoreA");
		store.setType("Retail");
		store.setAddress("100 Northpoint");

		storeDAO.insertStore(store);

		store = new Store();
		store.setName("StoreB");
		store.setType("Warehouse");
		store.setAddress("90 Blue Ravine");

		storeDAO.insertStore(store);

        for (Store storeFromList : storeDAO.getAllStores()) {
        	System.out.println(storeFromList.toString());
        }
		store = storeDAO.getStore("StoreA");

		Assert.assertEquals("Retail", store.getType());
	}
}
