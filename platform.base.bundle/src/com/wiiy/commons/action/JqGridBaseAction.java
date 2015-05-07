package com.wiiy.commons.action;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.DateUtil;
import com.wiiy.commons.util.StringUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Pager;

public abstract class JqGridBaseAction<T> extends BaseAction {
	
	protected Log logger = LogFactory.getLog(getClass());
	
	protected List<T> root = Collections.emptyList();
	protected Integer rows = 0;
	protected Integer page = 0;
	protected Integer total = 0;
	protected Integer records = 0;
	protected String sord;
	protected String sidx;
	protected String filters;
	protected Integer nodeid;
	protected Integer n_level;
	protected boolean tree;
	protected int fetchDepth = 1;

	protected abstract List<T> getListByFilter(Filter fitler);
	
	public String refresh(Filter filter) {
		try {
			if (filters != null && filters.length() > 0) {
				System.out.println("输出filters: "+filters);
				generateSearchCriteriaFromFilters(filter,filters);
			}
			if(tree){
				if(nodeid!=null)filter.eq("parent", nodeid);
				if(n_level==null)n_level=-1;
				filter.eq("level", n_level+1);
			}
			Pager pager = new Pager(page,rows);
			if(page!=0 && rows!=0){
				filter.pager(pager);
			}
			if(sidx!=null && sidx.length()>0 && sord!=null && sord.length()>0){
				filter.orderBy(sidx, sord);
			}
			root = getListByFilter(filter);
			setPropertyEmpty(root,fetchDepth);
			total = pager.getTotal();
			records = pager.getRecords();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSON;
	}
	
	protected void setPropertyEmpty(Collection<?> list,int depth){
		if(list!=null)
		for (Object object : list) {
			setPropertyEmpty(object, depth);
		}
	}
	
	protected void setPropertyEmpty(Object object,int depth){
		if(depth<0) return;
		Field[] fields = object.getClass().getDeclaredFields();
		for (Field field : fields) {
			String name = field.getType().getName();
			if(name.indexOf("DataDict")>-1) continue;
			else if(name.startsWith("com.wiiy") && name.indexOf("entity")>-1){
				try {
					if(depth>0){
						Object obj = object.getClass().getMethod("get"+StringUtil.convertFirstCharToUpperCase(field.getName())).invoke(object);
						if(obj!=null) setPropertyEmpty(obj,depth-1);
					} else {
//						System.out.println(object.getClass().getSimpleName()+"."+field.getName()+" has been set to null");
						object.getClass().getMethod("set"+StringUtil.convertFirstCharToUpperCase(field.getName()),field.getType()).invoke(object,BeanUtil.emptyObject());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (Collection.class.isAssignableFrom(field.getType())){
				assignableFrom(object,field);
			}
		}
	}
	
	protected void assignableFrom(Object object,Field field){
		try {
			object.getClass().getMethod("set"+StringUtil.convertFirstCharToUpperCase(field.getName()),field.getType()).invoke(object,BeanUtil.emptyObject());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void generateSearchCriterion(Filter filter,String propertyName,Object value, String searchOper) {
		if (propertyName != null && value != null && searchOper != null) {
			if ("eq".equals(searchOper)) {
				filter.eq(propertyName, value);
			} else if ("ne".equals(searchOper)) {
				filter.ne(propertyName, value);
			} else if ("lt".equals(searchOper)) {
				filter.lt(propertyName, value);
			} else if ("le".equals(searchOper)) {
				filter.le(propertyName, value);
			} else if ("gt".equals(searchOper)) {
				filter.gt(propertyName, value);
			} else if ("ge".equals(searchOper)) {
				filter.ge(propertyName, value);
			} else if ("bw".equals(searchOper)) {
				filter.like(propertyName, String.valueOf(value), Filter.START);
			} else if ("bn".equals(searchOper)) {
				filter.not(Filter.Like(propertyName, String.valueOf(value), Filter.START));
			} else if ("ew".equals(searchOper)) {
				filter.like(propertyName, String.valueOf(value), Filter.END);
			} else if ("en".equals(searchOper)) {
				filter.not(Filter.Like(propertyName, String.valueOf(value), Filter.END));
			} else if ("cn".equals(searchOper)) {
				filter.like(propertyName, String.valueOf(value), Filter.ANYWHERE);
			} else if ("nc".equals(searchOper)) {
				filter.not(Filter.Like(propertyName, String.valueOf(value), Filter.ANYWHERE));
			} else if ("nn".equals(searchOper)) {
				filter.notNull(propertyName);
			} else if ("in".equals(searchOper)) {
				filter.in(propertyName, (Object[])value);
			} else if ("ni".equals(searchOper)) {
				filter.notIn(propertyName, (Object[])value);
			}
		}
	}
	
	public String serachByLikeFromFilters(String keywords,String filters){
		JSONObject jsonObject = JSONObject.fromObject(filters);
		JSONArray rules = jsonObject.getJSONArray("rules");
		System.out.println("rules:"+rules);
		int i = 0;
		int j = 0;
		String value=null;
		boolean flag = false;
		for (Object obj : rules) {
			JSONObject rule = (JSONObject) obj;
			String field = rule.getString("field");
			if(keywords.equals(field)){
				flag = true;
				j = i;
				value = rule.getString("data");
			}
			i++;
			if(flag){
				rules.remove(j);
				filters = "{'rules':"+rules.toString()+"}";
				this.filters=filters;
				return value;
			}
		}
		return null;
	}
	private void generateSearchCriteriaFromFilters(Filter filter,String filters) {
		if(filters==null || filters.length()==0)return;
		JSONObject jsonObject = JSONObject.fromObject(filters);
		JSONArray rules = jsonObject.getJSONArray("rules");
		for (Object obj : rules) {
			JSONObject rule = (JSONObject) obj;
			String field = rule.getString("field");
			/*if(field.indexOf(".")>0){
				String alias = field.substring(0,field.indexOf("."));
				filter.createAlias(alias, alias);
				filter.like();
			}*/
			String op = rule.getString("op");
			if(rule.has("dataType")) {
				String dataType = rule.getString("dataType");
				Object value = null;
				if(dataType.equalsIgnoreCase("int")){
					if(op.equals("in") || op.equals("ni")){
						String ids = rule.getString("data");
						value = StringUtil.splitToIntegerArray(ids);
					} else {
						value = rule.getInt("data");
					}
				} else if(dataType.equalsIgnoreCase("boolean")){
					value = rule.getBoolean("data");
				} else if(dataType.equalsIgnoreCase("double")){
					value = rule.getDouble("data");
				} else if(dataType.equalsIgnoreCase("java.math.bigdecimal")){
					String dataValue = rule.getString("data");
					value = BigDecimal.valueOf(Double.parseDouble(dataValue));
				}else if(dataType.equalsIgnoreCase("long")){
					if(op.equals("in") || op.equals("ni")){
						String ids = rule.getString("data");
						value = StringUtil.splitToLongArray(ids);
					} else {
						value = rule.getLong("data");
					}
				} else if(dataType.equalsIgnoreCase("string")){
					value = rule.getString("data");
				} else if(dataType.equalsIgnoreCase("java.util.Date")){
					String dataValue = rule.getString("data");
					value = DateUtil.parse(dataValue);
				}else {
					try {
						if(Class.forName(dataType).getGenericSuperclass().toString().startsWith("java.lang.Enum")){
							String dataValue = rule.getString("data");
							Enum<?>[] enums = (Enum[]) Class.forName(dataType).getEnumConstants();
							for (int i = 0; i < enums.length; i++) {
								if(enums[i].name().equals(dataValue)){
									value = enums[i];
									break;
								}
							}
						} else {
							value = rule.get("data");
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
				generateSearchCriterion(filter,field, value, op);
			} else {
				generateSearchCriterion(filter,field, rule.get("data"), op);
			}
		}
	}
	
	public List<T> getRoot() {return root;}
	public void setRoot(List<T> root) {this.root = root;}
	public Integer getRows() {return rows;}
	public void setRows(Integer rows) {this.rows = rows;}
	public Integer getPage() {return page;}
	public void setPage(Integer page) {this.page = page;}
	public Integer getTotal() {return total;}
	public void setTotal(Integer total) {this.total = total;}
	public Integer getRecords() {return records;}
	public void setRecords(Integer records) {this.records = records;}
	public String getSord() {return sord;}
	public void setSord(String sord) {this.sord = sord;}
	public String getSidx() {return sidx;}
	public void setSidx(String sidx) {this.sidx = sidx;}
	public String getFilters() {return filters;}
	public void setFilters(String filters) {this.filters = filters;}
	public Integer getNodeid() {return nodeid;}
	public void setNodeid(Integer nodeid) {this.nodeid = nodeid;}
	public Integer getN_level() {return n_level;}
	public void setN_level(Integer n_level) {this.n_level = n_level;}
	public boolean isTree() {return tree;}
	public void setTree(boolean tree) {this.tree = tree;}
}