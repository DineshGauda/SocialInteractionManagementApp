package com.dinesh.org.SocialInteractionManagementApp.model;

import javax.persistence.*;

@Entity
public class Website
{	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "domain")
	private String domain;
	
	@Column(name = "url")
	private String url;
	
	@Column(name = "socialScore")
	private int socialScore;
	
	public Website() 
	{
		
	}

	public Website(Long id, String domain, String url, int socialScore)
	{
		this.id = id;
		this.domain = domain;
		this.url = url;
		this.socialScore = socialScore;
	}
	
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getDomain()
	{
		return domain;
	}

	public void setDomain(String domain)
	{
		this.domain = domain;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public int getSocialScore()
	{
		return socialScore;
	}

	public void setSocialScore(int socialScore)
	{
		this.socialScore = socialScore;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Website other = (Website) obj;
		if (url == null)
		{
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	
}
