package com.wiiy.synthesis.action;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.core.entity.Org;
import com.wiiy.core.entity.Role;
import com.wiiy.core.entity.User;
import com.wiiy.core.entity.UserRoleRef;
import com.wiiy.core.service.RoleService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.synthesis.activator.SynthesisActivator;
import com.wiiy.synthesis.entity.Archives;
import com.wiiy.synthesis.preferences.R;
import com.wiiy.synthesis.service.ArchivesService;
import com.wiiy.synthesis.service.RosterService;

/**
 * @author my
 */
public class RosterAction extends JqGridBaseAction<Archives>{
	

	@SuppressWarnings("rawtypes")
	private Result result;
	private User user;
	private Org org;
	private Archives archives;
	private ArchivesService archivesService;
	private RoleService roleService;
	private Long id;
	private String ids;
	private Map<Long, Long> selectedRoleIds;
	private List<User> selectedUsers;
	private List<Archives> archivesList;
	public List<Archives> getArchivesList() {
		return archivesList;
	}
	private RosterService rosterService;
	private List<Role> roleList;
	public String view(){
		user = SynthesisActivator.getUserById(id);
		roleList = roleService.getList().getValue();
		archives = archivesService.getBeanByFilter(new Filter(Archives.class).eq("userId", id)).getValue();
		Set<UserRoleRef> roleRefs = user.getRoleRefs();
		selectedRoleIds = new HashMap<Long, Long>();
		for (UserRoleRef userRoleRef : roleRefs) {
			if (userRoleRef == null) continue;
			Long selectedRoleId = userRoleRef.getRole().getId();
			selectedRoleIds.put(selectedRoleId, selectedRoleId);
		}
		return VIEW;
	}
	public String edit(){
		user = SynthesisActivator.getUserById(id);
		roleList = roleService.getList().getValue();
		archives = archivesService.getBeanByFilter(new Filter(Archives.class).eq("userId", id)).getValue();
		Set<UserRoleRef> roleRefs = user.getRoleRefs();
		selectedRoleIds = new HashMap<Long, Long>();
		for (UserRoleRef userRoleRef : roleRefs) {
			if (userRoleRef == null) continue;
			Long selectedRoleId = userRoleRef.getRole().getId();
			selectedRoleIds.put(selectedRoleId, selectedRoleId);
		}
		return EDIT;
	}

	private Set<UserRoleRef> convert2Set(List<UserRoleRef> roleRefs) {
		
		if (roleRefs == null || roleRefs.size() == 0) return new HashSet<UserRoleRef>();
		
		Set<UserRoleRef> ret = new HashSet<UserRoleRef>();
		for (UserRoleRef userRoleRef : roleRefs) {
			ret.add(userRoleRef);
		}
		return ret;
	}
	public String update(){
		user.setRoleRefs(convert2Set(user.getRoleRefList()));
		try {
			result = rosterService.update(user,archives);
		} catch (Exception e) {
			result = Result.failure(R.User.SAVE_FAILURE_DUPLICATED_NAME);
		}
		return JSON;
	}
	public String save(){
		if(archives==null)archives= new Archives();
		result = archivesService.save(archives);
		return JSON;
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

	public Archives getArchives() {
		return archives;
	}
	public void setArchives(Archives archives) {
		this.archives = archives;
	}
	public void setArchivesService(ArchivesService archivesService) {
		this.archivesService = archivesService;
	}
	public Map<Long, Long> getSelectedRoleIds() {
		return selectedRoleIds;
	}
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	@SuppressWarnings("rawtypes")
	public Result getResult() {
		return result;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	

	public List<User> getSelectedUsers() {
		return selectedUsers;
	}
	public List<Role> getRoleList() {
		return roleList;
	}
	@Override
	protected List<Archives> getListByFilter(Filter fitler) {
		
		return rosterService.getListByFilter(fitler).getValue();
	}
	public void setrosterService(RosterService rosterService) {
		this.rosterService = rosterService;
	}
}

