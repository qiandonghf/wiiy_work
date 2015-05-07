package com.wiiy.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;


/**
 * 过滤器
 * @author aswan
 */
public class Filter {
	
	/**---------------------------------------------------------------**/

	/**
	 * 降序
	 */
	public static final  String DESC = "desc";
	/**
	 * 升序
	 */
	public static final String ASC = "asc";
	/**
	 * 模糊查询 字符串在中间位置相当于 "%value%"
	 */
	public static final MatchMode ANYWHERE = MatchMode.ANYWHERE;
	/**
	 * 模糊查询字符串在开始位置相当于"value%"
	 */
	public static final MatchMode START = MatchMode.START;
	/**
	 * 模糊查询字符串在结束位置相当于"%value"
	 */
	public static final MatchMode END = MatchMode.END;
	/**
	 * 模糊查询字符串精确匹配相当于"value"
	 */
	public static final MatchMode EXACT = MatchMode.EXACT;
	
	public static final String JOIN = "join";
	public static final String SELECT = "select";
	
	/**---------------------------------------------------------------**/
	
	private final List<Projection> projectionList = new ArrayList<Projection>();
	private final List<Criterion> criterionList = new ArrayList<Criterion>();
	private final List<Criterion> criterionOrList = new ArrayList<Criterion>();
	private final List<Order> orderList = new ArrayList<Order>();
	private final Map<String,String> fetchModeMap = new HashMap<String,String>();
	private final Map<String,String> aliasMap = new HashMap<String,String>();
	private final List<String> includeFieldList = new ArrayList<String>();
	private Pager pager;
	private Integer maxResults;
	private Integer firstResult;
	private String primaryKeyOrder;
	
	/**---------------------------------------------------------------**/
	
	private Class<?> beanClass;
	public Filter(Class<?> clazz) {
		beanClass = clazz;
	}
	public Filter() {
	}
	public Class<?> getBeanClass() { return beanClass; }
	public Filter setClass(Class<?> clazz){beanClass = clazz;return this;}
	
	/**---------------------------------------------------------------**/
	
	/**
	 * 匹配(pattern:'>'('gt'),'>='('ge'),'='('eq'),'<='('le'),'<'('lt'))
	 */
	public static Criterion Match(String propertyName,Object value,String pattern){
		if(pattern.equals(">") || pattern.equalsIgnoreCase("gt"))
			return Restrictions.gt(propertyName, value);
		else if(pattern.equals(">=") || pattern.equalsIgnoreCase("ge"))
			return Restrictions.ge(propertyName, value);
		else if(pattern.equals("<") || pattern.equalsIgnoreCase("lt"))
			return Restrictions.lt(propertyName, value);
		else if(pattern.equals("<=") || pattern.equalsIgnoreCase("le"))
			return Restrictions.le(propertyName, value);
		else
			return Restrictions.eq(propertyName, value);
	}
	/**
	 * 相等
	 */
	public static Criterion Eq(String propertyName,Object value){
		return Restrictions.eq(propertyName, value);
	}
	/**
	 * 属性相等 
	 */
	public static Criterion EqProperty(String propertyName,String otherPropertyName){
		return Restrictions.eqProperty(propertyName, otherPropertyName);
	}
	/**
	 * 不等
	 */
	public static Criterion Ne(String propertyName,Object value){
		return Restrictions.ne(propertyName, value);
	}
	/**
	 * 大于
	 */
	public static Criterion Gt(String propertyName,Object value){
		return Restrictions.gt(propertyName, value);
	}
	/**
	 * 小于
	 */
	public static Criterion Lt(String propertyName,Object value){
		return Restrictions.lt(propertyName, value);
	}
	/**
	 * 大于等于
	 */
	public static Criterion Ge(String propertyName,Object value){
		return Restrictions.ge(propertyName, value);
	}
	/**
	 * 小于等于
	 */
	public static Criterion Le(String propertyName,Object value){
		return Restrictions.le(propertyName, value);
	}
	/**
	 * SQL约束
	 */
	public static Criterion SqlRestriction(String sql){
		return Restrictions.sqlRestriction(sql);
	}
	/**
	 * 为Null
	 */
	public static Criterion IsNull(String propertyName) {
		return Restrictions.isNull(propertyName);
	}
	/**
	 * 为空
	 */
	public static Criterion IsEmpty(String propertyName) {
		return Restrictions.isEmpty(propertyName);
	}
	/**
	 * 不为空 并且不为Null
	 */
	public static Criterion NotNull(String propertyName) {
		return Restrictions.isNotNull(propertyName);
	}
	/**
	 * 不为空
	 */
	public static Criterion NotEmpty(String propertyName) {
		return Restrictions.isNotEmpty(propertyName);
	}
	/**
	 * 介于 start end 之间
	 */
	public static Criterion Between(String propertyName, Object start, Object end) {
		return Restrictions.between(propertyName, start, end);
	}
	/**
	 * 模糊查询(默认"%"+value+"%")
	 */
	public static Criterion Like(String propertyName, String value) {
		if(value!=null && value.length()>0)
			return Restrictions.like(propertyName, value, ANYWHERE);
		return null;
	}
	/**
	 * 模糊查询(精确带匹配模式)
	 */
	public static Criterion Like(String propertyName, String value, MatchMode matchMode) {
		if(value!=null && value.length()>0)
			return Restrictions.like(propertyName, value, matchMode);
		return null;
	}
	/**
	 * In
	 */
	public static Criterion In(String propertyName,Object ... values){
		return Restrictions.in(propertyName, values);
	}
	/**
	 * NotIn
	 */
	public static Criterion NotIn(String propertyName,Object ... values){
		return Restrictions.not(Restrictions.in(propertyName, values));
	}
	/**
	 * In
	 */
	public static Criterion Or(Criterion criterion,Criterion criterion2){
		return Restrictions.or(criterion, criterion2);
	}
	/**
	 * And
	 */
	public static Criterion And(Criterion criterion,Criterion criterion2){
		return Restrictions.and(criterion, criterion2);
	}
	/**
	 * And
	 */
	public static Criterion Not(Criterion criterion){
		return Restrictions.not(criterion);
	}

	
	/**---------------------------------------------------------------**/
	
	/**
	 * 匹配(pattern:'>'('gt'),'>='('ge'),'='('eq'),'<='('le'),'<'('lt'))
	 */
	public Filter match(String propertyName,Object value,String pattern){
		if(pattern.equals(">") || pattern.equalsIgnoreCase("gt"))
			criterionList.add(Restrictions.gt(propertyName, value));
		else if(pattern.equals(">=") || pattern.equalsIgnoreCase("ge"))
			criterionList.add(Restrictions.ge(propertyName, value));
		else if(pattern.equals("<") || pattern.equalsIgnoreCase("lt"))
			criterionList.add(Restrictions.lt(propertyName, value));
		else if(pattern.equals("<=") || pattern.equalsIgnoreCase("le"))
			criterionList.add(Restrictions.le(propertyName, value));
		else
			criterionList.add(Restrictions.eq(propertyName, value));
		return this;
	}
	/**
	 * 相等
	 */
	public Filter eq(String propertyName,Object value){
		if(value!=null)
			criterionList.add(Restrictions.eq(propertyName, value));
		return this;
	}
	/**
	 * 属性相等 
	 */
	public Filter eqProperty(String propertyName,String otherPropertyName){
		if(propertyName!=null && otherPropertyName!=null)
			criterionList.add(Restrictions.eqProperty(propertyName, otherPropertyName));
		return this;
	}
	/**
	 * 不等
	 */
	public Filter ne(String propertyName,Object value){
		if(value!=null)
			criterionList.add(Restrictions.ne(propertyName, value));
		return this;
	}
	/**
	 * 大于
	 */
	public Filter gt(String propertyName,Object value){
		if(value!=null)
			criterionList.add(Restrictions.gt(propertyName, value));
		return this;
	}
	/**
	 * 小于
	 */
	public Filter lt(String propertyName,Object value){
		if(value!=null)
			criterionList.add(Restrictions.lt(propertyName, value));
		return this;
	}
	/**
	 * 大于等于
	 */
	public Filter ge(String propertyName,Object value){
		if(value!=null)
			criterionList.add(Restrictions.ge(propertyName, value));
		return this;
	}
	/**
	 * 小于等于
	 */
	public Filter le(String propertyName,Object value){
		if(value!=null)
			criterionList.add(Restrictions.le(propertyName, value));
		return this;
	}
	/**
	 * SQL约束
	 */
	public Filter sqlRestriction(String sql){
		criterionList.add(Restrictions.sqlRestriction(sql));
		return this;
	}
	/**
	 * 为NULL
	 */
	public Filter isNull(String propertyName) {
		criterionList.add(Restrictions.isNull(propertyName));
		return this;
	}
	/**
	 * 为空
	 */
	public Filter isEmpty(String propertyName) {
		criterionList.add(Restrictions.isEmpty(propertyName));
		return this;
	}
	/**
	 * 不为NULL
	 */
	public Filter notNull(String propertyName) {
		criterionList.add(Restrictions.isNotNull(propertyName));
		return this;
	}
	/**
	 * 不为空 并且不为NULL
	 */
	public Filter notEmpty(String propertyName) {
		criterionList.add(Restrictions.isNotEmpty(propertyName));
		return this;
	}
	/**
	 * 介于 start end 之间
	 */
	public Filter between(String propertyName, Object start, Object end) {
		criterionList.add(Restrictions.between(propertyName, start, end));
		return this;
	}
	/**
	 * 模糊查询(默认包含)
	 */
	public Filter like(String propertyName, String value) {
		if(value!=null && value.length()>0)
			criterionList.add(Restrictions.like(propertyName, value, ANYWHERE));
		return this;
	}
	/**
	 * 模糊查询(精确带符号)
	 */
	public Filter like(String propertyName, String value, MatchMode matchMode) {
		if(value!=null && value.length()>0)
			criterionList.add(Restrictions.like(propertyName, value, matchMode));
		return this;
	}
	/**
	 * In
	 */
	public Filter in(String propertyName,Object ... values){
		criterionList.add(Restrictions.in(propertyName, values));
		return this;
	}
	/**
	 * NotIn
	 */
	public Filter notIn(String propertyName,Object ... values) {
		Criterion inCriterion = Restrictions.in(propertyName, values);
		criterionList.add(Restrictions.not(inCriterion));
		return this;
	}
	/**
	 * 添加或约束(两个)
	 */
	public Filter or(Criterion criterion,Criterion criterion2) {
		Criterion temp = Restrictions.or(criterion,criterion2);
		criterionList.add(temp);
		return this;
	}
	/**
	 * 添加约束
	 */
	public Filter addCriterion(Criterion criterion) {
		criterionList.add(criterion);
		return this;
	}
	/**
	 * 添加且约束(两个)
	 */
	public Filter and(Criterion criterion,Criterion criterion2) {
		Criterion temp = Restrictions.and(criterion,criterion2);
		criterionList.add(temp);
		return this;
	}
	/**
	 * 添加或约束
	 */
	public Filter or(Criterion criterion,Criterion criterion2,Criterion ... criterions) {
		Criterion temp = Restrictions.or(criterion,criterion2);
		for (Criterion c : criterions) {
			temp = Restrictions.or(c, temp);
		}
		criterionList.add(temp);
		return this;
	}
	/**
	 * 添加OR
	 * @param criterion
	 * @return
	 */
	public Filter or(Criterion criterion){
		criterionOrList.add(criterion);
		return this;
	}
	/**
	 * 添加非约束
	 */
	public Filter not(Criterion ... criterions) {
		for (Criterion c : criterions) {
			criterionList.add(Restrictions.not(c));
		}
		return this;
	}
	
	/**---------------------------------------------------------------**/
	
	/**
	 * 求和
	 */
	public Filter sum(String propertyName){
		projectionList.add(Projections.sum(propertyName));
		return this;
	}
	/**
	 * 汇总
	 */
	public Filter count(String propertyName){
		projectionList.add(Projections.count(propertyName));
		return this;
	}
	/**
	 * 平均
	 */
	public Filter avg(String propertyName){
		projectionList.add(Projections.avg(propertyName));
		return this;
	}
	/**
	 * 最大值
	 */
	public Filter max(String propertyName){
		projectionList.add(Projections.max(propertyName));
		return this;
	}
	/**
	 * 最小值
	 */
	public Filter min(String propertyName){
		projectionList.add(Projections.min(propertyName));
		return this;
	}
	/**
	 * 分组
	 */
	public Filter groupProperty(String propertyName){
		projectionList.add(Projections.groupProperty(propertyName));
		return this;
	}
	
	/**
	 * 排序
	 */
	public Filter orderBy(String propertyName, String sort) {
		if(sort!=null && sort.length()>0){
			if (sort.equals(DESC)) {
				orderList.add(Order.desc(propertyName));
			} else if(sort.equals(ASC)){
				orderList.add(Order.asc(propertyName));
			}
		}
		return this;
	}
	/**
	 * 主键排序
	 */
	private Filter orderByPrimaryKey(String sort) {
		primaryKeyOrder = sort;
		return this;
	}
	/**
	 * 结果包含字段
	 */
	public Filter include(String name){
		if(name!=null && name.length()>0)
			includeFieldList.add(name);
		return this;
	}
	/**
	 * 结果包含字段
	 */
	public Filter include(String[] names){
		if(names!=null && names.length>0)
			for (int i = 0; i < names.length; i++) {
				includeFieldList.add(names[i]);
			}
		return this;
	}
	/**
	 * 包含字段列表
	 */
	public String[] getIncludeFields(){
		String[] properties = new String[includeFieldList.size()];
		for (int i = 0; i < properties.length; i++) {
			properties[i] = includeFieldList.get(i);
		}
		return properties;
	}
	/**
	 * 添加fetchMode
	 */
	public Filter fetchMode(String propertyName,String fetchMode){
		fetchModeMap.put(propertyName, fetchMode);
		return this;
	}
	/**
	 * 添加别名
	 */
	public Filter createAlias(String propertyName,String alias){
		aliasMap.put(propertyName, alias);
		return this;
	}
	/**---------------------------------------------------------------**/
	
	/**
	 * 判断此查询有无排序(在query中如果没有排序信息 则默认加上主键排序);
	 */
	boolean hasOrder(){
		return orderList.size()>0 || primaryKeyOrder!=null;
	}
	
	/**
	 * 设置过滤条件（不包含分页信息）
	 */
	Filter filter(Criteria criteria){
		criteria(criteria);
		fetchMode(criteria);
		projection(criteria);
		order(criteria);
//		paging(criteria);
		return this;
	}
	
	/**
	 * 设置条件
	 */
	Filter criteria(Criteria criteria){
		if(getCriterions()!=null){
			criteria.add(getCriterions());
		}
		if(aliasMap.size()>0){
			for (String key : aliasMap.keySet()) {
				criteria.createAlias(key, aliasMap.get(key),CriteriaSpecification.LEFT_JOIN);
			}
		}
		return this;
	}
	/**
	 * 设置抓取策略
	 */
	Filter fetchMode(Criteria criteria){
		if(fetchModeMap.size()>0){
			for (String key : fetchModeMap.keySet()) {
				String value = fetchModeMap.get(key);
				FetchMode mode = null;
				if(value.equals("join")) mode=FetchMode.JOIN;
				else if(value.equals("select")) mode=FetchMode.SELECT;
				else mode=FetchMode.DEFAULT;
				if(mode!=null)criteria.setFetchMode(key, mode);
			}
		}
		return this;
	}
	/**
	 * 设置投影
	 */
	Filter projection(Criteria criteria){
		ProjectionList projectionList = Projections.projectionList();
		if(this.projectionList.size()>0){
			for (Projection projection : this.projectionList) {
				projectionList.add(projection);
			}
			criteria.setProjection(projectionList);
		}
		return this;
	}
	/**
	 * 设置排序
	 */
	Filter order(Criteria criteria){
		if(orderList.size()>0){
			for (Order order : orderList) {
				criteria.addOrder(order);
			}
		}
		return this;
	}
	/**
	 * 设置分页
	 */
	Filter limit(Criteria criteria){
		if(pager!=null){
			if(pager.getPage()>0)
				criteria.setFirstResult(pager.getRows()*(pager.getPage()-1));
			criteria.setMaxResults(pager.getRows());
		}
		if(firstResult!=null && firstResult.intValue()>-1)
			criteria.setFirstResult(firstResult);
		if(maxResults!=null && maxResults.intValue()>0)
			criteria.setMaxResults(maxResults);
		return this;
	}
	
	void setTotal(int total){
		if(pager!=null)pager.setRecords(total);
	}
	boolean hasPager(){
		return pager!=null;
	}
	/**---------------------------------------------------------------**/
	
	/**
	 * 移除分页
	 */
	public Filter removePager() {
		pager(null);
		firstResult(null);
		maxResults(null);
		return this;
	}
	/**
	 * 移除条件
	 */
	public Filter removeCriteria(){
		criterionList.clear();
		return this;
	}
	/**
	 * 移除投影
	 */
	public Filter removeProjection(){
		projectionList.clear();
		return this;
	}
	/**
	 * 移除排序
	 */
	public Filter removeOrder(){
		orderList.clear();
		orderByPrimaryKey(null);
		return this;
	}
	/**
	 * 移除抓取策略
	 */
	public Filter removeFetchMode(){
		fetchModeMap.clear();
		return this;
	}
	/**
	 * 移除别名
	 */
	public Filter removeAlias(){
		aliasMap.clear();
		return this;
	}
	/**
	 * 移除所有信息
	 */
	public Filter clear() {
		removeProjection();
		removeCriteria();
		removeOrder();
		removeFetchMode();
		removePager();
		return this;
	}
	/**
	 * 设置最多记录数
	 */
	public Filter maxResults(Integer maxResults) {
		this.maxResults = maxResults;
		return this;
	}
	/**
	 * 设置起始记录
	 */
	public Filter firstResult(Integer firstResult) {
		this.firstResult = firstResult;
		return this;
	}
	/**
	 * 设置分页
	 */
	public Filter pager(Pager pager) {
		this.pager = pager;
		return this;
	}
	
	
	public Map<String,String> getAlias(){
		return aliasMap;
	}
	
	/**---------------------------------------------------------------**/
	
	public int getCriterionsCount(){
		return criterionList.size();
	}
	
	/**
	 * 获取所有条件
	 */
	private Criterion getCriterions() {
		Criterion criterions = null;
		if(criterionList!=null && criterionList.size()>0){
			criterions = criterionList.get(0);
			for (int i = 1; i < criterionList.size(); i++) {
				criterions = Restrictions.and(criterions, criterionList.get(i));
			}
		}
		for (Criterion criterion : criterionOrList) {
			criterions = Restrictions.or(criterions, criterion);
		}
		return criterions;
	}
	
	List<Projection> getProjectionList() {
		return projectionList;
	}
	
	/**
	 * 请注意 此方法不关闭session 请在调用完成后关闭
	 * @param session
	 * @return
	 */
	int getRowCount(Session session) {
		int rowCount = 0;
		try {
			Criteria criteria = session.createCriteria(getBeanClass());
			criteria.setProjection(Projections.rowCount());
			boolean isGrouped = false;
			if(projectionList.size()>0){
				ProjectionList groupProjectionList = Projections.projectionList();
				for (Projection projection : this.projectionList) {
					if(projection.isGrouped()){
						groupProjectionList.add(projection);
						isGrouped = true;
					}
				}
				if(isGrouped){
					criteria.setProjection(groupProjectionList);
				}
			}
			if (getCriterions() != null) {
				criteria.add(getCriterions());
			}
			if(aliasMap.size()>0){
				for (String key : aliasMap.keySet()) {
					criteria.createAlias(key, aliasMap.get(key),CriteriaSpecification.LEFT_JOIN);
				}
			}
			if(isGrouped){
				rowCount = criteria.list().size();
			}else{
				Object result = criteria.uniqueResult();
				if(result != null)rowCount = Integer.valueOf(result.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowCount;
	}
	
	/**-----------------------------以下方法此项目中没用到----------------------------------**/
	
	/**
	 * 为bean中的关联对象创建别名
	 * bean中有些关联对象不一定有值所以默认不创建 手动创建
	
	void createAlias(){
		Field[] fields = beanClass.getDeclaredFields();
		for (Field field : fields) {
			if(field.getType().getName().startsWith(fetchPackage)){
				createAlias(field.getName(), field.getName());
			}
		}
	}
	 */
	/**
	 * 设置bean中关联的对象中的collection加载模式
	 * @param mode
	
	private void setOtherCollectionFetch(String mode){
		Field[] fields = beanClass.getDeclaredFields();
		for (Field field : fields) {
			if(field.getType().getName().startsWith(fetchPackage)){
				Field[] fieldses = field.getType().getDeclaredFields();
				for (Field f : fieldses) {
					if(Collection.class.isAssignableFrom(f.getType())){
						fetchMode(field.getName()+"."+f.getName(), mode);
					}
				}
			}
		}
	}
	 */
	/**
	 * 设置bean中关联的collection加载模式
	 * @param mode
	 
	Filter setCollectionFetch(String value){
		Field[] fields = beanClass.getDeclaredFields();
		for (Field field : fields) {
			if(Collection.class.isAssignableFrom(field.getType())){
				fetchMode(field.getName(), value);
			}
		}
		return this;
	}
	*/
}