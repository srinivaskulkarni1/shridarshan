package com.shridarshan.in.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.datastax.driver.core.policies.DefaultRetryPolicy;
import com.datastax.driver.core.policies.TokenAwarePolicy;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.mapping.MappingManager;
import com.shridarshan.in.util.DBConstants;

public class DBConnection implements IDBConnection {

	private Cluster cluster;
	private Session session;
	private MappingManager manager;
	private static Logger LOGGER = LoggerFactory.getLogger(DBConnection.class);

	@Override
	public MappingManager getManager() {
		return manager;
	}

	@Override
	public Session getSession() {
		if (session == null) {
			LOGGER.debug("Initializing {}.Session",
					DBConnection.class.getSimpleName());
			connect();
			LOGGER.debug("Initialized {}.Session",
					DBConnection.class.getSimpleName());
		}
		return session;
	}

	@Override
	@SuppressWarnings("deprecation")
	public void connect() {

		if (cluster == null) {
			LOGGER.info("Initializing database on host | {} | Keyspace | {}",
					"localhost", DBConstants.KEYSPACE);
			cluster = Cluster
					.builder()
					.addContactPoint("localhost")
					.withRetryPolicy(DefaultRetryPolicy.INSTANCE)
					.withLoadBalancingPolicy(
							new TokenAwarePolicy(new DCAwareRoundRobinPolicy()))
					.build();
			session = cluster.connect(DBConstants.KEYSPACE);
			manager = new MappingManager(session);
		}
		LOGGER.info("Connected to cluster on host | {} | Keyspace | {}",
				"localhost", DBConstants.KEYSPACE);

	}

	public ResultSet getResultSet(String tableName) {
		LOGGER.info("Processing {}.getResultSet | table | {}",
				DBConnection.class.getSimpleName(), tableName);
		Statement select = QueryBuilder.select().all().from(tableName);
		ResultSet rs;
		rs = getSession().execute(select);
		LOGGER.info(
				"Processed {}.getResultSet | table | {} | rows returned | {}",
				DBConnection.class.getSimpleName(), tableName, rs.all().size());
		return rs;

	}

}
