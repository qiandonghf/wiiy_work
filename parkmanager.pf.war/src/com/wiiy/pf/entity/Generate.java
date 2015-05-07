package com.wiiy.pf.entity;

import java.io.File;

import com.wiiy.commons.generate.GenerateCode;
import com.wiiy.commons.generate.GenerateEntity;




/**
 * 文件生成
 * @author my
 *
 */
public class Generate {
	public static void main(String[] args) {

//		GenerateEntity.run(new File("src/com/wiiy/pf/entity/Bill.hbm.xml"));
		//GenerateEntity.run(new File("src/com/wiiy/pf/entity/PurchaseRequisition.hbm.xml"));
		GenerateCode.run(PurchaseRequisition.class);
//		GenerateCode.printSpringXMLCode(ContactLog.class);
	}
}
