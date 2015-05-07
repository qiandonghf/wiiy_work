package com.wiiy.business.action;

import java.io.ByteArrayInputStream;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.wiiy.business.activator.BusinessActivator;
import com.wiiy.business.entity.ProjectLib;
import com.wiiy.business.service.ProjectLibService;
import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.preferences.enums.BooleanEnum;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.commons.util.POIUtil;
import com.wiiy.commons.util.StringUtil;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;

/**
 * @author my
 */
public class ProjectLibAction extends JqGridBaseAction<ProjectLib> {

	private ProjectLibService projectLibService;
	private Result result;
	private ProjectLib projectLib;
	private Long id;
	private String ids;
	private List<Integer> years;

	private String excelName;
	private InputStream inputStream;
	private String columns;

	public String export() {
		Filter filter = new Filter(ProjectLib.class);
		if (BusinessActivator.getSessionUser().getAdmin()
				.equals(BooleanEnum.NO)) {
			filter.eq("creatorId", BusinessActivator.getSessionUser().getId());
		}
		page = 0;// 不要分页
		refresh(filter);
		excelName = StringUtil.URLEncoderToUTF8("项目库管理") + ".xls";
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		POIUtil.export("项目库管理", generateExportColumns(columns), root, out);
		inputStream = new ByteArrayInputStream(out.toByteArray());
		return "export";
	}

	private LinkedHashMap<String, String> generateExportColumns(String columns) {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		columns = columns.replace("{", "");
		columns = columns.replace("}", "");
		String[] properties = columns.split(",");
		for (String string : properties) {
			String[] ss = string.split(":");
			String field = ss[0].replace("\"", "");
			String description = ss[1].replace("\"", "");
			map.put(field, description);
		}
		return map;
	}


	public String add() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date = new Date();
		String year = sdf.format(date);
		for (int i = 4; i > -1; i--) {
			if (years == null) {
				years = new ArrayList<Integer>();
			}
			years.add(Integer.parseInt(year) - i);
		}
		// incubators = incubatorService.getListByFilter(new
		// Filter(Incubator.class)).getValue();
		// if(BusinessActivator.getSessionUser().getAdmin().equals(BooleanEnum.NO)){
		// incubators = incubatorService.getListByFilter(new
		// Filter(Incubator.class).eq("userId",
		// BusinessActivator.getSessionUser().getId())).getValue();
		// }
		return "add";
	}

	public String save() {
		result = projectLibService.save(projectLib);
		return JSON;
	}

	public String view() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date = new Date();
		String year = sdf.format(date);
		for (int i = 4; i > -1; i--) {
			if (years == null) {
				years = new ArrayList<Integer>();
			}
			years.add(Integer.parseInt(year) - i);
		}
		result = projectLibService.getBeanById(id);
		return VIEW;
	}

	public String edit() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date = new Date();
		String year = sdf.format(date);
		for (int i = 4; i > -1; i--) {
			if (years == null) {
				years = new ArrayList<Integer>();
			}
			years.add(Integer.parseInt(year) - i);
		}
		// incubators = incubatorService.getListByFilter(new
		// Filter(Incubator.class)).getValue();
		// if(!BusinessActivator.getSessionUser().getAdmin().equals(BooleanEnum.YES)){
		// incubators = incubatorService.getListByFilter(new
		// Filter(Incubator.class).eq("userId",
		// BusinessActivator.getSessionUser().getId())).getValue();
		// }
		result = projectLibService.getBeanById(id);
		return EDIT;
	}

	public String update() {
		ProjectLib dbBean = projectLibService.getBeanById(projectLib.getId())
				.getValue();
		BeanUtil.copyProperties(projectLib, dbBean);
		result = projectLibService.update(dbBean);
		return JSON;
	}

	public String delete() {
		if (id != null) {
			result = projectLibService.deleteById(id);
		} else if (ids != null) {
			result = projectLibService.deleteByIds(ids);
		}
		return JSON;
	}

	public String listAll() {
		Filter filter = new Filter(ProjectLib.class);
		return refresh(filter);
	}

	public String list() {
		Filter filter = new Filter(ProjectLib.class);
		// if(BusinessActivator.getSessionUser().getAdmin() == BooleanEnum.NO){
		// Incubator incubator = incubatorService.getBeanByFilter(
		// new Filter(Incubator.class).eq("userId",
		// BusinessActivator.getSessionUser().getId())).getValue();
		// filter.eq("incubatorId",incubator.getId().toString());
		// }
		// filter.orderBy("orderId", Filter.ASC);
		return refresh(filter);
	}

	@Override
	protected List<ProjectLib> getListByFilter(Filter fitler) {
		return projectLibService.getListByFilter(fitler).getValue();
	}

	public ProjectLib getProjectLib() {
		return projectLib;
	}

	public void setProjectLib(ProjectLib projectLib) {
		this.projectLib = projectLib;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setProjectLibService(ProjectLibService projectLibService) {
		this.projectLibService = projectLibService;
	}

	public Result getResult() {
		return result;
	}

	public List<Integer> getYears() {
		return years;
	}

	public void setYears(List<Integer> years) {
		this.years = years;
	}

	public String getExcelName() {
		return excelName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

}
