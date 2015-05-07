package com.wiiy.commons.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.storage.Owner;

/**
 * <br/>class-description BaseEntity
 */
public class BaseEntity implements Owner, Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2220445937713242364L;

	protected Long id = new Long("-1");

    /**
     * 创建时间
     */
    protected Date createTime;

    /**
     * 修改时间
     */
    protected Date modifyTime;

    /**
     * 创建人
     */
    protected String creator;

    /**
     * 修改人
     */
    protected String modifier;
    
    /**
     * 创建人ID
     */
    protected Long creatorId;

    /**
     * 修改人ID
     */
    protected Long modifierId;

    /**
     * 状态
     */
    
    protected Long orderby;

    /**
     * 排序
     */
    
    protected EntityStatus entityStatus = EntityStatus.NORMAL;

    public BaseEntity() {
    }

    public BaseEntity(Long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    /**
     * 修改时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 创建人
     */
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 修改人
     */
    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    /**
     * 状态
     */
    public EntityStatus getEntityStatus() {
        return entityStatus;
    }

    public void setEntityStatus(EntityStatus entityStatus) {
        this.entityStatus = entityStatus;
    }

    @Transient
    public boolean isNew() {
        return (id == -1L);
    }

    @Transient
    public Long getId(){
    	return id;
    }

    @Transient
    public String getLocation() {
        return getClass().getSimpleName();
    }

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Long getModifierId() {
		return modifierId;
	}

	public void setModifierId(Long modifierId) {
		this.modifierId = modifierId;
	}

	public Long getOrderby() {
		return orderby;
	}

	public void setOrderby(Long orderby) {
		this.orderby = orderby;
	}
}
