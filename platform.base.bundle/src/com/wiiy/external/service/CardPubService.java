package com.wiiy.external.service;

import java.util.List;

import com.wiiy.external.service.dto.CardDto;
import com.wiiy.hibernate.Result;
/**
 * 名片导入公共接口
 * @author Aswan
 *
 */
public interface CardPubService {
	public Result ImportCards(List<CardDto> cardList);
}
