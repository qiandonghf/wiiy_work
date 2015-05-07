package com.wiiy.hibernate;

import org.hibernate.cfg.DefaultNamingStrategy;
import org.hibernate.annotations.common.util.StringHelper;

public class ModulePrefixNamingStrategy extends DefaultNamingStrategy {

	private static final long serialVersionUID = -2940117483017525109L;

	private static String addUnderscores(String name) {
		StringBuilder buf = new StringBuilder(name.replace('.', '_'));
		for (int i = 1; i < buf.length() - 1; i++) {
			if ('_' != buf.charAt(i - 1) && Character.isUpperCase(buf.charAt(i)) && !Character.isUpperCase(buf.charAt(i + 1))) {
				buf.insert(i++, '_');
			}
		}
		return buf.toString().toLowerCase();
	}

	private static String getModuleName(String className) {
		int secondDotIndex = className.indexOf('.', className.indexOf('.') + 1);
		int thirdDotIndex = className.indexOf('.', secondDotIndex + 1);
		return className.substring(secondDotIndex + 1, thirdDotIndex).toUpperCase();
	}
	
	public static String classToTableName(Class<?> clazz) {
		String className = clazz.getName();
		return getModuleName(className) + "_" + addUnderscores(StringHelper.unqualify(className));
	}

	@Override
	public String classToTableName(String className) {
		return getModuleName(className) + "_" + addUnderscores(StringHelper.unqualify(className));
	}

	@Override
	public String propertyToColumnName(String propertyName) {
		return addUnderscores(StringHelper.unqualify(propertyName));
	}

	@Override
	public String tableName(String tableName) {
		return tableName;
	}

	@Override
	public String columnName(String columnName) {
		return columnName;
	}

	@Override
	public String foreignKeyColumnName(String propertyName, String propertyEntityName, String propertyTableName, String referencedColumnName) {
		if (propertyName != null) {
			return addUnderscores(propertyName) + "_id";
		} else {
			return addUnderscores(propertyTableName) + "_id";
		}
	}

	@Override
	public String collectionTableName(String ownerEntity, String ownerEntityTable, String associatedEntity, String associatedEntityTable, String propertyName) {
		return super.collectionTableName(ownerEntity, ownerEntityTable, associatedEntity, associatedEntityTable, propertyName);
	}

	@Override
	public String joinKeyColumnName(String joinedColumn, String joinedTable) {
		return super.joinKeyColumnName(joinedColumn, joinedTable);
	}

	@Override
	public String logicalColumnName(String columnName, String propertyName) {
		return super.logicalColumnName(columnName, propertyName);
	}

	@Override
	public String logicalCollectionTableName(String tableName, String ownerEntityTable, String associatedEntityTable, String propertyName) {
		return super.logicalCollectionTableName(tableName, ownerEntityTable, associatedEntityTable, propertyName);
	}

	@Override
	public String logicalCollectionColumnName(String columnName, String propertyName, String referencedColumn) {
		return super.logicalCollectionColumnName(columnName, propertyName, referencedColumn);
	}
}
