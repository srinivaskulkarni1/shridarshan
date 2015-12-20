package com.shridarshan.in.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.datastax.driver.core.policies.DefaultRetryPolicy;
import com.datastax.driver.core.policies.TokenAwarePolicy;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.shridarshan.in.pojo.PojoFactory;
import com.shridarshan.in.pojo.Temple;
import com.shridarshan.in.util.BeanConstants;

public class DataLoader implements ApplicationContextAware {

	private AbstractApplicationContext context;
	private Cluster cluster;
	private Session session;

	public List<Temple> getTempleList() {

		connect();

		PojoFactory pojoFactory = (PojoFactory) context
				.getBean(BeanConstants.POJO_FACTORY);
		List<Temple> templeList = new ArrayList<Temple>();

		Statement select = QueryBuilder.select().all().from("temple");
		ResultSet results = session.execute(select);

		for (Row row : results) {
			Temple temple = pojoFactory.getTemplePojo();
			temple.setGod(row.getString("god"));
			temple.setDistrict(row.getString("district"));
			temple.setPlace(row.getString("place"));
			temple.setState(row.getString("state"));
			templeList.add(temple);
		}
		return templeList;

	}

	@SuppressWarnings("deprecation")
	private void connect() {
		cluster = Cluster
				.builder()
				.addContactPoint("localhost")
				.withRetryPolicy(DefaultRetryPolicy.INSTANCE)
				.withLoadBalancingPolicy(
						new TokenAwarePolicy(new DCAwareRoundRobinPolicy()))
				.build();
		session = cluster.connect("shridarshan");

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context = (AbstractApplicationContext) applicationContext;
	}
}
