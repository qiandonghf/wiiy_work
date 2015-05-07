/*
MySQL Data Transfer
Source Host: localhost
Source Database: pm2
Target Host: localhost
Target Database: pm2
Author: Jonathan Jin
*/


-- -------------------------------------
-- Table init for core_data_dict
-- -------------------------------------
delete from core_data_dict;

-- -------------------------------------
-- business bandle
-- -------------------------------------
INSERT INTO core_data_dict VALUES ('business.0002', '招商管理', null, 'TECHNIC_TYPE', '技术领域', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.000201', '招商管理', 'business.0002', 'TECHNIC_TYPE', '电子信息', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.000202', '招商管理', 'business.0002', 'TECHNIC_TYPE', '先进制造', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('business.000203', '招商管理', 'business.0002', 'TECHNIC_TYPE', '航空航天', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('business.000204', '招商管理', 'business.0002', 'TECHNIC_TYPE', '现代交通', '1', '1', '4');
INSERT INTO core_data_dict VALUES ('business.000205', '招商管理', 'business.0002', 'TECHNIC_TYPE', '生物医药与医疗器械', '1', '1', '5');
INSERT INTO core_data_dict VALUES ('business.000206', '招商管理', 'business.0002', 'TECHNIC_TYPE', '新材料', '1', '1', '6');
INSERT INTO core_data_dict VALUES ('business.000207', '招商管理', 'business.0002', 'TECHNIC_TYPE', '新能源与节能', '1', '1', '7');
INSERT INTO core_data_dict VALUES ('business.000208', '招商管理', 'business.0002', 'TECHNIC_TYPE', '环境保护', '1', '1', '8');
INSERT INTO core_data_dict VALUES ('business.000209', '招商管理', 'business.0002', 'TECHNIC_TYPE', '地球空间与海洋', '1', '1', '9');
INSERT INTO core_data_dict VALUES ('business.000210', '招商管理', 'business.0002', 'TECHNIC_TYPE', '核应用技术', '1', '1', '10');
INSERT INTO core_data_dict VALUES ('business.000211', '招商管理', 'business.0002', 'TECHNIC_TYPE', '现代农业', '1', '1', '11');
INSERT INTO core_data_dict VALUES ('business.000212', '招商管理', 'business.0002', 'TECHNIC_TYPE', '其他', '1', '1', '12');

INSERT INTO core_data_dict VALUES ('business.0003', '招商管理', null, 'CUSTOMER_SOURCE', '客户来源', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('business.000301', '招商管理', 'business.0003', 'CUSTOMER_SOURCE', '电话', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.000302', '招商管理', 'business.0003', 'CUSTOMER_SOURCE', '网络', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('business.000303', '招商管理', 'business.0003', 'CUSTOMER_SOURCE', '客户或朋友介绍', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('business.000399', '招商管理', 'business.0003', 'CUSTOMER_SOURCE', '其他', '1', '1', '99');

INSERT INTO core_data_dict VALUES ('business.0004', '招商管理', null, 'REGISTER_TYPE', '注册类型', '1', '1', '4');
INSERT INTO core_data_dict VALUES ('business.000401', '招商管理', 'business.0004', 'REGISTER_TYPE', '内资-国有企业', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.000402', '招商管理', 'business.0004', 'REGISTER_TYPE', '内资-集体企业', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('business.000403', '招商管理', 'business.0004', 'REGISTER_TYPE', '内资-股份合作企业', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('business.000404', '招商管理', 'business.0004', 'REGISTER_TYPE', '内资-私营企业', '1', '1', '4');
INSERT INTO core_data_dict VALUES ('business.000405', '招商管理', 'business.0004', 'REGISTER_TYPE', '内资-股份有限公司', '1', '1', '5');
INSERT INTO core_data_dict VALUES ('business.000406', '招商管理', 'business.0004', 'REGISTER_TYPE', '内资-有限责任公司', '1', '1', '6');
INSERT INTO core_data_dict VALUES ('business.000407', '招商管理', 'business.0004', 'REGISTER_TYPE', '内资-联营企业', '1', '1', '7');
INSERT INTO core_data_dict VALUES ('business.000408', '招商管理', 'business.0004', 'REGISTER_TYPE', '港澳台-合资经营企业', '1', '1', '8');
INSERT INTO core_data_dict VALUES ('business.000409', '招商管理', 'business.0004', 'REGISTER_TYPE', '港澳台-合作经营企业', '1', '1', '9');
INSERT INTO core_data_dict VALUES ('business.000410', '招商管理', 'business.0004', 'REGISTER_TYPE', '港澳台-独资经营企业', '1', '1', '10');
INSERT INTO core_data_dict VALUES ('business.000411', '招商管理', 'business.0004', 'REGISTER_TYPE', '港澳台-股份有限公司', '1', '1', '11');
INSERT INTO core_data_dict VALUES ('business.000412', '招商管理', 'business.0004', 'REGISTER_TYPE', '外资-中外合资', '1', '1', '12');
INSERT INTO core_data_dict VALUES ('business.000413', '招商管理', 'business.0004', 'REGISTER_TYPE', '外资-中外合作', '1', '1', '13');
INSERT INTO core_data_dict VALUES ('business.000414', '招商管理', 'business.0004', 'REGISTER_TYPE', '外资-外商独资', '1', '1', '14');
INSERT INTO core_data_dict VALUES ('business.000415', '招商管理', 'business.0004', 'REGISTER_TYPE', '外资-股份有限公司', '1', '1', '15');

INSERT INTO core_data_dict VALUES ('business.0005', '招商管理', null, 'CURRENCY_TYPE', '币种', '1', '1', '5');
INSERT INTO core_data_dict VALUES ('business.000501', '招商管理', 'business.0005', 'CURRENCY_TYPE', '人民币', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.000502', '招商管理', 'business.0005', 'CURRENCY_TYPE', '港元', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('business.000503', '招商管理', 'business.0005', 'CURRENCY_TYPE', '台币', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('business.000504', '招商管理', 'business.0005', 'CURRENCY_TYPE', '美元', '1', '1', '4');
INSERT INTO core_data_dict VALUES ('business.000505', '招商管理', 'business.0005', 'CURRENCY_TYPE', '欧元', '1', '1', '5');

INSERT INTO core_data_dict VALUES ('business.0006', '招商管理', null, 'DOCUMENT_TYPE', '证件类型', '1', '1', '6');
INSERT INTO core_data_dict VALUES ('business.000601', '招商管理', 'business.0006', 'DOCUMENT_TYPE', '身份证', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.000602', '招商管理', 'business.0006', 'DOCUMENT_TYPE', '护照', '1', '1', '2');

INSERT INTO core_data_dict VALUES ('business.0007', '招商管理', null, 'APPLY_TYPE', '申报类型', '1', '1', '7');
INSERT INTO core_data_dict VALUES ('business.000701', '招商管理', 'business.0007', 'APPLY_TYPE', '国家-自然科学基金', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.000702', '招商管理', 'business.0007', 'APPLY_TYPE', '国家-863计划', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('business.000703', '招商管理', 'business.0007', 'APPLY_TYPE', '国家-973计划', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('business.000704', '招商管理', 'business.0007', 'APPLY_TYPE', '国家-科技新中小企业创新基金', '1', '1', '4');
INSERT INTO core_data_dict VALUES ('business.000705', '招商管理', 'business.0007', 'APPLY_TYPE', '国家-火炬计划', '1', '1', '5');
INSERT INTO core_data_dict VALUES ('business.000706', '招商管理', 'business.0007', 'APPLY_TYPE', '省市-省市级创新基金', '1', '1', '6');
INSERT INTO core_data_dict VALUES ('business.000707', '招商管理', 'business.0007', 'APPLY_TYPE', '省市-市级创新专项资金', '1', '1', '7');

INSERT INTO core_data_dict VALUES ('business.0008', '招商管理', null, 'APPLY_STATUS', '申报状态', '1', '1', '8');
INSERT INTO core_data_dict VALUES ('business.000801', '招商管理', 'business.0008', 'APPLY_STATUS', '申请', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.000802', '招商管理', 'business.0008', 'APPLY_STATUS', '成功', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('business.000803', '招商管理', 'business.0008', 'APPLY_STATUS', '失败', '1', '1', '3');

INSERT INTO core_data_dict VALUES ('business.0009', '招商管理', null, 'COPYRIGHT_TYPE', '著作权类型', '1', '1', '9');
INSERT INTO core_data_dict VALUES ('business.000901', '招商管理', 'business.0009', 'COPYRIGHT_TYPE', '软件著作权', '1', '1', '1');

INSERT INTO core_data_dict VALUES ('business.0010', '招商管理', null, 'PATENT_TYPE', '专利类型', '1', '1', '10');
INSERT INTO core_data_dict VALUES ('business.001001', '招商管理', 'business.0010', 'PATENT_TYPE', '发明专利', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.001002', '招商管理', 'business.0010', 'PATENT_TYPE', '实用新型', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('business.001003', '招商管理', 'business.0010', 'PATENT_TYPE', '外观设计', '1', '1', '3');

INSERT INTO core_data_dict VALUES ('business.0011', '招商管理', null, 'PATENT_STATUS', '专利状态', '1', '1', '11');
INSERT INTO core_data_dict VALUES ('business.001101', '招商管理', 'business.0011', 'PATENT_STATUS', '申请', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.001102', '招商管理', 'business.0011', 'PATENT_STATUS', '授权', '1', '1', '2');

INSERT INTO core_data_dict VALUES ('business.0012', '招商管理', null, 'PATENT_SOURCE', '专利来源', '1', '1', '12');
INSERT INTO core_data_dict VALUES ('business.001201', '招商管理', 'business.0012', 'PATENT_SOURCE', '境内专利', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.001202', '招商管理', 'business.0012', 'PATENT_SOURCE', '境外专利', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('business.001203', '招商管理', 'business.0012', 'PATENT_SOURCE', '境内专利转让', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('business.001204', '招商管理', 'business.0012', 'PATENT_SOURCE', '境外专利转让', '1', '1', '4');

INSERT INTO core_data_dict VALUES ('business.0013', '招商管理', null, 'PRODUCT_STAGE', '产品阶段', '1', '1', '13');
INSERT INTO core_data_dict VALUES ('business.001301', '招商管理', 'business.0013', 'PRODUCT_STAGE', '研发', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.001302', '招商管理', 'business.0013', 'PRODUCT_STAGE', '中试', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('business.001303', '招商管理', 'business.0013', 'PRODUCT_STAGE', '推广', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('business.001304', '招商管理', 'business.0013', 'PRODUCT_STAGE', '发布', '1', '1', '4');

INSERT INTO core_data_dict VALUES ('business.0014', '招商管理', null, 'POST', '职位', '1', '1', '14');
INSERT INTO core_data_dict VALUES ('business.001401', '招商管理', 'business.0014', 'POST', 'CEO', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.001402', '招商管理', 'business.0014', 'POST', 'COO', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.001403', '招商管理', 'business.0014', 'POST', 'CFO', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.001404', '招商管理', 'business.0014', 'POST', 'CTO', '1', '1', '1');

INSERT INTO core_data_dict VALUES ('business.0015', '招商管理', null, 'DEGREE', '学位', '1', '1', '15');
INSERT INTO core_data_dict VALUES ('business.001501', '招商管理', 'business.0015', 'DEGREE', '高中', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.001502', '招商管理', 'business.0015', 'DEGREE', '大专', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('business.001503', '招商管理', 'business.0015', 'DEGREE', '学士', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('business.001504', '招商管理', 'business.0015', 'DEGREE', '硕士', '1', '1', '4');
INSERT INTO core_data_dict VALUES ('business.001505', '招商管理', 'business.0015', 'DEGREE', '博士', '1', '1', '5');
INSERT INTO core_data_dict VALUES ('business.001506', '招商管理', 'business.0015', 'DEGREE', '博士后', '1', '1', '6');

INSERT INTO core_data_dict VALUES ('business.0016', '招商管理', null, 'CONTECTTYPE', '交往类型', '1', '1', '16');
INSERT INTO core_data_dict VALUES ('business.001601', '招商管理', 'business.0016', 'VISIT', '拜访', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.001602', '招商管理', 'business.0016', 'RECOMMEND', '引荐', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('business.001603', '招商管理', 'business.0016', 'METTING', '会晤', '1', '1', '3');

INSERT INTO core_data_dict VALUES ('business.0017', '招商管理', null, 'CERTIFICATION_TYPE', '认证类型', '1', '1', '17');
INSERT INTO core_data_dict VALUES ('business.001701', '招商管理', 'business.0017', 'SOFTWERE', '双软认证', '1', '1', '1');

INSERT INTO core_data_dict VALUES ('business.0018', '招商管理', null, 'CAPITAL_FORM', '出资方式', '1', '1', '18');
INSERT INTO core_data_dict VALUES ('business.001801', '招商管理', 'business.0018', 'MONEY', '货币', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.001802', '招商管理', 'business.0018', 'TECHNOLOGY', '技术', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('business.001803', '招商管理', 'business.0018', 'EQUIPMENT', '设备', '1', '1', '3');

INSERT INTO core_data_dict VALUES ('business.0020', '招商管理', null, 'RENT_REBATE_RULE', '租赁优惠规则', '1', '1', '20');
INSERT INTO core_data_dict VALUES ('business.002001', '招商管理', 'business.0020', 'CollegeStudents', '大学生创业', '1', '1', '1');

INSERT INTO core_data_dict VALUES ('business.0022', '招商管理', null, 'POLICY_TYPE', '招商政策类型', '1', '1', '22');
INSERT INTO core_data_dict VALUES ('business.002201', '招商管理', 'business.0022', 'STUDENT', '留学生', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.002202', '招商管理', 'business.0022', 'STUDENT', '海归', '1', '1', '2');

INSERT INTO core_data_dict VALUES ('business.0023', '招商管理', null, 'Net_TYPE', '宽带类型', '1', '1', '23');
INSERT INTO core_data_dict VALUES ('business.002301', '招商管理', 'business.0023', 'Net_TYPE', '10Mbps', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.002302', '招商管理', 'business.0023', 'Net_TYPE', '5Mbps', '1', '1', '2');

INSERT INTO core_data_dict VALUES ('business.0024', '招商管理', null, 'Service_TYPE', '服务类型', '1', '1', '24');
INSERT INTO core_data_dict VALUES ('business.002401', '招商管理', 'business.0024', '贷款服务', '贷款服务', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.002402', '招商管理', 'business.0024', '会计服务', '会计服务', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('business.002403', '招商管理', 'business.0024', '后勤服务', '后勤服务', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('business.002404', '招商管理', 'business.0024', '投资服务', '投资服务', '1', '1', '4');
INSERT INTO core_data_dict VALUES ('business.002405', '招商管理', 'business.0024', '法律服务', '法律服务', '1', '1', '5');
INSERT INTO core_data_dict VALUES ('business.002406', '招商管理', 'business.0024', '税务服务', '税务服务', '1', '1', '6');
INSERT INTO core_data_dict VALUES ('business.002407', '招商管理', 'business.0024', '人力资源', '人力资源', '1', '1', '7');
INSERT INTO core_data_dict VALUES ('business.002408', '招商管理', 'business.0024', '项目申报', '项目申报', '1', '1', '8');
INSERT INTO core_data_dict VALUES ('business.002409', '招商管理', 'business.0024', '营销服务', '营销服务', '1', '1', '9');

INSERT INTO core_data_dict VALUES ('business.0025', '招商管理', null, 'INCUBATE_ROUTE', '孵化过程', '1', '1', '25');
INSERT INTO core_data_dict VALUES ('business.002501', '招商管理', 'business.0025', '在孵', '在孵', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.002502', '招商管理', 'business.0025', '毕业', '毕业', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('business.002503', '招商管理', 'business.0025', '肄业', '肄业', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('business.002504', '招商管理', 'business.0025', '消亡', '消亡', '1', '1', '4');
INSERT INTO core_data_dict VALUES ('business.002505', '招商管理', 'business.0025', '其他', '其他', '1', '1', '5');

INSERT INTO core_data_dict VALUES ('business.0026', '招商管理', null, 'INCUBATE_SETTING', '入驻场所', '1', '1', '26');
INSERT INTO core_data_dict VALUES ('business.002601', '招商管理', 'business.0026', 'INCUBATE_SETTING', 'A楼', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.002602', '招商管理', 'business.0026', 'INCUBATE_SETTING', 'B楼', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('business.002603', '招商管理', 'business.0026', 'INCUBATE_SETTING', 'C楼', '1', '1', '3');

INSERT INTO core_data_dict VALUES ('business.0027', '招商管理', null, 'CUSTOMER_QUALIFICATION', '企业资质', '1', '1', '27');
INSERT INTO core_data_dict VALUES ('business.002701', '招商管理', 'business.0027', '经认定的软件企业', '经认定的软件企业', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.002702', '招商管理', 'business.0027', '园区高新技术企业', '园区高新技术企业', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.002703', '招商管理', 'business.0027', '市级高新技术企业', '市级高新技术企业', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.002704', '招商管理', 'business.0027', '国家级高新技术企业', '国家级高新技术企业', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.002705', '招商管理', 'business.0027', '已参加新企业见面会企业', '已参加新企业见面会企业', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.002706', '招商管理', 'business.0027', '大学生企业', '大学生企业', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.002707', '招商管理', 'business.0027', '服务外包', '服务外包', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.002708', '招商管理', 'business.0027', '明星企业', '明星企业', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.002709', '招商管理', 'business.0027', '其他', '其他', '1', '1', '1');

INSERT INTO core_data_dict VALUES ('business.0028', '招商管理', null, 'CUSTOMER_TAXADRESS', '企业纳税地', '1', '1', '28');
INSERT INTO core_data_dict VALUES ('business.002801', '招商管理', 'business.0028', '高新区', '高新区', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.002802', '招商管理', 'business.0028', '江东区', '江东区', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.002803', '招商管理', 'business.0028', '江北区', '江北区', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.002804', '招商管理', 'business.0028', '海曙区', '海曙区', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.002805', '招商管理', 'business.0028', '镇海区', '镇海区', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.002806', '招商管理', 'business.0028', '北仑区', '北仑区', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.002807', '招商管理', 'business.0028', '鄞州区', '鄞州区', '1', '1', '1');

INSERT INTO core_data_dict VALUES ('business.0029', '招商管理', null, 'Political', '政治面貌', '1', '1', '5');
INSERT INTO core_data_dict VALUES ('business.002901', '招商管理', 'business.0029', 'PartyMember', '党员', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.002902', '招商管理', 'business.0029', 'LeagueMember', '团员', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('business.002903', '招商管理', 'business.0029', 'Masses', '群众', '1', '1', '3');

INSERT INTO core_data_dict VALUES ('business.0030', '招商管理', null, 'EnterpriseType', '企业类型', '1', '1', '5');
INSERT INTO core_data_dict VALUES ('business.003001', '招商管理', 'business.0030', 'DXS', '大学生', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.003002', '招商管理', 'business.0030', 'LXS', '留学生', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('business.003003', '招商管理', 'business.0030', 'YB', '一般', '1', '1', '4');
INSERT INTO core_data_dict VALUES ('business.003004', '招商管理', 'business.0030', 'WZ', '外资', '1', '1', '5');
INSERT INTO core_data_dict VALUES ('business.003006', '招商管理', 'business.0030', 'JD', '街道企业', '1', '1', '6');

INSERT INTO core_data_dict VALUES ('business.0032', '招商管理', null, 'ROOM_TYPE', '房间用途', '1', '1', '32');
INSERT INTO core_data_dict VALUES ('business.003201', '招商管理', 'business.0032', 'ROOM_TYPE', '办公', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.003202', '招商管理', 'business.0032', 'ROOM_TYPE', '厂房', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('business.003203', '招商管理', 'business.0032', 'ROOM_TYPE', '宿舍', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('business.003299', '招商管理', 'business.0032', 'ROOM_TYPE', '其他', '1', '1', '99');

INSERT INTO core_data_dict VALUES ('business.0033', '招商管理', null, 'RENT_REBATE_RULE', '租赁优惠规则', '1', '1', '33');
INSERT INTO core_data_dict VALUES ('business.003301', '招商管理', 'business.0033', 'CollegeStudents', '大学生创业', '1', '1', '1');

INSERT INTO core_data_dict VALUES ('business.0034', '招商管理', null, 'CityId', '城市', '1', '1', '34');
INSERT INTO core_data_dict VALUES ('business.003401', '招商管理', 'business.0034', 'CITY_TYPE', '杭州', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.003402', '招商管理', 'business.0034', 'CITY_TYPE', '宁波', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('business.003403', '招商管理', 'business.0034', 'CITY_TYPE', '温州', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('business.003404', '招商管理', 'business.0034', 'CITY_TYPE', '绍兴', '1', '1', '4');
INSERT INTO core_data_dict VALUES ('business.003405', '招商管理', 'business.0034', 'CITY_TYPE', '湖州', '1', '1', '5');
INSERT INTO core_data_dict VALUES ('business.003406', '招商管理', 'business.0034', 'CITY_TYPE', '嘉兴', '1', '1', '6');
INSERT INTO core_data_dict VALUES ('business.003407', '招商管理', 'business.0034', 'CITY_TYPE', '金华', '1', '1', '7');
INSERT INTO core_data_dict VALUES ('business.003408', '招商管理', 'business.0034', 'CITY_TYPE', '衢州', '1', '1', '8');
INSERT INTO core_data_dict VALUES ('business.003409', '招商管理', 'business.0034', 'CITY_TYPE', '台州', '1', '1', '9');
INSERT INTO core_data_dict VALUES ('business.003410', '招商管理', 'business.0034', 'CITY_TYPE', '丽水', '1', '1', '10');
INSERT INTO core_data_dict VALUES ('business.003411', '招商管理', 'business.0034', 'CITY_TYPE', '舟山', '1', '1', '11');

INSERT INTO core_data_dict VALUES ('business.0035', '招商管理', null, 'IndustryId', '所属行业', '1', '1', '34');
INSERT INTO core_data_dict VALUES ('business.003501', '招商管理', 'business.0035', 'DZXX', '电子信息', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.003502', '招商管理', 'business.0035', 'XJZZ', '先进制造', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('business.003503', '招商管理', 'business.0035', 'HKHT', '航空航天', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('business.003504', '招商管理', 'business.0035', 'XDJT', '现代交通', '1', '1', '4');
INSERT INTO core_data_dict VALUES ('business.003505', '招商管理', 'business.0035', 'SWYY', '生物医药与医疗器械', '1', '1', '5');
INSERT INTO core_data_dict VALUES ('business.003506', '招商管理', 'business.0035', 'XCL', '新材料', '1', '1', '6');
INSERT INTO core_data_dict VALUES ('business.003507', '招商管理', 'business.0035', 'XLY', '新能源与节能', '1', '1', '7');
INSERT INTO core_data_dict VALUES ('business.003508', '招商管理', 'business.0035', 'HJBH', '环境保护', '1', '1', '8');
INSERT INTO core_data_dict VALUES ('business.003509', '招商管理', 'business.0035', 'DQKJ', '地球空间与海洋', '1', '1', '9');
INSERT INTO core_data_dict VALUES ('business.003510', '招商管理', 'business.0035', 'HYY', '核应用技术', '1', '1', '10');
INSERT INTO core_data_dict VALUES ('business.003511', '招商管理', 'business.0035', 'XDNY', '现代农业', '1', '1', '11');
INSERT INTO core_data_dict VALUES ('business.003512', '招商管理', 'business.0035', 'QT', '其它', '1', '1', '12');

INSERT INTO core_data_dict VALUES ('business.0036', '招商管理', null, 'CHOOSE_TYPE', '合同楼宇编码', '1', '1', '36');
INSERT INTO core_data_dict VALUES ('business.003601', '招商管理', 'business.0036', 'A', 'AL', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('business.003602', '招商管理', 'business.0036', 'B', 'BL', '1', '1', '2');
-- -------------------------------------
-- cms bandle
-- -------------------------------------
INSERT INTO core_data_dict VALUES ('cms.10001', '内容管理', null, 'TEMPLETE', '浮动广告模版', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('cms.100101', '内容管理', 'cms.10001', 'TEMPLETE', '首页浮窗', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('cms.100102', '内容管理', 'cms.10001', 'TEMPLETE', '页面左右侧', '1', '1', '2');

-- -------------------------------------
-- common bandle
-- -------------------------------------
INSERT INTO core_data_dict VALUES ('contract.0002', '公共字典', null, 'CATEGORY', '合同类别', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('contract.000201', '公共字典', 'contract.0002', 'CATEGORY', '商务合同', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('contract.000202', '公共字典', 'contract.0002', 'CATEGORY', '租赁合同', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('contract.000203', '公共字典', 'contract.0002', 'CATEGORY', '聘用合同', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('contract.000204', '公共字典', 'contract.0002', 'CATEGORY', '采购合同', '1', '1', '4');
INSERT INTO core_data_dict VALUES ('contract.000205', '公共字典', 'contract.0002', 'CATEGORY', '销售合同', '1', '1', '5');

INSERT INTO core_data_dict VALUES ('customer.0001', '公共字典', null, 'TECHNIC_TYPE', '技术领域', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('customer.000101', '公共字典', 'customer.0001', 'TECHNIC_TYPE', '电子信息', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('customer.000102', '公共字典', 'customer.0001', 'TECHNIC_TYPE', '先进制造', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('customer.000103', '公共字典', 'customer.0001', 'TECHNIC_TYPE', '航空航天', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('customer.000104', '公共字典', 'customer.0001', 'TECHNIC_TYPE', '现代交通', '1', '1', '4');
INSERT INTO core_data_dict VALUES ('customer.000105', '公共字典', 'customer.0001', 'TECHNIC_TYPE', '生物医药与医疗器械', '1', '1', '5');
INSERT INTO core_data_dict VALUES ('customer.000106', '公共字典', 'customer.0001', 'TECHNIC_TYPE', '新材料', '1', '1', '6');
INSERT INTO core_data_dict VALUES ('customer.000107', '公共字典', 'customer.0001', 'TECHNIC_TYPE', '新能源与节能', '1', '1', '7');
INSERT INTO core_data_dict VALUES ('customer.000108', '公共字典', 'customer.0001', 'TECHNIC_TYPE', '环境保护', '1', '1', '8');
INSERT INTO core_data_dict VALUES ('customer.000109', '公共字典', 'customer.0001', 'TECHNIC_TYPE', '地球空间与海洋', '1', '1', '9');
INSERT INTO core_data_dict VALUES ('customer.000110', '公共字典', 'customer.0001', 'TECHNIC_TYPE', '核应用技术', '1', '1', '10');
INSERT INTO core_data_dict VALUES ('customer.000111', '公共字典', 'customer.0001', 'TECHNIC_TYPE', '现代农业', '1', '1', '11');
INSERT INTO core_data_dict VALUES ('customer.000199', '公共字典', 'customer.0001', 'TECHNIC_TYPE', '其他', '1', '1', '99');

INSERT INTO core_data_dict VALUES ('customer.0002', '公共字典', null, 'CUSTOMER_SOURCE', '客户来源', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('customer.000201', '公共字典', 'customer.0002', 'CUSTOMER_SOURCE', '电话', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('customer.000202', '公共字典', 'customer.0002', 'CUSTOMER_SOURCE', '网络', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('customer.000203', '公共字典', 'customer.0002', 'CUSTOMER_SOURCE', '客户或朋友介绍', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('customer.000299', '公共字典', 'customer.0002', 'CUSTOMER_SOURCE', '其他', '1', '1', '99');

INSERT INTO core_data_dict VALUES ('customer.0003', '公共字典', null, 'CONTECTTYPE', '交往类型', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('customer.000301', '公共字典', 'customer.0003', 'VISIT', '拜访', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('customer.000302', '公共字典', 'customer.0003', 'RECOMMEND', '引荐', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('customer.000303', '公共字典', 'customer.0003', 'METTING', '会晤', '1', '1', '3');

INSERT INTO core_data_dict VALUES ('customer.0004', '公共字典', null, 'REGISTER_TYPE', '注册类型', '1', '1', '4');
INSERT INTO core_data_dict VALUES ('customer.000401', '公共字典', 'customer.0004', 'REGISTER_TYPE', '内资-国有企业', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('customer.000402', '公共字典', 'customer.0004', 'REGISTER_TYPE', '内资-集体企业', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('customer.000403', '公共字典', 'customer.0004', 'REGISTER_TYPE', '内资-股份合作企业', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('customer.000404', '公共字典', 'customer.0004', 'REGISTER_TYPE', '内资-私营企业', '1', '1', '4');
INSERT INTO core_data_dict VALUES ('customer.000405', '公共字典', 'customer.0004', 'REGISTER_TYPE', '内资-股份有限公司', '1', '1', '5');
INSERT INTO core_data_dict VALUES ('customer.000406', '公共字典', 'customer.0004', 'REGISTER_TYPE', '内资-有限责任公司', '1', '1', '6');
INSERT INTO core_data_dict VALUES ('customer.000407', '公共字典', 'customer.0004', 'REGISTER_TYPE', '内资-联营企业', '1', '1', '7');
INSERT INTO core_data_dict VALUES ('customer.000408', '公共字典', 'customer.0004', 'REGISTER_TYPE', '港澳台-合资经营企业', '1', '1', '8');
INSERT INTO core_data_dict VALUES ('customer.000409', '公共字典', 'customer.0004', 'REGISTER_TYPE', '港澳台-合作经营企业', '1', '1', '9');
INSERT INTO core_data_dict VALUES ('customer.000410', '公共字典', 'customer.0004', 'REGISTER_TYPE', '港澳台-独资经营企业', '1', '1', '10');
INSERT INTO core_data_dict VALUES ('customer.000411', '公共字典', 'customer.0004', 'REGISTER_TYPE', '港澳台-股份有限公司', '1', '1', '11');
INSERT INTO core_data_dict VALUES ('customer.000412', '公共字典', 'customer.0004', 'REGISTER_TYPE', '外资-中外合资', '1', '1', '12');
INSERT INTO core_data_dict VALUES ('customer.000413', '公共字典', 'customer.0004', 'REGISTER_TYPE', '外资-中外合作', '1', '1', '13');
INSERT INTO core_data_dict VALUES ('customer.000414', '公共字典', 'customer.0004', 'REGISTER_TYPE', '外资-外商独资', '1', '1', '14');
INSERT INTO core_data_dict VALUES ('customer.000415', '公共字典', 'customer.0004', 'REGISTER_TYPE', '外资-股份有限公司', '1', '1', '15');

INSERT INTO core_data_dict VALUES ('customer.0005', '公共字典', null, 'DOCUMENT_TYPE', '证件类型', '1', '1', '5');
INSERT INTO core_data_dict VALUES ('customer.000501', '公共字典', 'customer.0005', 'DOCUMENT_TYPE', '身份证', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('customer.000502', '公共字典', 'customer.0005', 'DOCUMENT_TYPE', '护照', '1', '1', '2');

INSERT INTO core_data_dict VALUES ('estate.0010', '公共字典', null, 'FIXREPORT_TYPE', '报修类型', '1', '1', '10');
INSERT INTO core_data_dict VALUES ('estate.001001', '公共字典', 'estate.0010', 'TYPE1', '空调报修', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('estate.001002', '公共字典', 'estate.0010', 'TYPE2', '网络报修', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('estate.001003', '公共字典', 'estate.0010', 'TYPE3', '墙体报修', '1', '1', '3');

INSERT INTO core_data_dict VALUES ('estate.0011', '公共字典', null, 'FIXREPORT_METHOD', '报修方式', '1', '1', '11');
INSERT INTO core_data_dict VALUES ('estate.001101', '公共字典', 'estate.0011', 'NETWORK', '网络报修', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('estate.001102', '公共字典', 'estate.0011', 'PHONE', '电话报修', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('estate.001103', '公共字典', 'estate.0011', 'SCENCE', '现场报修', '1', '1', '3');


INSERT INTO core_data_dict VALUES ('pb.0001', '公共字典', null, 'BUILDING_TYPE', '楼宇类型', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('pb.000101', '园区管理', 'pb.0001', 'BUILDING_TYPE', '办公楼', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('pb.000102', '园区管理', 'pb.0001', 'BUILDING_TYPE', '厂房', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('pb.000103', '园区管理', 'pb.0001', 'BUILDING_TYPE', '宿舍公寓', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('pb.000104', '园区管理', 'pb.0001', 'BUILDING_TYPE', '混合型', '1', '1', '4');
INSERT INTO core_data_dict VALUES ('pb.000105', '园区管理', 'pb.0001', 'BUILDING_TYPE', '其他', '1', '1', '5');

INSERT INTO core_data_dict VALUES ('pb.0002', '公共字典', null, 'BUILDING_KIND', '楼宇性质', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('pb.000201', '园区管理', 'pb.0002', 'BUILDING_KIND', '自营楼宇', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('pb.000202', '园区管理', 'pb.0002', 'BUILDING_KIND', '托管楼宇', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('pb.000203', '园区管理', 'pb.0002', 'BUILDING_KIND', '其他', '1', '1', '3');

INSERT INTO core_data_dict VALUES ('pb.0003', '公共字典', null, 'INVEST_DIRECTION', '招商方向', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('pb.000301', '园区管理', 'pb.0003', 'INVEST_DIRECTION', '旅游产业', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('pb.000302', '园区管理', 'pb.0003', 'INVEST_DIRECTION', '文化创意产业', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('pb.000303', '园区管理', 'pb.0003', 'INVEST_DIRECTION', '金融服务业', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('pb.000304', '园区管理', 'pb.0003', 'INVEST_DIRECTION', '商贸物流业', '1', '1', '4');
INSERT INTO core_data_dict VALUES ('pb.000305', '园区管理', 'pb.0003', 'INVEST_DIRECTION', '信息服务与软件业', '1', '1', '5');
INSERT INTO core_data_dict VALUES ('pb.000306', '园区管理', 'pb.0003', 'INVEST_DIRECTION', '中介服务业', '1', '1', '6');
INSERT INTO core_data_dict VALUES ('pb.000307', '园区管理', 'pb.0003', 'INVEST_DIRECTION', '房地产业', '1', '1', '7');
INSERT INTO core_data_dict VALUES ('pb.000308', '园区管理', 'pb.0003', 'INVEST_DIRECTION', '社区服务业', '1', '1', '8');
INSERT INTO core_data_dict VALUES ('pb.000309', '园区管理', 'pb.0003', 'INVEST_DIRECTION', '其他', '1', '1', '9');

INSERT INTO core_data_dict VALUES ('pb.0004', '公共字典', null, 'AIRCON_SITUATION', '空调设施', '1', '1', '4');
INSERT INTO core_data_dict VALUES ('pb.000401', '园区管理', 'pb.0004', 'AIRCON_SITUATION', '独立空调', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('pb.000402', '园区管理', 'pb.0004', 'AIRCON_SITUATION', '中央空调', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('pb.000403', '园区管理', 'pb.0004', 'AIRCON_SITUATION', '自配空调', '1', '1', '3');

INSERT INTO core_data_dict VALUES ('pb.0005', '公共字典', null, 'DECORATION_SITUATION', '装修情况', '1', '1', '5');
INSERT INTO core_data_dict VALUES ('pb.000501', '园区管理', 'pb.0005', 'DECORATION_SITUATION', '未装', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('pb.000502', '园区管理', 'pb.0005', 'DECORATION_SITUATION', '简装', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('pb.000503', '园区管理', 'pb.0005', 'DECORATION_SITUATION', '精装', '1', '1', '3');

INSERT INTO core_data_dict VALUES ('pb.0006', '公共字典', null, 'ROOM_TYPE', '房间用途', '1', '1', '6');
INSERT INTO core_data_dict VALUES ('pb.000601', '园区管理', 'pb.0006', 'ROOM_TYPE', '办公', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('pb.000602', '园区管理', 'pb.0006', 'ROOM_TYPE', '厂房', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('pb.000603', '园区管理', 'pb.0006', 'ROOM_TYPE', '宿舍', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('pb.000699', '园区管理', 'pb.0006', 'ROOM_TYPE', '其他', '1', '1', '99');

INSERT INTO core_data_dict VALUES ('pb.0007', '公共字典', null, 'ROOM_KIND', '房间性质', '1', '1', '7');
INSERT INTO core_data_dict VALUES ('pb.000701', '园区管理', 'pb.0007', 'ROOM_KIND', '出租', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('pb.000702', '园区管理', 'pb.0007', 'ROOM_KIND', '出售', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('pb.000703', '园区管理', 'pb.0007', 'ROOM_KIND', '自用', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('pb.000704', '园区管理', 'pb.0007', 'ROOM_KIND', '共用', '1', '1', '4');
INSERT INTO core_data_dict VALUES ('pb.000799', '园区管理', 'pb.0007', 'ROOM_KIND', '其他', '1', '1', '99');

INSERT INTO core_data_dict VALUES ('pb.0008', '公共字典', null, 'CHANGE_TYPE', '变更类型', '1', '1', '8');
INSERT INTO core_data_dict VALUES ('pb.000801', '园区管理', 'pb.0008', 'CHANGE_TYPE', '房屋合并', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('pb.000802', '园区管理', 'pb.0008', 'CHANGE_TYPE', '房屋拆分', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('pb.000803', '园区管理', 'pb.0008', 'CHANGE_TYPE', '水电气表变更', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('pb.000899', '园区管理', 'pb.0008', 'CHANGE_TYPE', '其他', '1', '1', '99');

INSERT INTO core_data_dict VALUES ('pb.0009', '公共字典', null, 'FACILITY_TYPE', '设施类型', '1', '1', '9');
INSERT INTO core_data_dict VALUES ('pb.000901', '园区管理', 'pb.0009', 'FACILITY_TYPE', '网络', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('pb.000902', '园区管理', 'pb.0009', 'FACILITY_TYPE', '电梯', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('pb.000903', '园区管理', 'pb.0009', 'FACILITY_TYPE', '车位', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('pb.000904', '园区管理', 'pb.0009', 'FACILITY_TYPE', '广告位', '1', '1', '4');
INSERT INTO core_data_dict VALUES ('pb.000905', '园区管理', 'pb.0009', 'FACILITY_TYPE', '会议室', '1', '1', '5');

INSERT INTO core_data_dict VALUES ('pb.0010', '公共字典', null, 'FIXREPORT_TYPE', '报修类型', '1', '1', '10');
INSERT INTO core_data_dict VALUES ('pb.001001', '园区管理', 'pb.0010', 'TYPE1', '空调报修', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('pb.001002', '园区管理', 'pb.0010', 'TYPE2', '网络报修', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('pb.001003', '园区管理', 'pb.0010', 'TYPE3', '墙体报修', '1', '1', '3');

INSERT INTO core_data_dict VALUES ('pb.0011', '公共字典', null, 'FIXREPORT_METHOD', '报修方式', '1', '1', '11');
INSERT INTO core_data_dict VALUES ('pb.001101', '园区管理', 'pb.0011', 'NETWORK', '网络报修', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('pb.001102', '园区管理', 'pb.0011', 'PHONE', '电话报修', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('pb.001103', '园区管理', 'pb.0011', 'SCENCE', '现场报修', '1', '1', '3');

INSERT INTO core_data_dict VALUES ('project.0001', '公共字典', null, 'CURRENCY_TYPE', '货币种类', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('project.000101', '项目', 'project.0001', 'CURRENCY_TYPE', '人民币', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('project.000102', '项目', 'project.0001', 'CURRENCY_TYPE', '港元', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('project.000103', '项目', 'project.0001', 'CURRENCY_TYPE', '台币', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('project.000104', '项目', 'project.0001', 'CURRENCY_TYPE', '美元', '1', '1', '4');
INSERT INTO core_data_dict VALUES ('project.000105', '项目', 'project.0001', 'CURRENCY_TYPE', '欧元', '1', '1', '5');

INSERT INTO core_data_dict VALUES ('room.0001', '公共字典', null, 'ROOM_DIRECTION', '房间朝向', '1', '1', '6');
INSERT INTO core_data_dict VALUES ('room.000101', '房源管理', 'room.0001', 'DIRECTION1', '南', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('room.000102', '房源管理', 'room.0001', 'DIRECTION2', '北', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('room.000103', '房源管理', 'room.0001', 'DIRECTION3', '南北', '1', '1', '3');

INSERT INTO core_data_dict VALUES ('room.0002', '公共字典', null, 'ROOM_TYPE', '户型编号', '1', '1', '7');
INSERT INTO core_data_dict VALUES ('room.000201', '房源管理', 'room.0002', 'TYPE1', 'A户型', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('room.000202', '房源管理', 'room.0002', 'TYPE2', 'B户型', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('room.000203', '房源管理', 'room.0002', 'TYPE3', 'C户型', '1', '1', '3');

INSERT INTO core_data_dict VALUES ('room.0003', '公共字典', null, 'ROOM_TYPE', '房屋户型', '1', '1', '8');
INSERT INTO core_data_dict VALUES ('room.000301', '房源管理', 'room.0003', 'TYPE1', '三室两厅', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('room.000302', '房源管理', 'room.0003', 'TYPE2', '两室两厅', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('room.000303', '房源管理', 'room.0003', 'TYPE3', '两室一厅', '1', '1', '3');

-- -------------------------------------
-- sales bandle
-- -------------------------------------
INSERT INTO core_data_dict VALUES ('sale.0001', '销售管理', null, 'EDUCATION', '学历', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('sale.000101', '销售管理', 'sale.0001', 'EDUCATION', '高中', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('sale.000102', '销售管理', 'sale.0001', 'EDUCATION', '专科', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('sale.000103', '销售管理', 'sale.0001', 'EDUCATION', '本科 ', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('sale.000104', '销售管理', 'sale.0001', 'EDUCATION', '研究生', '1', '1', '4');
INSERT INTO core_data_dict VALUES ('sale.000105', '销售管理', 'sale.0001', 'EDUCATION', '博士', '1', '1', '5');
INSERT INTO core_data_dict VALUES ('sale.000106', '销售管理', 'sale.0001', 'EDUCATION', '硕士', '1', '1', '6');

INSERT INTO core_data_dict VALUES ('sale.0002', '销售管理', null, 'PROFESSION', '职业', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('sale.000201', '销售管理', 'sale.0002', 'PROFESSION', '制造', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('sale.000202', '销售管理', 'sale.0002', 'PROFESSION', '个体工商户', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('sale.000203', '销售管理', 'sale.0002', 'PROFESSION', '商业服务行业', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('sale.000204', '销售管理', 'sale.0002', 'PROFESSION', '公务员', '1', '1', '4');
INSERT INTO core_data_dict VALUES ('sale.000205', '销售管理', 'sale.0002', 'PROFESSION', '房地产', '1', '1', '5');
INSERT INTO core_data_dict VALUES ('sale.000206', '销售管理', 'sale.0002', 'PROFESSION', '军人', '1', '1', '6');
INSERT INTO core_data_dict VALUES ('sale.000207', '销售管理', 'sale.0002', 'PROFESSION', '金融证券', '1', '1', '7');
INSERT INTO core_data_dict VALUES ('sale.000208', '销售管理', 'sale.0002', 'PROFESSION', '高科技', '1', '1', '8');
INSERT INTO core_data_dict VALUES ('sale.000209', '销售管理', 'sale.0002', 'PROFESSION', '其它', '1', '1', '9');

INSERT INTO core_data_dict VALUES ('sale.0003', '销售管理', null, 'FAMILY_INCOME', '家庭年收入', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('sale.000301', '销售管理', 'sale.0003', 'FAMILY_INCOME', '4万以下', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('sale.000302', '销售管理', 'sale.0003', 'FAMILY_INCOME', '4-6万', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('sale.000303', '销售管理', 'sale.0003', 'FAMILY_INCOME', '6-8万', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('sale.000304', '销售管理', 'sale.0003', 'FAMILY_INCOME', '8-10万', '1', '1', '4');
INSERT INTO core_data_dict VALUES ('sale.000305', '销售管理', 'sale.0003', 'FAMILY_INCOME', '10-15万', '1', '1', '5');
INSERT INTO core_data_dict VALUES ('sale.000306', '销售管理', 'sale.0003', 'FAMILY_INCOME', '15-20万', '1', '1', '6');
INSERT INTO core_data_dict VALUES ('sale.000307', '销售管理', 'sale.0003', 'FAMILY_INCOME', '20万以上', '1', '1', '7');

INSERT INTO core_data_dict VALUES ('sale.0004', '销售管理', null, 'CLIENT_AREA', '客户区域', '1', '1', '4');
INSERT INTO core_data_dict VALUES ('sale.000401', '销售管理', 'sale.0004', 'CLIENT_AREA', '地段', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('sale.000402', '销售管理', 'sale.0004', 'CLIENT_AREA', '户型', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('sale.000403', '销售管理', 'sale.0004', 'CLIENT_AREA', '价格', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('sale.000404', '销售管理', 'sale.0004', 'CLIENT_AREA', '环境', '1', '1', '4');

INSERT INTO core_data_dict VALUES ('sale.0005', '销售管理', null, 'SOURCE', '讯息来源', '1', '1', '5');
INSERT INTO core_data_dict VALUES ('sale.000501', '销售管理', 'sale.0005', 'SOURCE', '网络', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('sale.000502', '销售管理', 'sale.0005', 'SOURCE', '电视', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('sale.000503', '销售管理', 'sale.0005', 'SOURCE', '报纸', '1', '1', '3');

INSERT INTO core_data_dict VALUES ('sale.0006', '销售管理', null, 'HONGXING', '户型需求', '1', '1', '6');
INSERT INTO core_data_dict VALUES ('sale.000601', '销售管理', 'sale.0006', 'HONGXING', '不确定', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('sale.000602', '销售管理', 'sale.0006', 'HONGXING', '很好', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('sale.000603', '销售管理', 'sale.0006', 'HONGXING', '可以', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('sale.000604', '销售管理', 'sale.0006', 'HONGXING', '一般', '1', '1', '4');
INSERT INTO core_data_dict VALUES ('sale.000605', '销售管理', 'sale.0006', 'HONGXING', '随便看看', '1', '1', '5');
INSERT INTO core_data_dict VALUES ('sale.000606', '销售管理', 'sale.0006', 'HONGXING', '无购买意向', '1', '1', '6');

INSERT INTO core_data_dict VALUES ('sale.0007', '销售管理', null, 'RESISTANCE', '抗性分析', '1', '1', '7');
INSERT INTO core_data_dict VALUES ('sale.000701', '销售管理', 'sale.0007', 'RESISTANCE', '价格偏高', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('sale.000702', '销售管理', 'sale.0007', 'RESISTANCE', '户型不合适', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('sale.000703', '销售管理', 'sale.0007', 'RESISTANCE', '立面园林', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('sale.000704', '销售管理', 'sale.0007', 'RESISTANCE', '竞争影响', '1', '1', '4');
INSERT INTO core_data_dict VALUES ('sale.000705', '销售管理', 'sale.0007', 'RESISTANCE', '犹豫观望', '1', '1', '5');
INSERT INTO core_data_dict VALUES ('sale.000706', '销售管理', 'sale.0007', 'RESISTANCE', '其他原因', '1', '1', '6');

INSERT INTO core_data_dict VALUES ('sale.0008', '销售管理', null, 'MOTIVATION', '购房动机', '1', '1', '8');
INSERT INTO core_data_dict VALUES ('sale.000801', '销售管理', 'sale.0008', 'MOTIVATION', '不确定', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('sale.000802', '销售管理', 'sale.0008', 'MOTIVATION', '首次购房', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('sale.000803', '销售管理', 'sale.0008', 'MOTIVATION', '改善住房条件', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('sale.000804', '销售管理', 'sale.0008', 'MOTIVATION', '工作需要', '1', '1', '4');
INSERT INTO core_data_dict VALUES ('sale.000805', '销售管理', 'sale.0008', 'MOTIVATION', '投资', '1', '1', '5');
INSERT INTO core_data_dict VALUES ('sale.000806', '销售管理', 'sale.0008', 'MOTIVATION', '其他原因', '1', '1', '6');

-- -------------------------------------
-- synthesis bandle
-- -------------------------------------
INSERT INTO core_data_dict VALUES ('oa.0003', '综合管理', null, 'FixedAssetsType', '资产类别', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('oa.000301', '综合管理', 'oa.0003', 'OfficeSupplies', '办公用品', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('oa.000302', '综合管理', 'oa.0003', 'Car', '车辆', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('oa.000303', '综合管理', 'oa.0003', 'Machine', '机械设备', '1', '1', '3');

INSERT INTO core_data_dict VALUES ('oa.0004', '综合管理', null, 'Religious', '宗教信仰', '1', '1', '4');
INSERT INTO core_data_dict VALUES ('oa.000401', '综合管理', 'oa.0004', 'Buddhism', '佛教', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('oa.000402', '综合管理', 'oa.0004', 'Islam', '伊斯兰教', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('oa.000403', '综合管理', 'oa.0004', 'Other', '其他', '1', '1', '3');

INSERT INTO core_data_dict VALUES ('oa.0005', '综合管理', null, 'Political', '政治面貌', '1', '1', '5');
INSERT INTO core_data_dict VALUES ('oa.000501', '综合管理', 'oa.0005', 'PartyMember', '党员', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('oa.000502', '综合管理', 'oa.0005', 'LeagueMember', '团员', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('oa.000503', '综合管理', 'oa.0005', 'Masses', '群众', '1', '1', '3');

INSERT INTO core_data_dict VALUES ('oa.0006', '综合管理', null, 'Nationality', '国籍', '1', '1', '6');
INSERT INTO core_data_dict VALUES ('oa.000601', '综合管理', 'oa.0006', 'China', '中国', '1', '1', '1');

INSERT INTO core_data_dict VALUES ('oa.0007', '综合管理', null, 'Ethnic', '民族', '1', '1', '7');
INSERT INTO core_data_dict VALUES ('oa.000701', '综合管理', 'oa.0007', 'Han', '汉族', '1', '1', '1');

INSERT INTO core_data_dict VALUES ('oa.0008', '综合管理', null, 'MeetingType', '会议类型', '1', '1', '7');
INSERT INTO core_data_dict VALUES ('oa.000801', '会议类型', 'oa.0008', 'OfficeConference', '办公会议', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('oa.000802', '会议类型', 'oa.0008', 'FireSafetyConference', '消防安全会议', '1', '1', '1');

INSERT INTO core_data_dict VALUES ('syn.001', '综合管理', null, 'SealType', '印章类型', '1', '1', '5');
INSERT INTO core_data_dict VALUES ('syn.00101', '财务类', 'crm.001', 'CW', '财务类', '1', '1', '1');

-- -------------------------------------
-- parkflow bandle
-- -------------------------------------
INSERT INTO core_data_dict VALUES ('pf.0001', '流程管理系统', null, 'WorkflowType', '应用类别', '1', '1', '3');
INSERT INTO core_data_dict VALUES ('pf.000101', '流程管理系统', 'pf.0001', 'Executive', '行政类', '1', '1', '1');
INSERT INTO core_data_dict VALUES ('pf.000102', '流程管理系统', 'pf.0001', 'Executive', '财务类', '1', '1', '2');
INSERT INTO core_data_dict VALUES ('pf.000103', '流程管理系统', 'pf.0001', 'Executive', '园区管理类', '1', '1', '3');

-- -------------------------------------
-- Table init for cms_document
-- -------------------------------------
delete from cms_document;
INSERT INTO cms_document VALUES('1',null,',','YES','SHARED','招商',null,null,null,null,'1',null,'0','NORMAL','2015-01-21 09:37:28','超级管理员','1','2015-01-21 09:37:28','超级管理员','1');
INSERT INTO cms_document VALUES('2',null,',','YES','SHARED','销售',null,null,null,null,'1',null,'0','NORMAL','2015-01-21 09:37:28','超级管理员','1','2015-01-21 09:37:28','超级管理员','1');
INSERT INTO cms_document VALUES('3',null,',','YES','SHARED','工程',null,null,null,null,'1',null,'0','NORMAL','2015-01-21 09:37:28','超级管理员','1','2015-01-21 09:37:28','超级管理员','1');
INSERT INTO cms_document VALUES('4',null,',','YES','SHARED','办公',null,null,null,null,'1',null,'0','NORMAL','2015-01-21 09:37:28','超级管理员','1','2015-01-21 09:37:28','超级管理员','1');
INSERT INTO cms_document VALUES('5',null,',','YES','SHARED','财务',null,null,null,null,'1',null,'0','NORMAL','2015-01-21 09:37:28','超级管理员','1','2015-01-21 09:37:28','超级管理员','1');

-- -------------------------------------
-- Table init for business_data_property
-- -------------------------------------

delete from business_data_property;
INSERT INTO business_data_property VALUES ('1', null, ',', '0', '0', '1', '企业经营状况', null, '', '', '', 'NO', 'NORMAL', '2012-12-17 11:19:05', '超级管理员', '1', '2012-12-17 11:19:05', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('3', null, ',', '0', '0', '2', '企业人才引进情况', null, '', '', '', 'NO', 'NORMAL', '2012-12-17 11:20:32', '超级管理员', '1', '2012-12-17 11:20:32', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('4', null, ',', '0', '0', '3', '知识产权情况', null, '', '', '', 'NO', 'NORMAL', '2012-12-17 11:20:43', '超级管理员', '1', '2012-12-17 11:20:43', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('5', '1', ',1,', '0', '1', '1', '营业收入', null, '', '', '', 'NO', 'NORMAL', '2012-12-17 11:21:06', '超级管理员', '1', '2012-12-17 11:21:06', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('6', '5', ',1,5,', '1', '2', '1', '本期数', 'DOUBLE', '', '万元', '', 'NO', 'NORMAL', '2012-12-17 11:21:26', '超级管理员', '1', '2012-12-17 11:21:26', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('7', '5', ',1,5,', '1', '2', '2', '本年累计数', 'DOUBLE', '', '万元', '', 'NO', 'NORMAL', '2012-12-17 11:21:45', '超级管理员', '1', '2012-12-17 11:21:45', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('8', '5', ',1,5,', '0', '2', '3', '软件收入', null, '', '', '', 'NO', 'NORMAL', '2012-12-17 11:22:20', '超级管理员', '1', '2012-12-18 14:33:06', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('9', '8', ',1,5,8,', '1', '3', '1', '本期数', 'DOUBLE', '', '万元', '', 'NO', 'NORMAL', '2012-12-17 11:22:38', '超级管理员', '1', '2012-12-17 11:22:38', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('10', '8', ',1,5,8,', '1', '3', '2', '本年累计数', 'DOUBLE', '', '万元', '', 'NO', 'NORMAL', '2012-12-17 11:22:55', '超级管理员', '1', '2012-12-17 11:22:55', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('11', '1', ',1,', '0', '1', '2', '营业利润', null, '', '', '', 'NO', 'NORMAL', '2012-12-17 11:23:19', '超级管理员', '1', '2012-12-17 11:23:19', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('12', '11', ',1,11,', '1', '2', '1', '本期数', 'DOUBLE', '', '万元', '', 'NO', 'NORMAL', '2012-12-17 11:23:39', '超级管理员', '1', '2012-12-17 11:23:39', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('13', '11', ',1,11,', '1', '2', '2', '本年累计数', 'DOUBLE', '', '万元', '', 'NO', 'NORMAL', '2012-12-17 11:23:54', '超级管理员', '1', '2012-12-17 11:23:54', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('14', '1', ',1,', '0', '1', '3', '利润总额', null, '', '', '', 'NO', 'NORMAL', '2012-12-17 11:24:10', '超级管理员', '1', '2012-12-17 11:24:10', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('15', '14', ',1,14,', '1', '2', '1', '本期数', 'DOUBLE', '', '万元', '', 'NO', 'NORMAL', '2012-12-17 11:24:22', '超级管理员', '1', '2012-12-17 11:24:22', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('16', '14', ',1,14,', '1', '2', '2', '本年累计数', 'DOUBLE', '', '万元', '', 'NO', 'NORMAL', '2012-12-17 11:24:56', '超级管理员', '1', '2012-12-17 11:24:56', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('17', '1', ',1,', '0', '1', '4', '税金合计', null, '', '', '', 'NO', 'NORMAL', '2012-12-17 11:25:08', '超级管理员', '1', '2012-12-17 11:25:08', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('18', '17', ',1,17,', '1', '2', '1', '本期数', 'DOUBLE', '', '万元', '', 'NO', 'NORMAL', '2012-12-17 11:25:35', '超级管理员', '1', '2012-12-17 11:25:35', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('19', '17', ',1,17,', '1', '2', '2', '本年累计数', 'DOUBLE', '', '万元', '', 'NO', 'NORMAL', '2012-12-17 11:25:53', '超级管理员', '1', '2012-12-17 11:25:53', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('20', '17', ',1,17,', '0', '2', '3', '营业税', null, '', '', '', 'NO', 'NORMAL', '2012-12-17 11:26:10', '超级管理员', '1', '2012-12-18 14:33:22', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('21', '20', ',1,17,20,', '1', '3', '1', '本期数', 'DOUBLE', '', '万元', '', 'NO', 'NORMAL', '2012-12-17 11:26:26', '超级管理员', '1', '2012-12-17 11:26:26', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('22', '20', ',1,17,20,', '1', '3', '2', '本年累计数', 'DOUBLE', '', '万元', '', 'NO', 'NORMAL', '2012-12-17 11:26:54', '超级管理员', '1', '2012-12-17 11:26:54', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('23', '17', ',1,17,', '0', '2', '4', '增值税', null, '', '', '', 'NO', 'NORMAL', '2012-12-17 11:27:30', '超级管理员', '1', '2012-12-17 11:27:30', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('24', '23', ',1,17,23,', '1', '3', '1', '本期数', 'DOUBLE', '', '万元', '', 'NO', 'NORMAL', '2012-12-17 11:27:50', '超级管理员', '1', '2012-12-17 11:27:50', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('25', '23', ',1,17,23,', '1', '3', '2', '本年累计数', 'DOUBLE', '', '万元', '', 'NO', 'NORMAL', '2012-12-17 11:28:07', '超级管理员', '1', '2012-12-17 11:28:07', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('26', '17', ',1,17,', '0', '2', '5', '所得税', null, '', '', '', 'NO', 'NORMAL', '2012-12-17 11:28:23', '超级管理员', '1', '2012-12-17 11:28:23', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('27', '26', ',1,17,26,', '1', '3', '1', '本期数', 'DOUBLE', '', '万元', '', 'NO', 'NORMAL', '2012-12-17 11:28:39', '超级管理员', '1', '2012-12-17 11:28:39', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('28', '26', ',1,17,26,', '1', '3', '2', '本年累计数', 'DOUBLE', '', '万元', '', 'NO', 'NORMAL', '2012-12-17 11:28:59', '超级管理员', '1', '2012-12-17 11:28:59', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('29', '1', ',1,', '0', '1', '5', '从业人员工资总额', null, '', '', '', 'NO', 'NORMAL', '2012-12-17 11:29:20', '超级管理员', '1', '2012-12-17 11:29:20', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('30', '29', ',1,29,', '1', '2', '1', '本期数', 'DOUBLE', '', '万元', '', 'NO', 'NORMAL', '2012-12-17 11:29:34', '超级管理员', '1', '2012-12-17 11:29:34', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('31', '29', ',1,29,', '1', '2', '2', '本年累计数', 'DOUBLE', '', '万元', '', 'NO', 'NORMAL', '2012-12-17 11:29:56', '超级管理员', '1', '2012-12-17 11:29:56', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('32', '1', ',1,', '0', '1', '6', '科技支出', null, '', '', '', 'NO', 'NORMAL', '2012-12-17 11:30:17', '超级管理员', '1', '2012-12-17 11:30:17', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('33', '32', ',1,32,', '1', '2', '1', '本期数', 'DOUBLE', '', '万元', '', 'NO', 'NORMAL', '2012-12-17 11:30:32', '超级管理员', '1', '2012-12-17 11:30:32', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('34', '32', ',1,32,', '1', '2', '2', '本年累计数', 'DOUBLE', '', '万元', '', 'NO', 'NORMAL', '2012-12-17 11:30:50', '超级管理员', '1', '2012-12-17 11:30:50', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('35', '1', ',1,', '1', '1', '7', '资产合计', 'DOUBLE', '', '万元', '', 'NO', 'NORMAL', '2012-12-17 11:52:34', '超级管理员', '1', '2012-12-17 11:52:34', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('36', '1', ',1,', '1', '1', '8', '负债合计', 'DOUBLE', '', '万元', '', 'NO', 'NORMAL', '2012-12-17 11:52:48', '超级管理员', '1', '2012-12-17 11:52:48', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('37', '1', ',1,', '1', '1', '9', '所有者权益', 'DOUBLE', '', '万元', '', 'NO', 'NORMAL', '2012-12-17 11:53:16', '超级管理员', '1', '2012-12-17 11:53:16', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('38', '3', ',3,', '0', '1', '1', '企业总人数', 'INT', '', '人', '', 'NO', 'NORMAL', '2012-12-17 11:53:51', '超级管理员', '1', '2012-12-17 11:53:51', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('39', '38', ',3,38,', '1', '2', '1', '本月新增就业人员', 'INT', '', '人', '', 'NO', 'NORMAL', '2012-12-17 11:54:19', '超级管理员', '1', '2012-12-17 11:54:19', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('40', '38', ',3,38,', '1', '2', '2', '博士', 'INT', '', '人', '', 'NO', 'NORMAL', '2012-12-17 11:55:00', '超级管理员', '1', '2012-12-18 14:33:41', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('41', '38', ',3,38,', '1', '2', '3', '硕士', 'INT', '', '人', '', 'NO', 'NORMAL', '2012-12-17 11:55:18', '超级管理员', '1', '2012-12-17 11:55:18', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('42', '38', ',3,38,', '1', '2', '4', '本科', 'INT', '', '人', '', 'NO', 'NORMAL', '2012-12-17 11:56:22', '超级管理员', '1', '2012-12-17 11:56:22', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('43', '38', ',3,38,', '1', '2', '5', '大专', 'INT', '', '人', '', 'NO', 'NORMAL', '2012-12-17 11:56:39', '超级管理员', '1', '2012-12-17 11:56:39', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('44', '38', ',3,38,', '1', '2', '6', '高级职称', 'INT', '', '人', '', 'NO', 'NORMAL', '2012-12-17 11:59:11', '超级管理员', '1', '2012-12-18 14:34:07', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('45', '38', ',3,38,', '1', '2', '7', '中级职称', 'INT', '', '人', '', 'NO', 'NORMAL', '2012-12-17 11:59:28', '超级管理员', '1', '2012-12-17 11:59:28', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('46', '38', ',3,38,', '1', '2', '8', '初级职称', 'INT', '', '人', '', 'NO', 'NORMAL', '2012-12-17 11:59:43', '超级管理员', '1', '2012-12-17 11:59:43', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('47', '38', ',3,38,', '1', '2', '9', '留学人员', 'INT', '', '人', '', 'NO', 'NORMAL', '2012-12-17 12:00:04', '超级管理员', '1', '2012-12-18 14:34:15', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('48', '4', ',4,', '0', '1', '1', '企业获得专利总数', 'INT', '', '', '', 'NO', 'NORMAL', '2012-12-17 12:02:48', '超级管理员', '1', '2012-12-17 12:02:48', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('49', '48', ',4,48,', '1', '2', '1', '本月新增发明专利', 'INT', '', '', '', 'NO', 'NORMAL', '2012-12-17 12:03:12', '超级管理员', '1', '2012-12-18 14:34:22', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('50', '48', ',4,48,', '1', '2', '2', '本月新增实用新型专利', 'INT', '', '', '', 'NO', 'NORMAL', '2012-12-17 12:03:36', '超级管理员', '1', '2012-12-17 12:03:36', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('51', '48', ',4,48,', '1', '2', '3', '本月新增外观专利', 'INT', '', '', '', 'NO', 'NORMAL', '2012-12-17 12:03:55', '超级管理员', '1', '2012-12-17 12:06:31', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('52', '4', ',4,', '0', '1', '2', '企业获得软件著作权总数', 'INT', '', '', '', 'NO', 'NORMAL', '2012-12-17 12:04:22', '超级管理员', '1', '2012-12-17 12:04:22', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('53', '52', ',4,52,', '1', '2', '1', '本月新增著作权', 'INT', '', '', '', 'NO', 'NORMAL', '2012-12-17 12:04:44', '超级管理员', '1', '2012-12-18 14:34:31', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('54', null, ',', '0', '0', '4', '企业基本情况', null, '', '', '', 'NO', 'NORMAL', '2012-12-17 15:42:42', '超级管理员', '1', '2012-12-17 15:42:42', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('55', null, ',', '0', '0', '5', '企业经济概况', null, '', '', '', 'NO', 'NORMAL', '2012-12-17 15:42:59', '超级管理员', '1', '2012-12-17 15:42:59', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('56', null, ',', '0', '0', '6', '企业从业人员情况', null, '', '', '', 'NO', 'NORMAL', '2012-12-17 15:43:13', '超级管理员', '1', '2012-12-17 15:43:13', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('57', null, ',', '0', '0', '7', '企业知识产权情况', null, '', '', '', 'NO', 'NORMAL', '2012-12-17 15:43:28', '超级管理员', '1', '2012-12-17 15:43:28', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('58', null, ',', '0', '0', '8', '国家科技项目情况', null, '', '', '', 'NO', 'NORMAL', '2012-12-17 15:43:43', '超级管理员', '1', '2012-12-17 15:43:43', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('59', '54', ',54,', '1', '1', '1', '企业成立时间', 'DATETIME', '', '', '', 'NO', 'NORMAL', '2012-12-17 15:44:50', '超级管理员', '1', '2012-12-17 15:44:50', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('60', '54', ',54,', '1', '1', '2', '企业登记注册类型', 'SELECT', '内资-国有企业|内资-集体企业|内资-股份合作企业|内资-私营企业|内资-股份有限公司|内资-有限责任公司|内资-联营企业|港澳台-合资经营企业|港澳台-合作经营企业|港澳台-独资经营企业|港澳台-股份有限公司|外资-中外合资|外资-中外合作|外资-外商独资|外资-股份有限公司', '', '', 'NO', 'NORMAL', '2012-12-18 11:21:19', '超级管理员', '1', '2012-12-18 11:25:15', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('61', '54', ',54,', '1', '1', '3', '行业类别', 'SELECT', '电子信息|先进制造|航空航天|现代交通|生物医药与医疗器械|新材料|新能源与节能|环境保护|地球空间与海洋|核应用技术|现代农业|其他', '', '', 'NO', 'NORMAL', '2012-12-18 11:28:42', '超级管理员', '1', '2012-12-18 11:28:42', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('62', '54', ',54,', '1', '1', '4', '企业所属技术领域', 'SELECT', '电子信息|先进制造|航空航天|现代交通|生物医药与医疗器械|新材料|新能源与节能|环境保护|地球空间与海洋|核应用技术|现代农业|其他', '', '', 'NO', 'NORMAL', '2012-12-18 11:31:00', '超级管理员', '1', '2012-12-18 11:31:00', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('63', '54', ',54,', '1', '1', '5', '获天使或风险投资额', 'DOUBLE', '', '万元', '', 'NO', 'NORMAL', '2012-12-18 11:31:40', '超级管理员', '1', '2012-12-18 11:31:40', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('64', '54', ',54,', '1', '1', '6', '占用孵化器场地面积', 'DOUBLE', '', '平方米', '', 'NO', 'NORMAL', '2012-12-18 11:32:06', '超级管理员', '1', '2012-12-18 11:32:06', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('65', '54', ',54,', '1', '1', '7', '企业成立时注册资本', 'DOUBLE', '', '万元', '', 'NO', 'NORMAL', '2012-12-18 14:11:17', '超级管理员', '1', '2012-12-18 14:11:17', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('66', '54', ',54,', '1', '1', '8', '高新技术企业', 'SELECT', '是|否', '', '', 'NO', 'NORMAL', '2012-12-18 14:12:14', '超级管理员', '1', '2012-12-18 14:12:14', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('67', '54', ',54,', '1', '1', '9', '毕业企业', 'SELECT', '是|否', '', '', 'NO', 'NORMAL', '2012-12-18 14:12:41', '超级管理员', '1', '2012-12-18 14:12:41', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('68', '54', ',54,', '1', '1', '10', '与创业导师建立辅导关系', 'SELECT', '是|否', '', '', 'NO', 'NORMAL', '2012-12-18 14:13:05', '超级管理员', '1', '2012-12-18 14:13:05', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('69', '54', ',54,', '1', '1', '11', '留学人员企业', 'SELECT', '是|否', '', '', 'NO', 'NORMAL', '2012-12-18 14:13:34', '超级管理员', '1', '2012-12-18 14:13:34', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('70', '54', ',54,', '1', '1', '12', '大学生科技企业', 'SELECT', '是|否', '', '', 'NO', 'NORMAL', '2012-12-18 14:14:14', '超级管理员', '1', '2012-12-18 14:14:14', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('71', '55', ',55,', '1', '1', '1', '在孵企业总收入', 'DOUBLE', '', '万元', '', 'NO', 'NORMAL', '2012-12-18 14:15:04', '超级管理员', '1', '2012-12-18 14:15:04', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('72', '55', ',55,', '1', '1', '2', '在孵企业净利润', 'DOUBLE', '', '万元', '', 'NO', 'NORMAL', '2012-12-18 14:15:33', '超级管理员', '1', '2012-12-18 14:15:33', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('73', '55', ',55,', '1', '1', '3', '在孵企业出口创汇', 'DOUBLE', '', '万元', '', 'NO', 'NORMAL', '2012-12-18 14:17:02', '超级管理员', '1', '2012-12-18 14:17:02', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('74', '55', ',55,', '1', '1', '4', '在孵企业R&D投入', 'DOUBLE', '', '万元', '', 'NO', 'NORMAL', '2012-12-18 14:17:30', '超级管理员', '1', '2012-12-18 14:17:30', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('75', '56', ',56,', '1', '1', '5', '在孵企业从业人员数', 'INT', '', '人', '', 'NO', 'NORMAL', '2012-12-18 14:18:04', '超级管理员', '1', '2012-12-18 14:18:04', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('76', '56', ',56,', '1', '1', '6', '博士', 'INT', '', '人', '', 'NO', 'NORMAL', '2012-12-18 14:18:24', '超级管理员', '1', '2012-12-18 14:34:49', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('77', '56', ',56,', '1', '1', '7', '大专以上', 'INT', '', '人', '', 'NO', 'NORMAL', '2012-12-18 14:18:44', '超级管理员', '1', '2012-12-18 14:18:44', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('78', '56', ',56,', '1', '1', '8', '留学人员', 'INT', '', '人', '', 'NO', 'NORMAL', '2012-12-18 14:19:19', '超级管理员', '1', '2012-12-18 14:19:19', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('79', '56', ',56,', '1', '1', '9', '千人计划人数', 'INT', '', '人', '', 'NO', 'NORMAL', '2012-12-18 14:19:53', '超级管理员', '1', '2012-12-18 14:19:53', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('80', '57', ',57,', '1', '1', '1', '申请知识产权保护数', 'INT', '', '', '', 'NO', 'NORMAL', '2012-12-18 14:21:19', '超级管理员', '1', '2012-12-18 14:21:19', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('81', '57', ',57,', '1', '1', '2', '批准知识产权保护数', 'INT', '', '', '', 'NO', 'NORMAL', '2012-12-18 14:21:39', '超级管理员', '1', '2012-12-18 14:21:39', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('82', '57', ',57,', '1', '1', '3', '发明专利', 'INT', '', '', '', 'NO', 'NORMAL', '2012-12-18 14:21:57', '超级管理员', '1', '2012-12-18 14:34:59', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('83', '57', ',57,', '1', '1', '4', '软件著作权', 'INT', '', '', '', 'NO', 'NORMAL', '2012-12-18 14:22:13', '超级管理员', '1', '2012-12-18 14:22:13', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('84', '57', ',57,', '1', '1', '5', '植物新品种', 'INT', '', '', '', 'NO', 'NORMAL', '2012-12-18 14:23:25', '超级管理员', '1', '2012-12-18 14:23:25', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('85', '57', ',57,', '1', '1', '6', '集成电路布图', 'INT', '', '', '', 'NO', 'NORMAL', '2012-12-18 14:23:55', '超级管理员', '1', '2012-12-18 14:23:55', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('86', '57', ',57,', '1', '1', '7', '购买国外专利', 'INT', '', '', '', 'NO', 'NORMAL', '2012-12-18 14:24:13', '超级管理员', '1', '2012-12-18 14:24:13', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('87', '58', ',58,', '1', '1', '1', '获得国家创新基金资助额', 'DOUBLE', '', '万元', '', 'NO', 'NORMAL', '2012-12-18 14:24:48', '超级管理员', '1', '2012-12-18 14:25:04', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('88', '58', ',58,', '1', '1', '2', '获得省创新基金资助额', 'DOUBLE', '', '万元', '', 'NO', 'NORMAL', '2012-12-18 14:25:39', '超级管理员', '1', '2012-12-18 14:25:39', '超级管理员', '1');
INSERT INTO business_data_property VALUES ('89', '58', ',58,', '1', '1', '3', '获得市创新基金资助额', 'DOUBLE', '', '万元', '', 'NO', 'NORMAL', '2012-12-18 14:26:10', '超级管理员', '1', '2012-12-18 14:26:10', '超级管理员', '1');

-- -------------------------------------
-- view create for customer_change_view
-- -------------------------------------


