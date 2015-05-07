package com.wiiy.synthesis.job;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.context.ApplicationContext;

import com.wiiy.commons.util.CalendarUtil;
import com.wiiy.core.entity.User;
import com.wiiy.external.service.SMSPubService;
import com.wiiy.external.service.SysEmailSenderPubService;
import com.wiiy.hibernate.Filter;
import com.wiiy.synthesis.activator.SynthesisActivator;
import com.wiiy.synthesis.entity.Car;
import com.wiiy.synthesis.preferences.enums.CarStatusEnum;
import com.wiiy.synthesis.service.CarService;

public class CarInsuranceJob extends RepeatJob{
	
	protected ApplicationContext applicationContext;
	private CarService carService;
	private List<Car> cars = new ArrayList<Car>();
	
	public CarInsuranceJob(ApplicationContext applicationContext) {
		super(applicationContext);
		carService =  applicationContext.getBean(CarService.class);
	}
	public void init(){
		new Timer().schedule(new JobTask(),CalendarUtil.getEarliest(CalendarUtil.add(new Date(), Calendar.DAY_OF_YEAR, 1).getTime(), Calendar.DAY_OF_YEAR), 1000*60*60*24);
	}
	@Override
	protected void execute() {
		//提前15天提醒
		Calendar remindTime = Calendar.getInstance();
		remindTime.setTime(new Date());
		remindTime.set(Calendar.DATE, remindTime.get(Calendar.DATE) + 15);
		cars = carService.getListByFilter(new Filter(Car.class).le("insuranceDate",remindTime.getTime())).getValue();
	}
	
	public void sendMail(String receiverEmail,String content,String subject){
		SysEmailSenderPubService sysEmailSenderPubService = SynthesisActivator.getService(SysEmailSenderPubService.class);
		sysEmailSenderPubService.send(receiverEmail, content,subject);
	}
	
	public void sendSms(String receiverMobile,String content, String receiverName){
		SMSPubService smsPubService = SynthesisActivator.getService(SMSPubService.class);
		smsPubService.sendByAdmin(receiverMobile, content, receiverName);
	}
	class CarArrears extends TimerTask{
		@Override
		public void run() {
			if(cars!=null&&cars.size()>0){
				String licenseNo = "";
				for (Car car : cars) {
					licenseNo = car.getLicenseNo()+";";
				}
				User user = SynthesisActivator.getUserById(Long.valueOf(SynthesisActivator.getAppConfig().getConfig("carmanager").getParameter("name")));
				String receiverEmail = user.getEmail();
				String content = "尊敬的"+user.getRealName()+"；车牌号为【"+licenseNo+"】的车辆保险即将到期， 请尽快处理";
				String receiverMobile = "";
				if(user.getMobile()!=null){
					receiverMobile = user.getMobile();
				}
				String subject = "车辆保险到期提醒";
				sendMail(receiverEmail, content,subject);
				sendSms(receiverMobile, content, user.getRealName());
			}
		}
	}
}
