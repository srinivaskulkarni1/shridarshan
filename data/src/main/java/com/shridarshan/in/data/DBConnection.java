package com.shridarshan.in.data;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.datastax.driver.core.policies.DefaultRetryPolicy;
import com.datastax.driver.core.policies.TokenAwarePolicy;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.shridarshan.in.util.DBConstants;

public class DBConnection implements IDBConnection {

	private Cluster cluster;
	private Session session;

	@Override
	public Session getSession() {
		if (session == null) {
			connect();
		}
		return session;
	}

	@Override
	@SuppressWarnings("deprecation")
	public void connect() {

		if (cluster == null) {
			cluster = Cluster
					.builder()
					.addContactPoint("localhost")
					.withRetryPolicy(DefaultRetryPolicy.INSTANCE)
					.withLoadBalancingPolicy(
							new TokenAwarePolicy(new DCAwareRoundRobinPolicy()))
					.build();
			session = cluster.connect(DBConstants.KEYSPACE);
		}

	}

	public ResultSet getResultSet(String tableName) {
		Statement select = QueryBuilder.select().all().from(tableName);
		return getSession().execute(select);

	}
}
