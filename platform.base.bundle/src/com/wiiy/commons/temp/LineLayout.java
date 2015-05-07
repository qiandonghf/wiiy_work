package com.wiiy.commons.temp;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.SwingConstants;

public class LineLayout implements LayoutManager , SwingConstants{
	
	private int orientation;
	private int[] percents;
	private Insets insets;
	private int gap;
	
	public LineLayout(int[] percents) {
		this.percents = percents;
	}
	
	public LineLayout(int orientation,int[] percents) {
		this.orientation = orientation;
		this.percents = percents;
	}
	public LineLayout(int[] percents, Integer gap) {
		this.percents = percents;
		this.gap = gap;
	}
	
	
	public LineLayout(int orientation, int[] percents, Insets insets) {
		this.orientation = orientation;
		this.percents = percents;
		this.insets = insets;
	}
	
	public LineLayout(int orientation, int[] percents, Insets insets, Integer gap) {
		this.orientation = orientation;
		this.percents = percents;
		this.insets = insets;
		this.gap = gap;
	}
	
	public LineLayout(int orientation, int[] percents, Integer gap) {
		this.orientation = orientation;
		this.percents = percents;
		this.gap = gap;
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	@Override
	public void removeLayoutComponent(Component comp) {
	}
	
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		synchronized (parent.getTreeLock()) {
			Insets insets = parent.getInsets();
			int width = 0;
			int height = 0;
			for (int i = 0; i < parent.getComponentCount(); i++) {
				Component comp = parent.getComponent(i);
				Dimension d = comp.getPreferredSize();
				width += d.getWidth();
				height += d.getHeight();
			}
			return new Dimension(insets.left + insets.right + width, insets.top + insets.bottom + height);
		}
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		synchronized (parent.getTreeLock()) {
			Insets insets = parent.getInsets();
			int width = 0;
			int height = 0;
			for (int i = 0; i < parent.getComponentCount(); i++) {
				Component comp = parent.getComponent(i);
				Dimension d = comp.getMinimumSize();
				width += d.getWidth();
				height += d.getHeight();
			}
			return new Dimension(insets.left + insets.right + width, insets.top + insets.bottom + height);
		}
	}

	@Override
	public void layoutContainer(Container parent) {
		synchronized (parent.getTreeLock()) {
			Insets insets = parent.getInsets();
			if(this.insets!=null) insets.set(insets.top+this.insets.top, insets.left+this.insets.left, insets.bottom+this.insets.bottom, insets.right+this.insets.right);
			int ncomponents = parent.getComponentCount();
			if (ncomponents == 0) {
				return;
			}
			int sumPercent = 0;
			for (int i = 0; i < percents.length; i++) {
				sumPercent += percents[i];
			}
			int width = (int) (parent.getWidth() - (insets.left + insets.right));
			int height = (int) (parent.getHeight() - (insets.top + insets.bottom));
			if(orientation==HORIZONTAL){
				width -= (gap*percents.length-1);
				int x = insets.left;
				int y = insets.top;
				for (int i = 0; i < percents.length; i++) {
					parent.getComponent(i).setBounds(x, y, percents[i]*width/sumPercent, height);
					x += percents[i]*width/sumPercent;
					if(i!=percents.length-1){
						x += gap;
					}
				}
			} else {
				height -= (gap*percents.length-1);
				int x = insets.left;
				int y = insets.top;
				for (int i = 0; i < percents.length; i++) {
					parent.getComponent(i).setBounds(x, y, width,percents[i]*height/sumPercent);
					y += percents[i]*height/sumPercent;
					if(i!=percents.length-1){
						y += gap;
					}
				}
			}
		}
	}
	
	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	public int[] getPercents() {
		return percents;
	}

	public void setPercents(int[] percents) {
		this.percents = percents;
	}
}
