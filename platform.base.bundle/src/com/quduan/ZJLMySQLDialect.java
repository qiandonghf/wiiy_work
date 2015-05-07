package com.quduan;
import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQLDialect;
import org.hibernate.type.StringType;
import org.hibernate.type.TextType;
public class ZJLMySQLDialect extends MySQLDialect{
	public static final StringType STRING_TYPE = new StringType() ;
	public static final TextType TEXT_TYPE = new TextType() ;
	 public ZJLMySQLDialect () {   
         super();   
         registerHibernateType(Types.DECIMAL, TEXT_TYPE.getName());   
         registerHibernateType(-1, STRING_TYPE.getName());
     }   


}
