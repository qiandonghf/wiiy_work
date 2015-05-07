package com.wiiy.park.action;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.wiiy.common.activator.ProjectActivator;
import com.wiiy.common.dto.BuildingPandectDto;
import com.wiiy.common.preferences.enums.RoomStatusEnum;
import com.wiiy.commons.action.JqGridBaseAction;
import com.wiiy.commons.util.BeanUtil;
import com.wiiy.core.entity.DataDict;
import com.wiiy.core.service.export.DataDictInitService;
import com.wiiy.hibernate.Filter;
import com.wiiy.hibernate.Result;
import com.wiiy.park.entity.Building;
import com.wiiy.park.entity.Room;
import com.wiiy.park.service.BuildingService;
import com.wiiy.park.service.ParkService;
import com.wiiy.park.service.RoomService;
import com.wiiy.park.service.StatisticService;

/**
 * @author my
 */
public class BuildingAction extends JqGridBaseAction<Building>{
	
	private BuildingService buildingService;
	private StatisticService statisticService;
	
	private ParkService parkService;
	private RoomService roomService;
	@SuppressWarnings("rawtypes")
	private Result result;
	private Building building;
	private Long id ;
	private String ids;
	private Double usageRates;//使用率
	private Double occupancy;//出租率
	private Long settledEnterprise;//入驻企业 
	private Long finishedContract;//合同到期
	
	private List<BuildingPandectDto> buildingPandectList;//楼宇总览 
	
	public String buildingRent(){
		buildingPandectList = rent();
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("width", request.getParameter("width"));
		result =  statisticService.buildingRentGraph();
		return "rent";
	}
	
	/*public String graph(){
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("width", request.getParameter("width"));
		List<DataDict> dataDictList = ProjectActivator.getService(DataDictInitService.class).getDataDictByParentId("pb.0007");
		request.setAttribute("dataDictList", dataDictList);
		result = statisticService.buildingGraph();
		return "graph";
	}
	
	public String rentGraph(){
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("width", request.getParameter("width"));
		result =  statisticService.buildingRentGraph();
		return "rentGraph";
	}*/
	
	/**
	 * 楼宇总览,统计部分
	 * @return
	 */
	public String buildingCensus(){
		HttpServletRequest request = ServletActionContext.getRequest();
		List<DataDict> dataDictList = ProjectActivator.getService(DataDictInitService.class).getDataDictByParentId("pb.0007");
		request.setAttribute("dataDictList", dataDictList);
		result = statisticService.buildingGraph();
		buildingPandectList = pandect();
		return "pandect";
	}
	
	protected  List<BuildingPandectDto> pandect(){
		//根据楼宇找到对应楼宇所有的房间
		List<Building> buildingList = buildingService.getListByHql("select new Building(b.id,b.parkId,b.name,b.photos) from Building b").getValue();
		List<Room> roomList = roomService.getListByHql("select new Room(r.buildingId,r.buildingArea,r.kind,r.kindId,r.status) from Room r").getValue();
		Map<Long,List<Room>> roomMap = new HashMap<Long, List<Room>>();
		if(buildingList.size()>0 && buildingList!=null){
			for(Building building : buildingList){
				List<Room> newRoomList = new ArrayList<Room>();
				if(roomList.size()>0 && roomList!=null){
					for(Room room : roomList){
						if(room.getBuildingId().equals(building.getId())){
							newRoomList.add(room);
						}
					}
				}
				roomMap.put(building.getId(), newRoomList);
			}
		
			buildingPandectList = new ArrayList<BuildingPandectDto>();
			for(Building building : buildingList){
				List<Room> myRoomList = roomMap.get(building.getId());
				BuildingPandectDto dto = new BuildingPandectDto();
				BigDecimal allArea = BigDecimal.ZERO;//总面积
				BigDecimal rentArea = BigDecimal.ZERO;//出租面积
				BigDecimal usedArea = BigDecimal.ZERO;//使用面积
				BigDecimal ownArea = BigDecimal.ZERO;//自用面积
				BigDecimal shareArea = BigDecimal.ZERO;//共用面积
				BigDecimal saletArea = BigDecimal.ZERO;//出售面积
				BigDecimal otherArea = BigDecimal.ZERO;//出售面积
				BigDecimal rentedArea = BigDecimal.ZERO;//已经出租的面积
				dto.setId(building.getId());
				dto.setParkId(building.getParkId());
				dto.setBuildingName(building.getName());
				dto.setPhotos(building.getPhotos());
				if(myRoomList.size()>0 && myRoomList!=null){
					for(Room room : myRoomList){
						if(room.getBuildingArea()==null){
							room.setBuildingArea(BigDecimal.ZERO);
						}
						allArea = allArea.add(room.getBuildingArea());//得到总面积
						if(room.getKind()!=null){
							if(!room.getStatus().equals(RoomStatusEnum.IDLE)){//除了空闲都是在使用
								usedArea = usedArea.add(room.getBuildingArea());//得到使用面积
							}
							if(room.getKind().getDataValue().equals("出租")){
								rentArea = rentArea.add(room.getBuildingArea());//得到放假性质为出租的面积
								if(room.getStatus().equals(RoomStatusEnum.USING)){
									rentedArea = rentedArea.add(room.getBuildingArea());//得到已经出租的面积
								}
							}
							if(room.getKind().getDataValue().equals("自用")){
								ownArea = ownArea.add(room.getBuildingArea());//得到自用面积
							}
							if(room.getKind().getDataValue().equals("共用")){
								shareArea = shareArea.add(room.getBuildingArea());//得到共用面积
							}
							if(room.getKind().getDataValue().equals("出售")){
								saletArea = saletArea.add(room.getBuildingArea());//得到出售面积
							}
							if(room.getKind().getDataValue().equals("其他")){
								otherArea = otherArea.add(room.getBuildingArea());//其他面积
							}
						}
					}
				}
				dto.setAllArea(allArea);
				dto.setRentArea(rentArea);
				dto.setRentedArea(rentedArea);
				dto.setOwnArea(ownArea);
				dto.setShareArea(shareArea);
				dto.setSaletArea(saletArea);
				dto.setOtherArea(otherArea);
				occupancy = 0D;
				if(rentedArea !=BigDecimal.ZERO || rentArea != BigDecimal.ZERO){//出租率等于：（已出租的）房间面积除以（性质为出租的）房间面积
					occupancy = rentedArea.doubleValue()/rentArea.doubleValue()*100;
				}
				usageRates = 0D;
				if(usedArea != BigDecimal.ZERO || allArea != BigDecimal.ZERO){
					usageRates = usedArea.doubleValue()/allArea.doubleValue()*100;
				}
				dto.setOccupancy(occupancy);
				dto.setUsageRates(usageRates);
				buildingPandectList.add(dto);
			} 
		}
		return buildingPandectList;
	}
	
	/**
	 * 出租率统计 及明细 
	 * @param 楼宇名称  出租面积  已出租  出租率	
	 * @return
	 */
	protected  List<BuildingPandectDto> rent(){
		buildingPandectList = pandect();
		List<Room> roomList = roomService.getList().getValue();
		//List<SubjectRent> subjectRentList = subjectRentService.getList().getValue();
		List<BuildingPandectDto> dtoList = new ArrayList<BuildingPandectDto>();
		if(buildingPandectList!=null && buildingPandectList.size()>0 /*&& subjectRentList.size()>0 && subjectRentList!=null*/){
			for(BuildingPandectDto buildingPandectDto : buildingPandectList){
				BigDecimal rentedArea = BigDecimal.ZERO;//已出租面积
				BigDecimal rentArea = buildingPandectDto.getRentArea();//计划出租面积
				BuildingPandectDto dto = new BuildingPandectDto();
				dto.setId(buildingPandectDto.getId());
				dto.setBuildingName(buildingPandectDto.getBuildingName());
				dto.setRentArea(rentArea);
				for(Room room : roomList){
					if(room.getBuildingId().equals(buildingPandectDto.getId())){
						if(room.getStatus().equals(RoomStatusEnum.USING) && room.getKind().getDataValue().equals("出租")){
							rentedArea = rentedArea.add(room.getBuildingArea());//得到已出租面积
						}
					}
				}
				dto.setRentedArea(rentedArea);
				occupancy = 0D;
				if(rentedArea !=BigDecimal.ZERO || rentArea != BigDecimal.ZERO){
					occupancy = rentedArea.doubleValue()/rentArea.doubleValue()*100;
					NumberFormat nf =  NumberFormat.getNumberInstance();
					nf.setMinimumFractionDigits(2);
					String s = nf.format(occupancy);
					occupancy = Double.parseDouble(s);
				}
				dto.setOccupancy(occupancy);
				dtoList.add(dto);
			}
		}
		return dtoList;
	}
	
	public String loadBuildingByParkId(){
		result = buildingService.getListByFilter(new Filter(Building.class).eq("parkId", id));
		return JSON;
	}
	
	public String add() {
		result = parkService.getBeanById(id);
		return "add";
	}
	
	public String save(){
		if (building.getTypeId().equals("")) {
			building.setTypeId(NULL);
		}
		if (building.getKindId().equals("")) {
			building.setKindId(NULL);
		}
		if (building.getInvestDirectionId().equals("")) {
			building.setInvestDirectionId(NULL);
		}
		if (building.getAirconSituationId().equals("")) {
			building.setAirconSituationId(NULL);
		}
		if (building.getDecorationSituationId().equals("")) {
			building.setDecorationSituationId(NULL);
		}
		result = buildingService.save(building);
		return JSON;
	}
	/**
	 * 获得入驻企业数
	 * @param id
	 * @return
	 */
	/*public Long gainSettledEnterprise(Long id){		
		List<Long> customerIds = new ArrayList<Long>();
		List<Contract> contractList = contractService.getListByHql("select new Contract(c.customerId) from Contract c where c.id in (select sub.contractId from SubjectRent sub where sub.roomId in ( select r.id from Room r where r.buildingId = "+id+"))").getValue();
		for (Contract contract : contractList) {
			Long customerId = contract.getCustomerId();
			if(!customerIds.contains(customerId)){
				customerIds.add(customerId);
			}					
		}
		return (long) customerIds.size();
	}*/
	/**
	 * 获得过期合同数量
	 * @param id
	 * @return
	 */
	/*public Long gainFinishedContract(Long id){
		List<Contract> contracts = contractService.getListByHql("select new Contract(c.customerId) from Contract c where c.id in (select sub.contractId from SubjectRent sub where sub.roomId in ( select r.id from Room r where r.buildingId = "+id+")) and c.endDate < '"+DateUtil.format(new Date())+"'").getValue();
		return (long) contracts.size();
	}*/
	public String view(){
		result = buildingService.getBeanById(id);
		List<Room> rooms = roomService.getListByHql("select new Room(r.buildingId,r.buildingArea,r.kind,r.kindId,r.status) from Room r where r.buildingId = "+id).getValue();
		BigDecimal allArea = BigDecimal.ZERO;
		BigDecimal usedArea = BigDecimal.ZERO;
		BigDecimal rentArea = BigDecimal.ZERO;//性质为出租的房间面积
		BigDecimal rentedArea = BigDecimal.ZERO;//已经出租的面积
		for (Room room : rooms) {
			if(room.getBuildingArea()==null){
				room.setBuildingArea(BigDecimal.ZERO);
			}
			allArea = allArea.add(room.getBuildingArea());
			if(!room.getStatus().equals(RoomStatusEnum.IDLE)){
				usedArea = usedArea.add(room.getBuildingArea());
			}
			if(room.getKind()!=null){
				if(room.getKind().getDataValue().equals("出租")){
					rentArea = rentArea.add(room.getBuildingArea());
					if(room.getStatus().equals(RoomStatusEnum.USING)){
						rentedArea = rentedArea.add(room.getBuildingArea());
					}
				}
			}
		}
		usageRates = 0D;
		if(usedArea != BigDecimal.ZERO || allArea != BigDecimal.ZERO){
			usageRates = usedArea.doubleValue()/allArea.doubleValue()*100;
		}
		occupancy = 0D;
		if(rentedArea !=BigDecimal.ZERO || rentArea != BigDecimal.ZERO){//出租率等于：（已出租的）房间面积除以（性质为出租的）房间面积
			occupancy = rentedArea.doubleValue()/rentArea.doubleValue()*100;
		}
		
		//settledEnterprise = gainSettledEnterprise(id);//入驻企业数量
		//finishedContract = gainFinishedContract(id);//过期合同数量
		return VIEW;
	}
	
	public String edit(){
		result = buildingService.getBeanById(id);
		return EDIT;
	}
	
	public String update(){
		if (building.getTypeId().equals("")) {
			building.setTypeId(NULL);
		}
		if (building.getKindId().equals("")) {
			building.setKindId(NULL);
		}
		if (building.getInvestDirectionId().equals("")) {
			building.setInvestDirectionId(NULL);
		}
		if (building.getAirconSituationId().equals("")) {
			building.setAirconSituationId(NULL);
		}
		if (building.getDecorationSituationId().equals("")) {
			building.setDecorationSituationId(NULL);
		}
		Building dbBean = buildingService.getBeanById(building.getId()).getValue();
		BeanUtil.copyProperties(building, dbBean);
		dbBean.setMaxArea(building.getMaxArea());
		dbBean.setMinArea(building.getMinArea());
		result = buildingService.update(dbBean);
		return JSON;
	}
	
	public String delete(){
		if(id!=null){
			result = buildingService.deleteById(id);
		} else if(ids!=null){
			result = buildingService.deleteByIds(ids);
		}
		return JSON;
	}
	
	public String list(){
		return refresh(new Filter(Building.class));
	}
	
	@Override
	protected List<Building> getListByFilter(Filter fitler) {
		return buildingService.getListByFilter(fitler).getValue();
	}
	
	
	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
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

	public void setBuildingService(BuildingService buildingService) {
		this.buildingService = buildingService;
	}
	
	public Result getResult() {
		return result;
	}
	
	public void setParkService(ParkService parkService) {
		this.parkService = parkService;
	}
	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}
	public Double getUsageRates() {
		return usageRates;
	}

	public void setUsageRates(Double usageRates) {
		this.usageRates = usageRates;
	}

	public Double getOccupancy() {
		return occupancy;
	}

	public void setOccupancy(Double occupancy) {
		this.occupancy = occupancy;
	}
	
	public Long getSettledEnterprise() {
		return settledEnterprise;
	}

	public void setSettledEnterprise(Long settledEnterprise) {
		this.settledEnterprise = settledEnterprise;
	}

	public Long getFinishedContract() {
		return finishedContract;
	}

	public void setFinishedContract(Long finishedContract) {
		this.finishedContract = finishedContract;
	}

	public List<BuildingPandectDto> getBuildingPandectList() {
		return buildingPandectList;
	}

	public void setBuildingPandectList(List<BuildingPandectDto> buildingPandectList) {
		this.buildingPandectList = buildingPandectList;
	}
	public void setStatisticService(StatisticService statisticService) {
		this.statisticService = statisticService;
	}
}
