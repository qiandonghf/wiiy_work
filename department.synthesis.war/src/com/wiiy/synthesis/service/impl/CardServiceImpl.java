package com.wiiy.synthesis.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wiiy.commons.preferences.enums.EntityStatus;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.hibernate.FKConstraintException;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.hibernate.UKConstraintException;
import com.wiiy.synthesis.activator.SynthesisActivator;
import com.wiiy.synthesis.dao.CardDao;
import com.wiiy.synthesis.dto.CardDto;
import com.wiiy.synthesis.entity.Card;
import com.wiiy.synthesis.preferences.R;
import com.wiiy.synthesis.service.CardService;

public class CardServiceImpl implements CardService{
	private CardDao cardDao;
	
	public void setCardDao(CardDao cardDao) {
		this.cardDao = cardDao;
	}

	@Override
	public Result<Card> save(Card t) {
		try{
			t.setCreateTime(new Date());
			t.setCreator(SynthesisActivator.getSessionUser().getRealName());
			t.setCreatorId(SynthesisActivator.getSessionUser().getId());
			t.setOwnerId(SynthesisActivator.getSessionUser().getId());
			t.setModifyTime(t.getCreateTime());
			t.setModifier(t.getCreator());
			t.setModifierId(t.getCreatorId());
			t.setEntityStatus(EntityStatus.NORMAL);
			cardDao.save(t);
			SynthesisActivator.getOperationLogService().logOP("添加名片【"+t.getName()+"】");
			return Result.success(R.Card.SAVE_SUCCESS,t);
		}catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Card.class)));
		}catch (Exception e){
			return Result.failure(R.Card.SAVE_FAILURE);
		}
	}

	@Override
	public Result<Card> delete(Card t) {
		try {
			SynthesisActivator.getOperationLogService().logOP(" 删除名片【"+t.getName()+"】");
			cardDao.delete(t);
			return Result.success(R.Card.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.Card.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Card> deleteById(Serializable id) {
		try {
			SynthesisActivator.getOperationLogService().logOP("删除id为【"+id+"】的名片");
			cardDao.deleteById(id);
			return Result.success(R.Card.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.Card.DELETE_FAILURE);
		}
	}


	@Override
	public Result<Card> deleteByIds(String ids) {
		try {
			for(String id : ids.split(",")){
				SynthesisActivator.getOperationLogService().logOP("删除id为【"+id+"】的名片");
			}
			cardDao.deleteByIds(ids);
			return Result.success(R.Card.DELETE_SUCCESS);
		} catch (FKConstraintException e) {
			return Result.failure(SynthesisActivator.getFKMessage(e.getFK()));
		} catch (Exception e) {
			return Result.failure(R.Card.DELETE_FAILURE);
		}
	}

	@Override
	public Result<Card> update(Card t) {
		try {
			t.setModifyTime(new Date());
			t.setModifier(SynthesisActivator.getSessionUser().getRealName());
			t.setModifierId(SynthesisActivator.getSessionUser().getId());
			cardDao.update(t);
			SynthesisActivator.getOperationLogService().logOP("编辑名片【"+t.getName()+"】");
			return Result.success(R.Card.UPDATE_SUCCESS,t);
		} catch (UKConstraintException e) {
			return Result.failure(R.getUKMessage(BeanUtil.getFieldDescriptionByColumnName(e.getUK(),Card.class)));
		} catch (Exception e) {
			return Result.failure(R.Card.UPDATE_FAILURE);
		}
	}
	@Override
	public Result<Card> getBeanById(Serializable id) {
		try {
			return Result.value(cardDao.getBeanById(id));
		} catch (Exception e) {
			return Result.failure(R.Card.LOAD_FAILURE);
		}
	}

	@Override
	public Result<Card> getBeanByFilter(Filter filter) {
		try {
			return Result.value(cardDao.getBeanByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.Card.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Card>> getList() {
		try {
			return Result.value(cardDao.getList());
		} catch (Exception e) {
			return Result.failure(R.Card.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<Card>> getListByFilter(Filter filter) {
		try {
			return Result.value(cardDao.getListByFilter(filter));
		} catch (Exception e) {
			return Result.failure(R.Card.LOAD_FAILURE);
		}
	}

	@Override
	public Result<List<CardDto>> importCard(List<CardDto> cardDtos) {
		return null;
	}

}
