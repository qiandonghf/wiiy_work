package com.wiiy.synthesis.dto;

import java.util.List;

import com.wiiy.synthesis.entity.CardCategory;


public class CardCategoryDto {
	private CardCategory cardCategory;
	private List<CardCategory> cardLabelList;
	public CardCategory getCardCategory() {
		return cardCategory;
	}
	public void setCardCategory(CardCategory cardCategory) {
		this.cardCategory = cardCategory;
	}
	public List<CardCategory> getCardLabelList() {
		return cardLabelList;
	}
	public void setCardLabelList(List<CardCategory> cardLabelList) {
		this.cardLabelList = cardLabelList;
	}
	
}
