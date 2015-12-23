package com.shridarshan.in.automation_test;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.datastax.driver.core.policies.DefaultRetryPolicy;
import com.datastax.driver.core.policies.TokenAwarePolicy;
import com.datastax.driver.core.querybuilder.QueryBuilder;

public class DbConnection {

	private static Cluster cluster;
	private static Session session;
	private static String hostName;
	private static String keyspace;

	public static void setHostName(String hostName) {
		DbConnection.hostName = hostName;
	}

	public static void setKeyspace(String keyspace) {
		DbConnection.keyspace = keyspace;
	}
	
	public static Session getSession() {
		if (session == null) {
			connect();
		}
		return session;
	}

	@SuppressWarnings("deprecation")
	public static void connect() {

		if (cluster == null) {
			cluster = Cluster
					.builder()
					.addContactPoint(hostName)
					.withRetryPolicy(DefaultRetryPolicy.INSTANCE)
					.withLoadBalancingPolicy(
							new TokenAwarePolicy(new DCAwareRoundRobinPolicy()))
					.build();
			session = cluster.connect(keyspace);
		}

	}

	public ResultSet getResultSet(String tableName) {
		Statement select = QueryBuilder.select().all().from(tableName);
		return getSession().execute(select);

	}
}
