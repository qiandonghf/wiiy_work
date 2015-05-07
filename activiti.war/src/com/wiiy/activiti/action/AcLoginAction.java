package com.wiiy.activiti.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import me.kafeitu.demo.activiti.util.UserUtil;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.struts2.ServletActionContext;

public class AcLoginAction {
	private Boolean error;
	private IdentityService identityService;

	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}

    public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	
	
	/**
     * 登录系统
     *
     * @param userName
     * @param password
     * @param session
     * @return
     */
    public String logon() {
        
        HttpServletRequest request=ServletActionContext.getRequest();
        
        String userName =request.getParameter("username");
        String password =request.getParameter("password");
        HttpSession session=request.getSession();
        
        System.out.println("AcLoginAction.logon request: {username="+userName+", password="+password+"}");
        
        boolean checkPassword = identityService.checkPassword(userName, password);
        if (checkPassword) {

            // read user from database
            User user = identityService.createUserQuery().userId(userName).singleResult();
            UserUtil.saveUserToSession(session, user);
            
            
            List<Group> groupList = identityService.createGroupQuery().groupMember(userName).list();
            session.setAttribute("groups", groupList);

            String[] groupNames = new String[groupList.size()];
            for (int i = 0; i < groupNames.length; i++) {
                System.out.println(groupList.get(i).getName());
                groupNames[i] = groupList.get(i).getName();
            }

            session.setAttribute("groupNames", ArrayUtils.toString(groupNames));

            return "index";
        } else {
        	error=true;
            return "login";
        }
    }

    public String logout() {
    	 HttpServletRequest request=ServletActionContext.getRequest();
         HttpSession session=request.getSession();
        session.removeAttribute("user");
        session.removeAttribute("groupNames");
        session.removeAttribute("groups");
        return "login";
    }
}
