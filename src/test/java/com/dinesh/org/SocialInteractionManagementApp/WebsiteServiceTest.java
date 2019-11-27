package com.dinesh.org.SocialInteractionManagementApp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.dinesh.org.SocialInteractionManagementApp.dao.WebsiteDao;
import com.dinesh.org.SocialInteractionManagementApp.dao.WebsiteDaoImpl;
import com.dinesh.org.SocialInteractionManagementApp.model.Website;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WebsiteServiceTest
{

	private WebsiteDao dao = new WebsiteDaoImpl();

	@Before
	public void setUp() throws Exception
	{
		System.out.println("setup");
	}

	@After
	public void clean()
	{
		System.out.println("clean after");
	}

	@Test
	public void testAddWebsite()
	{
		Website website = new Website();
		website.setDomain("rte.ie");
		website.setUrl("http://www.rte.ie/news/politics/2018/1004/1001034-cso/");
		website.setSocialScore(20);
		assertTrue(dao.addWebsite(website));
	}

	@Test
	public void testDeleteWebsite()
	{
		assertTrue(dao.deleteWebsite("http://www.rte.ie/news/politics/2018/1004/1001034-cso/"));
	}
	
	@Test
	public void testGetWebsiteByUrl()
	{
		Website website = new Website();
		website.setDomain("rte.ie");
		website.setUrl("http://www.rte.ie/news/politics/2018/1004/1001034-cso/");
		website.setSocialScore(20);
		dao.addWebsite(website);
		assertEquals(dao.findByUrl("http://www.rte.ie/news/politics/2018/1004/1001034-cso/"), website);
	}
	
	@Test
	public void testGetWrongWebsiteByUrl()
	{
		Website website = new Website();
		website.setDomain("rte.ie");
		website.setUrl("https://www.rte.ie/news/ulster/2018/1004/1000952-moanghan-mine/");
		website.setSocialScore(20);
		dao.addWebsite(website);
		assertFalse(website.equals(dao.findByUrl("http://www.rte.ie/news/politics/2018/1004/1001034-cso/")));
	}
}
