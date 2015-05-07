package com.wiiy.commons.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wiiy.commons.entity.TreeEntity;

public class TreeUtil {

	@SuppressWarnings("rawtypes")
	public static void printTree(List nodes){
		for (Object o : nodes) {
			TreeEntity node = (TreeEntity)o;
			for (int i = 0; i < node.getLevel(); i++) {
				System.out.print("--");
			}
			System.out.println(node.getText()+"  "+node.getParentIds());
			if(node.getChildren()!=null && node.getChildren().size()>0)
				printTree(node.getChildren());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List generateTree(List nodes) {
		Collections.sort(nodes, new Comparator<TreeEntity>(){
			@Override
			public int compare(TreeEntity o1, TreeEntity o2) {
				if(o1.getLevel()==null)return -1;
				if(o2.getLevel()==null)return 1;
				return o2.getLevel()-o1.getLevel();
			}
		});
		int level = -1;
		if(nodes!=null && nodes.size()>0){
			level = ((TreeEntity)nodes.get(0)).getLevel();
		}
		if(level==0){
			return nodes;
		} else if(level>0){
			Map<Long,TreeEntity> parentMap = new HashMap<Long,TreeEntity>();
			Map<Long,TreeEntity> childrenMap = new HashMap<Long,TreeEntity>();
			Map<Long,TreeEntity> treeMap = new HashMap<Long,TreeEntity>();
			for (Object o : nodes) {
				TreeEntity node = (TreeEntity)o;
				if(node.getLevel()==level){
					childrenMap.put(node.getId(), node);
				} else if(node.getLevel()==level-1){
					parentMap.put(node.getId(), node);
				} else {
					treeMap.put(node.getId(), node);
				}
			}
			return generateTree(treeMap,parentMap,childrenMap,level-2);
		}
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static List generateTree(Map<Long,TreeEntity> treeMap,Map<Long,TreeEntity> parentMap,Map<Long,TreeEntity> childrenMap,Integer level){
		for (Long key : childrenMap.keySet()) {
			TreeEntity child = childrenMap.get(key);
			Long parentId = child.getParentId();
			if(parentMap.get(parentId).getChildren()==null){
				parentMap.get(parentId).setChildren(new ArrayList());
			}
			parentMap.get(parentId).getChildren().add(child);
		}
		childrenMap.clear();
		if(level==-1){
			List nodeList = new ArrayList();
			for (Long key : parentMap.keySet()) {
				nodeList.add(parentMap.get(key));
			}
			return nodeList;
		}
		List<Long> removeKey = new ArrayList<Long>();
		for (Long key : treeMap.keySet()) {
			TreeEntity child = treeMap.get(key);
			if(child.getLevel().intValue()==level.intValue()){
				childrenMap.put(child.getId(),child);
				removeKey.add(key);
			}
		}
		for (Long key : removeKey) {
			treeMap.remove(key);
		}
		return generateTree(treeMap, childrenMap, parentMap, level-1);
	}
	
}
