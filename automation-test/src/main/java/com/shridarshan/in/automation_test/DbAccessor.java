package com.shridarshan.in.automation_test;

import java.util.ArrayList;
import java.util.List;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.shridarshan.in.util.DBConstants;

public class DbAccessor {

	private String action;
	private String table;
	private List<String> values = new ArrayList<>();

	public void setAction(String action) {
		this.action = action;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public boolean status() {
		if (action.equalsIgnoreCase("insert")) {
			return handleInsert();
		} else if (action.equalsIgnoreCase("clear")) {
			return handleDelete();
		}

		return true;
	}

	private boolean handleDelete() {
		String deleteQuery = "Truncate " + table + ";";
		ResultSet resultSet = DbConnection.getSession().execute(deleteQuery);
		return resultSet.wasApplied();
	}

	private boolean handleInsert() {
		if (table.equals(DBConstants.TABLE_TEMPLE)) {
			Session session = DbConnection.getSession();

			PreparedStatement statement = session.prepare(

			"INSERT INTO temple" + "(god, place, district, state)"
					+ "VALUES (?,?,?,?);");

			BoundStatement boundStatement = new BoundStatement(statement);

			ResultSet resultSet = session
					.execute(boundStatement.bind(values.get(0), values.get(1),
							values.get(2), values.get(3)));
			return resultSet.wasApplied();
		}
		return true;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}
}
