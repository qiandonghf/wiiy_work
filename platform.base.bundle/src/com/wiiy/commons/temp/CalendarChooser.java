package com.wiiy.commons.temp;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import com.wiiy.commons.util.CalendarUtil;
import com.wiiy.commons.util.DateUtil;

public class CalendarChooser extends JPanel implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3128582586292460117L;
	
	private JLabel[][] calendars = new JLabel[6][7];
	private Date[][] dayArray = new Date[6][7];
	
	private JPanel main;
	
	private JPanel month;
	private JPanel days;
	private JPanel tools;
	
	private JButton nextY;
	private JButton preY;
	private JButton nextM;
	private JButton preM;
	
	private JButton cancel;
	private JButton today;
	
	private JLabel ym;
	
	private Date date = new Date();
	private CalendarMouseListener calendarMouseListener = new CalendarMouseListener();
	
	
	private static final int ANIMATION_INTERVAL=10;
	private static final int ANIMATION_FRAMES=50;
	private int frameIndex;
	private Timer timer;

	public CalendarChooser() {
		initComponent();
		initChildren();
	}
	
	@Override
	public void paint(Graphics g) {
		if(isAnimating()){
			float alpha=(float)frameIndex/(float)ANIMATION_FRAMES;
			Graphics2D g2d=(Graphics2D)g;
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
			super.paint(g2d);
		} else {
			frameIndex=0;
			timer=new Timer(ANIMATION_INTERVAL, this);
			timer.start();
		}
	}
	private boolean isAnimating(){
		return timer!=null && timer.isRunning();
	}    
	/**
	 * 关上时钟 初始化参数    
	 */
	private void closeTimer() {
		if(isAnimating()){
			timer.stop();
			frameIndex=0;
			timer=null;
		}
	}
	
	private void initComponent(){
		
		setBackground(new Color(253, 254, 83));
		setPreferredSize(new Dimension(192, 192));
		
	}
	
	private void initChildren(){
		main = new JPanel();
		main.setPreferredSize(new Dimension(172,172));
		main.setLayout(new BorderLayout());
		add(main);
		initMonth();
		initCalendar();
		initTools();
	}
	
	private void initMonth(){
		month = new JPanel();
		month.setPreferredSize(new Dimension(getPreferredSize().width, 25));
		month.setBackground(new Color(225, 240, 248));
		month.setLayout(new LineLayout(new int[]{10,10,40,10,10}));
		preY = generateJButton("<<");
		preY.setToolTipText("上一年");
		preY.addMouseListener(calendarMouseListener);
		month.add(preY);
		preM = generateJButton("<<");
		preM.setToolTipText("上一月");
		preM.addMouseListener(calendarMouseListener);
		month.add(preM);
		ym = new JLabel();
		ym.setOpaque(true);
		ym.setBackground(new Color(210, 220, 230));
		ym.setHorizontalAlignment(JLabel.CENTER);
		month.add(ym);
		nextM = generateJButton(">>");
		nextM.setToolTipText("下一月");
		nextM.addMouseListener(calendarMouseListener);
		month.add(nextM);
		nextY = generateJButton(">>");
		nextY.setToolTipText("下一年");
		nextY.addMouseListener(calendarMouseListener);
		month.add(nextY);
		main.add(month,BorderLayout.NORTH);
	}
	
	private JButton generateJButton(String text){
		JButton jb = new JButton(text);
		jb.setMargin(new Insets(2, 0, 2, 0));
		return jb;
	}
	
	private void initCalendar(){
		days = new JPanel();
		days.setBackground(new Color(225, 215, 248));
		days.setLayout(new GridLayout(6, 7));
		main.add(days,BorderLayout.CENTER);
		for (int i = 0; i < calendars.length; i++) {
			for (int j = 0; j < calendars[i].length; j++) {
				JLabel day = new JLabel();
				day.setHorizontalAlignment(JLabel.CENTER);
				day.addMouseListener(calendarMouseListener);
				calendars[i][j] = day;
				days.add(day);
			}
		}
		reloadCalendar();
	}
	
	private void initTools(){
		tools = new JPanel();
		tools.setPreferredSize(new Dimension(getWidth(), 28));
		tools.setBackground(new Color(225, 140, 248));
		tools.setLayout(new GridLayout(1, 2, 20, 4));
		today = new JButton("今天");
		today.addMouseListener(calendarMouseListener);
		tools.add(today);
		cancel = new JButton("取消");
		cancel.addMouseListener(calendarMouseListener);
		tools.add(cancel);
		main.add(tools,BorderLayout.SOUTH);
	}
	
	private void reloadCalendar(){
		ym.setText(DateUtil.format(date,"yyyy年MM月"));
		dayArray = generateCalendar();
		for (int i = 0; i < dayArray.length; i++) {
			for (int j = 0; j < dayArray[i].length; j++) {
				if(DateUtil.format(dayArray[i][j]).equals(DateUtil.format(new Date()))){
					calendars[i][j].setBorder(new LineBorder(new Color(057, 145, 209)));
					calendars[i][j].setOpaque(true);
					calendars[i][j].setBackground(new Color(150, 210, 210));
					calendars[i][j].setForeground(null);
				} else {
					if(!DateUtil.format(dayArray[i][j],"MM").equals(DateUtil.format(date,"MM"))){
						calendars[i][j].setForeground(Color.GRAY);
					} else {
						calendars[i][j].setForeground(null);
					}
					calendars[i][j].setBorder(null);
					calendars[i][j].setOpaque(false);
					calendars[i][j].setBackground(null);
				}
				calendars[i][j].setText(DateUtil.format(dayArray[i][j],"d"));
			}
		}
	}
	
	private Date[][] generateCalendar(){
		return CalendarUtil.generateCalendar(date,Calendar.MONDAY);
	}

	class CalendarMouseListener extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getSource().equals(preY)){
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				c.add(Calendar.YEAR, -1);
				date = c.getTime();
				reloadCalendar();
			} else if(e.getSource().equals(preM)){
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				c.add(Calendar.MONDAY, -1);
				date = c.getTime();
				reloadCalendar();
			} else if(e.getSource().equals(nextM)){
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				c.add(Calendar.MONDAY, 1);
				date = c.getTime();
				reloadCalendar();
			} else if(e.getSource().equals(nextY)){
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				c.add(Calendar.YEAR, 1);
				date = c.getTime();
				reloadCalendar();
			} else if(e.getSource().equals(cancel)){
				setVisible(false);
			} else if(e.getSource().equals(today)){
				date = new Date();
				reloadCalendar();
			} else if(e.getSource() instanceof JLabel){
				for (int i = 0; i < calendars.length; i++) {
					for (int j = 0; j < calendars[i].length; j++) {
						if(e.getSource().equals(calendars[i][j])){
							System.out.println(dayArray[i][j]);
//							fireChooserAction();
//							setVisible(false);
						}
					}
				}
			}
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			if(e.getSource() instanceof JLabel){
				for (int i = 0; i < calendars.length; i++) {
					for (int j = 0; j < calendars[i].length; j++) {
						if(e.getSource().equals(calendars[i][j])){
							if(calendars[i][j].getBorder()==null){
								calendars[i][j].setOpaque(true);
								calendars[i][j].setBackground(new Color(052, 108, 95));
								calendars[i][j].setForeground(Color.WHITE);
							}
						}
					}
				}
			}
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			if(e.getSource() instanceof JLabel){
				for (int i = 0; i < calendars.length; i++) {
					for (int j = 0; j < calendars[i].length; j++) {
						if(e.getSource().equals(calendars[i][j])){
							if(calendars[i][j].getBorder()==null){
								calendars[i][j].setOpaque(false);
								calendars[i][j].setBackground(null);
								if(!DateUtil.format(dayArray[i][j],"MM").equals(DateUtil.format(date,"MM"))){
									calendars[i][j].setForeground(Color.GRAY);
								} else {
									calendars[i][j].setForeground(null);
								}
							}
						}
					}
				}
			}
		}
		
	}
	
	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		if(b) reloadCalendar();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//前进一帧
		frameIndex++;
		if(frameIndex>=ANIMATION_FRAMES)//结尾一帧，关上动画
			closeTimer();
		else//更新当前一帧
			repaint();
	}
	
	public static void main(String[] args) {
		final JFrame jframe = new JFrame();
		jframe.add(new CalendarChooser());
		jframe.setSize(new Dimension(200, 220));
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				jframe.setLocationRelativeTo(null);
				jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				jframe.setVisible(true);
			}
		});
	}
}
