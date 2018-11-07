package com.iykno.modeling.common;

import java.util.ArrayList;
import java.util.List;

import com.iykno.modeling.bean.ModelingConfig;
import com.iykno.modeling.bean.TableModel;

/**
 * SQL构造器
 * 
 * @author iykno
 *
 */
public class SQLBuilder {

	/**
	 * 
	 * @param c
	 * @return
	 */
	public static String buildMain(ModelingConfig c) {
		StringBuilder sGetSQL = new StringBuilder();
		sGetSQL.append(" select ");
		sGetSQL.append(c.getMainColumn());
		sGetSQL.append(" from ");
		sGetSQL.append(c.getMainTable());
		return sGetSQL.toString();
	}

	/**
	 * 
	 * @param c
	 * @param startId
	 * @param endId
	 * @return
	 */
	public static String buildMain(ModelingConfig c, int startId, int endId) {
		StringBuilder sGetSQL = new StringBuilder();
		sGetSQL.append("select * from ").append(c.getMainTable()).append(" where ").append(c.getMainColumn())
				.append(" >= ").append(startId).append(" and ").append(c.getMainColumn()).append(" < ").append(endId);
		return sGetSQL.toString();
	}

	/**
	 * 
	 * @param c
	 * @param mainColumnVals
	 * @return
	 */
	public static String buildMain(ModelingConfig c, List<String> mainColumnVals) {
		StringBuilder sGetSQL = new StringBuilder();
		sGetSQL.append("select * from ").append(c.getMainTable()).append(" where ").append(c.getMainColumn())
				.append(" in ( ").append(CollectionUtils.getValConcat(mainColumnVals)).append(") ");
		return sGetSQL.toString();
	}

	/**
	 * 
	 * @param column
	 * @param table
	 * @param columnVals
	 * @return
	 */
	public static List<String> buildSlave(String column, TableModel table, String columnVals) {
		List<String> sqlGroup = new ArrayList<String>();
		StringBuffer sGetSQL = new StringBuffer();
		switch (table.getInitType()) {
		case 0:
			sGetSQL.append("select * from ").append(table.getSlaveTable()).append(" where ").append(column);
			sGetSQL.append(" in (").append(columnVals).append(")");
			sqlGroup.add(sGetSQL.toString());
			break;
		case 1:
			if (table.getSql() != null) {
				sGetSQL.append(table.getSql().getSelect());

				if (isNullOrEmpty(table.getSql().getWhere())) {
					sGetSQL.append(" where 1=1 ");
				} else {
					sGetSQL.append(table.getSql().getWhere());
				}

				// 如果没有特别条件，统一走批量模式
				if (isNullOrEmpty(table.getSql().getOther())) {
					sGetSQL.append(" and ").append(column).append(" in (").append(columnVals).append(")");
					sqlGroup.add(sGetSQL.toString());
					// 走单个查询模式
				} else {
					String[] arr = columnVals.split(",");
					String getSql = sGetSQL.toString();
					for (String columnVal : arr) {
						StringBuffer sCurSQL = new StringBuffer();
						sCurSQL.append(getSql);
						sCurSQL.append(" and ").append(column).append(" = ").append(columnVal).append(" ");
						sCurSQL.append(table.getSql().getOther());
						sqlGroup.add(sCurSQL.toString());
					}

				}
			}
			break;
		default:
			break;
		}

		return sqlGroup;
	}

	private static boolean isNullOrEmpty(Object o) {
		return o == null || String.valueOf(o).trim().length() == 0 || String.valueOf(o).trim().equals("null");
	}

}
