package com.dinesh.org.SocialInteractionManagementApp.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import com.dinesh.org.SocialInteractionManagementApp.model.Website;
import com.dinesh.org.SocialInteractionManagementApp.util.HibernateUtil;

@Repository
public class WebsiteDaoImpl implements WebsiteDao
{
	static Logger logger = Logger.getLogger(WebsiteDaoImpl.class);

	@Override
	public Boolean addWebsite(Website webSite)
	{
		Transaction transaction = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try
		{
			transaction = session.beginTransaction();
			session.save(webSite);
			session.getTransaction().commit();
		} catch (RuntimeException e)
		{
			if (transaction != null)
			{
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		} finally
		{
			session.close();
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Website> listAllRecords()
	{
		List<Website> websites = new ArrayList<Website>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try
		{
			session.beginTransaction();
			websites = session.createQuery("from Website").list();
		} catch (RuntimeException e)
		{
			e.printStackTrace();
		} finally
		{
			session.close();
		}
		return websites;
	}

	@SuppressWarnings("unchecked")
	private List<Website> findByProperty(String propertyName, String value)
	{
		logger.debug("finding User instance with property: " + propertyName + ", value: " + value);
		Session session = HibernateUtil.getSessionFactory().openSession();
		try
		{
			String queryString = "from Website as url where url." + propertyName + "= ?";

			session.beginTransaction();
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re)
		{
			logger.error("find by property name failed", re);
			throw re;
		}
	}

	@Override
	public Website findByUrl(String url)
	{
		if(findByProperty("url", url).size() > 0)
			return findByProperty("url", url).get(0);
		return null;
	}

	@Override
	public boolean deleteWebsite(String url)
	{
		Transaction transaction = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try
		{
			Website website = findByUrl(url);
			transaction = session.beginTransaction();
			session.delete(website);
			session.getTransaction().commit();
			logger.debug("delete successful");

		} catch (RuntimeException e)
		{
			if (transaction != null)
			{
				transaction.rollback();
			}
			logger.error("delete failed", e);
			e.printStackTrace();
			return false;
		} finally
		{
			session.close();
		}
		return true;
	}
}
