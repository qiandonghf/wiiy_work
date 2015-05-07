package com.wiiy.commons.util;


import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class MicrosoftWordUtil {
	
	/**
	 * word文档
	 */
	private Dispatch doc;
	/**
	 * word运行程序对象
	 */
	private ActiveXComponent word;
	/**
	 * 所有word文档集合
	 */
	private Dispatch documents;
	/**
	 * 选定的范围或插入点
	 */
	private Dispatch selection;
	/**
	 * 退出时保存
	 */
	private boolean saveOnExit = true;
	
	
	/**
	 * 构造
	 */
	public MicrosoftWordUtil() {
		if (word == null) {
			word = new ActiveXComponent("Word.Application");
			word.setProperty("Visible", new Variant(false));
		}
		if (documents == null)
			documents = word.getProperty("Documents").toDispatch();
	}

	/**
	 * 创建一个新的word文档
	 * 
	 */
	public void createNewDocument() {
		doc = Dispatch.call(documents, "Add").toDispatch();
		selection = Dispatch.get(word, "Selection").toDispatch();
	}

	/**
	 * 打开一个已存在的文档
	 * 
	 * @param docPath
	 */
	public void openDocument(String docPath) {
		closeDocument();
		doc = open(documents, docPath);
		selection = Dispatch.get(word, "Selection").toDispatch();
	}
	
	public Dispatch open(Dispatch documents,String docPath){
		return Dispatch.call(documents, "Open", docPath).toDispatch();
	}
	
	/**
	 * 获取选定的文本内容
	 * @return
	 */
	public String getSelectionText(){
		return Dispatch.get(selection, "Text").getString();
	}
	
	/**
	 * 换行
	 * @throws Exception
	 */
	public void enter() throws Exception {
		Dispatch.call(selection, "TypeParagraph");
	}
	
	/**
	 * 设置颜色
	 * @param color RGB颜色的十进制表示(OxFFFFFF->"16,77,72,15") 两位数一组 ，隔开
	 * @throws Exception
	 */
	public void setColor(String color) throws Exception {
		Dispatch font = Dispatch.get(selection, "Font").toDispatch();
		Dispatch.put(font, "Color", color);
	}

	/**
	 * 把选定的内容或插入点向上移动
	 * 
	 * @param pos
	 *            移动的距离
	 */
	public void moveUp(int pos) {
		if (selection == null)
			selection = Dispatch.get(word, "Selection").toDispatch();
		for (int i = 0; i < pos; i++)
			Dispatch.call(selection, "MoveUp");

	}

	/**
	 * 把选定的内容或插入点向下移动
	 * 
	 * @param pos
	 *            移动的距离
	 */
	public void moveDown(int pos) {
		if (selection == null)
			selection = Dispatch.get(word, "Selection").toDispatch();
		for (int i = 0; i < pos; i++)
			Dispatch.call(selection, "MoveDown");
	}

	/**
	 * 把选定的内容或插入点向左移动
	 * 
	 * @param pos
	 *            移动的距离
	 */
	public void moveLeft(int pos) {
		if (selection == null)
			selection = Dispatch.get(word, "Selection").toDispatch();
		for (int i = 0; i < pos; i++) {
			Dispatch.call(selection, "MoveLeft");
		}
	}

	/**
	 * 把选定的内容或插入点向右移动
	 * 
	 * @param pos
	 *            移动的距离
	 */
	public void moveRight(int pos) {
		if (selection == null)
			selection = Dispatch.get(word, "Selection").toDispatch();
		for (int i = 0; i < pos; i++)
			Dispatch.call(selection, "MoveRight");
	}

	/**
	 * 把插入点移动到文件首位置
	 * 
	 */
	public void moveStart() {
		if (selection == null)
			selection = Dispatch.get(word, "Selection").toDispatch();
		Dispatch.call(selection, "HomeKey", new Variant(6));
	}

	/**
	 * 从选定内容或插入点开始查找文本
	 * 
	 * @param toFindText
	 *            要查找的文本
	 * @return boolean true-查找到并选中该文本，false-未查找到文本
	 */
	public boolean find(String toFindText) {
		if (toFindText == null || ("").equals(toFindText))
			return false;
//		从selection所在位置开始查询
		Dispatch find = Dispatch.call(selection, "Find").toDispatch();
//		设置要查找的内容
		Dispatch.put(find, "Text", toFindText);
//		向前查找
		Dispatch.put(find, "Forward", "True");
//		忽略格式
		Dispatch.call(find, "ClearFormatting");
//		设置格式
		Dispatch.put(find, "Format", "True");
//		大小写匹配
		Dispatch.put(find, "MatchCase", "True");
//		全字匹配
//		Dispatch.put(find, "MatchWholeWord", "True");
//		查找并选中
		return Dispatch.call(find, "Execute").getBoolean();
	}

	/**
	 * 把选定选定内容设定为替换文本
	 * 
	 * @param toFindText
	 *            查找字符串
	 * @param newText
	 *            要替换的内容
	 * @return
	 */
	public boolean replaceText(String toFindText, String newText) {
		if (!find(toFindText))
			return false;
		Dispatch.put(selection, "Text", newText);
		return true;
	}

	/**
	 * 全局替换文本
	 * 
	 * @param toFindText
	 *            查找字符串
	 * @param newText
	 *            要替换的内容
	 */
	public void replaceAllText(String toFindText, String newText) {
		while (find(toFindText)) {
			Dispatch.put(selection, "Text", newText);
			Dispatch.call(selection, "MoveRight");
		}
	}

	/**
	 * 在当前插入点插入字符串
	 * 
	 * @param newText
	 *            要插入的新字符串
	 */
	public void insertText(String newText) {
		Dispatch.call(selection, "TypeText", newText);
	}

	/**
	 * 把选定选定内容设定为替换图片
	 * @param toFindText
	 *            要查找的字符串
	 * @param imagePath
	 *            图片路径
	 * @return
	 */
	public boolean replaceImage(String toFindText, String imagePath) {
		if (!find(toFindText))
			return false;
		Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(), "AddPicture", imagePath);
		return true;
	}

	/**
	 * 全局替换图片
	 * 
	 * @param toFindText
	 *            查找字符串
	 * @param imagePath
	 *            图片路径
	 */
	public void replaceAllImage(String toFindText, String imagePath) {
		while (find(toFindText)) {
			Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(), "AddPicture", imagePath);
			Dispatch.call(selection, "MoveRight");
		}
	}

	/**
	 * 在当前插入点插入图片
	 * 
	 * @param imagePath
	 *            图片路径
	 */
	public void insertImage(String imagePath) {
		Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(), "AddPicture", imagePath);
	}
	
	/**
	 * 获取总数
	 * @return
	 */
	public int getCount(Dispatch items){
		return Dispatch.get(items, "Count").getInt();
	}
	
	/**
	 * 获取所有表格
	 * @return
	 */
	public Dispatch getTables(){
		return getTables(doc);
	}
	
	/**
	 * 获取所有表格
	 * @return
	 */
	public Dispatch getTables(Dispatch docment){
		return Dispatch.get(docment, "Tables").toDispatch();
	}
	
	/**
	 * 获取表格
	 * @param tableIndex
	 *             word文件中的第N张表(从1开始)
	 * @return
	 */
	public Dispatch getTable(int tableIndex){
		return getItem(getTables(), tableIndex);
	}

	/**
	 * 获取第一个元素
	 * @param items
	 * @return
	 */
	public Dispatch getFirst(Dispatch items){
		return Dispatch.get(items, "First").toDispatch();
	}
	
	/**
	 * 获取最后一个元素
	 * @param items
	 * @return
	 */
	public Dispatch getLast(Dispatch items){
		return Dispatch.get(items, "Last").toDispatch();
	}
	
	/**
	 * 获取指定元素
	 * @param items
	 * @param index
	 * @return
	 */
	public Dispatch getItem(Dispatch items,int index){
		return Dispatch.call(items, "Item", new Variant(index)).toDispatch();
	}
	
	/**
	 * 将指定元素添加到元素集中
	 * @param items
	 * @param item
	 */
	public void addItem(Dispatch items,Dispatch item){
		Dispatch.call(items, "Add", item);
	}
	
	/**
	 * 在元素集后添加一个空元素
	 * @param items
	 */
	public void addItem(Dispatch items){
		Dispatch.call(items, "Add");
	}
	
	/**
	 * 获取一列
	 * @param table
	 * @param cellColIdx
	 * @return
	 */
	public Dispatch getCol(Dispatch table, int cellColIdx){
		return Dispatch.call(table, "Column", new Variant(cellColIdx)).toDispatch();
	}
	
	/**
	 * 获取一行
	 * @param table
	 * @param cellColIdx
	 * @return
	 */
	public Dispatch getRow(Dispatch table, int cellRowIdx){
		return Dispatch.call(table, "Row", new Variant(cellRowIdx)).toDispatch();
	}
	
	/**
	 * 获取一单元格
	 * @param table
	 * @param cellColIdx
	 * @return
	 */
	public Dispatch getCell(Dispatch table, int cellRowIdx, int cellColIdx){
		return Dispatch.call(table, "Cell", new Variant(cellRowIdx), new Variant(cellColIdx)).toDispatch();
	}
	
	/**
	 * 合并单元格
	 * 
	 * @param tableIndex
	 * @param fstCellRowIdx
	 * @param fstCellColIdx
	 * @param secCellRowIdx
	 * @param secCellColIdx
	 */
	public void mergeCell(int tableIndex, int fstCellRowIdx, int fstCellColIdx, int secCellRowIdx, int secCellColIdx) {
		Dispatch table = getTable(tableIndex);
		Dispatch fstCell = getCell(table, fstCellRowIdx, fstCellColIdx);
		Dispatch secCell = getCell(table, secCellRowIdx, secCellColIdx);
		Dispatch.call(fstCell, "Merge", secCell);
	}
	
	/**
	 * 拆分单元格
	 * 
	 * @param tableIndex
	 * @param cellRowIdx
	 * @param cellColIdx
	 * @param rows
	 * @param cols
	 */
	public void splitCell(int tableIndex, int cellRowIdx, int cellColIdx, int rows, int cols) {
		Dispatch table = getTable(tableIndex);
		Dispatch cell = getCell(table, cellRowIdx, cellColIdx);
		float height = getRowHeight(cell);
		Dispatch.call(cell, "Split", new Variant(rows), new Variant(cols));
		setRowHeight(getCell(table, cellRowIdx, cellColIdx), height);
		setRowHeight(getCell(table, cellRowIdx+1, cellColIdx), height);
	}

	/**
	 * 设置行高
	 * @param rows
	 * @param height
	 */
	public void setRowHeight(Dispatch tableItem, float height){
		Dispatch.put(tableItem, "Height", new Variant(height)); 
	}
	
	/**
	 * 设置列宽
	 * @param rows
	 * @param height
	 */
	public void setColumnWidth(Dispatch tableItem, float width){
		Dispatch.put(tableItem, "Width", new Variant(width)); 
	}
	
	/**
	 * 获取行高
	 * @param rows
	 * @param height
	 */
	public float getRowHeight(Dispatch tableItem){
		return Dispatch.get(tableItem, "Height").getFloat(); 
	}
	
	/**
	 * 获取列宽
	 * @param rows
	 * @param height
	 */
	public float getColumnWidth(Dispatch tableItem){
		return Dispatch.get(tableItem, "Width").getFloat(); 
	}
	
	/**
	 * 在指定的单元格里填写数据
	 * 
	 * @param tableIndex
	 * @param cellRowIdx
	 * @param cellColIdx
	 * @param txt
	 */
	public void writeCell(int tableIndex, int cellRowIdx, int cellColIdx, String value) {
		Dispatch table = getTable(tableIndex);
		Dispatch cell = Dispatch.call(table, "Cell", new Variant(cellRowIdx),new Variant(cellColIdx)).toDispatch();
		Dispatch.call(cell, "Select");
		Dispatch.put(selection, "Text", value);
	}
	
	/**
	 * 在指定的单元格里读取数据
	 * 
	 * @param tableIndex
	 * @param cellRowIdx
	 * @param cellColIdx
	 * @return 
	 */
	public String readCell(int tableIndex, int cellRowIdx, int cellColIdx) {
		Dispatch table = getTable(tableIndex);
		Dispatch cell = Dispatch.call(table, "Cell", new Variant(cellRowIdx),new Variant(cellColIdx)).toDispatch();
		Dispatch.call(cell, "Select");
		return Dispatch.get(selection, "Text").getString();
	}

	/**
	 * 在当前文档指定的位置粘贴剪贴板数据
	 * 
	 * @param position
	 *            粘贴的位置 (通过此文本查找位置)
	 */
	public void paste() {
		Dispatch.call(getRange(selection), "Paste");
	}
	
	/**
	 * 复制
	 * @param range
	 */
	public void copy(Dispatch range){
		Dispatch.call(range, "Copy");
	}
	
	/**
	 * 获取当前文档内可以修改的部分
	 * @param dispatch
	 * @return
	 */
	public Dispatch getRange(Dispatch dispatch){
		return Dispatch.get(dispatch, "Range").toDispatch();
	}

	/**
	 * 在当前文档指定的位置粘贴指定的表格
	 * 
	 * @param position
	 *            当前文档指定的位置
	 * @param tableIndex
	 *            word文件中的第N张表(从1开始)
	 */
	public void copyTable(int tableIndex) {
		Dispatch table = getTable(tableIndex);
		Dispatch range = getRange(table);
		copy(range);
	}

	/**
	 * 在当前文档指定的位置拷贝来自另一个文档中的表格
	 * 
	 * @param anotherDocPath
	 *            另一个文档的磁盘路径
	 * @param tableIndex
	 *            word文件中的第N张表(从1开始)
	 */
	public void copyTableFromAnotherDoc(String anotherDocPath, int tableIndex) {
		Dispatch doc2 = null;
		try {
			doc2 = open(documents, anotherDocPath);
			Dispatch tables = getTables(doc2);
			Dispatch table = getItem(tables, tableIndex);
			Dispatch range = getRange(table);
			copy(range);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (doc2 != null) {
				Dispatch.call(doc2, "Close", new Variant(saveOnExit));
				doc2 = null;
			}
		}
	}

	/**
	 * 在当前文档指定的位置拷贝来自另一个文档中的图形
	 * 
	 * @param anotherDocPath
	 *            另一个文档的磁盘路径
	 * @param shapeIndex
	 *            被拷贝的图形在另一格文档中的位置
	 */
	public void copyShapeFromAnotherDoc(String anotherDocPath, int shapeIndex) {
		Dispatch doc2 = null;
		try {
			doc2 = open(documents, anotherDocPath);
			Dispatch shapes = getTables(doc2);
			Dispatch shape = getItem(shapes, shapeIndex);
			Dispatch range = getRange(shape);
			copy(range);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (doc2 != null) {
				Dispatch.call(doc2, "Close", new Variant(saveOnExit));
				doc2 = null;
			}
		}
	}

	/**
	 * 创建表格
	 * 
	 * @param pos
	 *            位置
	 * @param cols
	 *            列数
	 * @param rows
	 *            行数
	 */
	public void createTable(String pos, int numCols, int numRows) {
		if (find(pos)) {
			Dispatch tables = getTables();
			Dispatch range = Dispatch.get(selection, "Range").toDispatch();
			Dispatch.call(tables, "Add", range, new Variant(numRows), new Variant(numCols));
			Dispatch.call(selection, "MoveRight");
		}
	}

	/**
	 * 获取文档指定表格的所有行
	 * @param table
	 * @return
	 */
	public Dispatch getRows(Dispatch table){
		return Dispatch.get(table, "Rows").toDispatch();
	}
	
	/**
	 * 在指定行前面插入一行
	 * 
	 * @param tableIndex
	 *            word文件中的第N张表(从1开始)
	 * @param rowIndex
	 *            指定行的序号(从1开始)
	 */
	public void insertRow(int tableIndex, int rowIndex) {
		Dispatch table = getTable(tableIndex);
		Dispatch rows = getRows(table);
		Dispatch row = getItem(rows, rowIndex);
		addItem(rows, row);
	}
	
	/**
	 * 在指定行后面增加一行
	 * 
	 * @param tableIndex
	 *            word文件中的第N张表(从1开始)
	 * @param rowIndex
	 *            指定行的序号(从1开始)
	 */
	public void addRow(int tableIndex, int rowIndex) {
		Dispatch table = getTable(tableIndex);
		Dispatch rows = getRows(table);
		Dispatch row = getItem(rows, rowIndex+1);
		System.out.println(row);
//		addItem(rows, row);
	}

	/**
	 * 在第一行前插入一行
	 * 
	 * @param tableIndex
	 *            word文档中的第N张表(从1开始)
	 */
	public void addFirstRow(int tableIndex) {
		Dispatch table = getTable(tableIndex);
		Dispatch rows = getRows(table);
		Dispatch row = getFirst(rows);
		addItem(rows, row);
	}

	/**
	 * 在最后一行前插入一行
	 * 
	 * @param tableIndex
	 *            word文档中的第N张表(从1开始)
	 */
	public void addLastRow(int tableIndex) {
		Dispatch table = getTable(tableIndex);
		Dispatch rows = getRows(table);
		Dispatch row = getLast(rows);
		addItem(rows, row);
	}

	/**
	 * 增加一行
	 * 
	 * @param tableIndex
	 *            word文档中的第N张表(从1开始)
	 */
	public void addRow(int tableIndex) {
		Dispatch table = getTable(tableIndex);
		Dispatch rows = getRows(table);
		addItem(rows);
	}
	
	/**
	 * 获取文档指定表格的所有列
	 * @param table
	 * @return
	 */
	public Dispatch getColumns(Dispatch table){
		return Dispatch.get(table, "Columns").toDispatch();
	}

	/**
	 * 增加一列
	 * 
	 * @param tableIndex
	 *            word文档中的第N张表(从1开始)
	 */
	public void addCol(int tableIndex) {
		Dispatch table = getTable(tableIndex);
		Dispatch cols = getColumns(table);
		addItem(cols);
	}

	/**
	 * 在指定列前面插入一列
	 * 
	 * @param tableIndex
	 *            word文档中的第N张表(从1开始)
	 * @param colIndex
	 *            制定列的序号 (从1开始)
	 */
	public void insertCol(int tableIndex, int colIndex) {
		Dispatch table = getTable(tableIndex);
		Dispatch cols = getColumns(table);
		Dispatch col = getItem(cols, colIndex);
		addItem(cols,col);
	}
	
	/**
	 * 在指定列后面增加一列
	 * 
	 * @param tableIndex
	 *            word文档中的第N张表(从1开始)
	 * @param colIndex
	 *            制定列的序号 (从1开始)
	 */
	public void addCol(int tableIndex, int colIndex) {
		Dispatch table = getTable(tableIndex);
		Dispatch cols = getColumns(table);
		Dispatch col = getItem(cols, colIndex+1);
		addItem(cols,col);
	}

	/**
	 * 在第一列前插入一列
	 * 
	 * @param tableIndex
	 *            word文档中的第N张表(从1开始)
	 */
	public void addFirstCol(int tableIndex) {
		Dispatch table = getTable(tableIndex);
		Dispatch cols = getColumns(table);
		Dispatch col = getFirst(cols);
		addItem(cols,col);
	}

	/**
	 * 在最后一列前插入一列
	 * 
	 * @param tableIndex
	 *            word文档中的第N张表(从1开始)
	 */
	public void addLastCol(int tableIndex) {
		Dispatch table = getTable(tableIndex);
		Dispatch cols = getColumns(table);
		Dispatch col = getLast(cols);
		addItem(cols,col);
	}
	
	/**
	 * 自动调整列
	 * @param table
	 */
	public void autoFit(Dispatch cols){
		Dispatch.call(cols, "AutoFit");
	}

	/**
	 * 自动调整表格列
	 * 
	 */
	public void autoFitTable() {
		Dispatch tables = getTables();
		for (int i = 0; i < getCount(tables); i++) {
			Dispatch table = getItem(tables, i + 1);
			Dispatch cols = getColumns(table);
			autoFit(cols);
		}
	}

	/**
	 * 调用word里的宏以调整表格的宽度,其中宏保存在document下
	 * 
	 */
	public void callWordMacro() {
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		int count = Dispatch.get(tables, "Count").getInt();
		for (int i = 0; i < count; i++) {
			Dispatch table = Dispatch.call(tables, "Item", new Variant(i + 1)).toDispatch();
			Dispatch.call(table, "Select");
			Dispatch.call(word, "Run", "tableFitContent");
		}
	}

	/**
	 * 设置当前选定内容的字体
	 * 
	 * @param boldSize
	 * @param italicSize
	 * @param underLineSize
	 *            下划线
	 * @param colorSize
	 *            字体颜色
	 * @param size
	 *            字体大小
	 * @param name
	 *            字体名称
	 */
	public void setFont(boolean bold, boolean italic, boolean underLine, String colorSize, String size, String name) {
		Dispatch font = Dispatch.get(selection, "Font").toDispatch();
		Dispatch.put(font, "Name", new Variant(name));
		Dispatch.put(font, "Bold", new Variant(bold));
		Dispatch.put(font, "Italic", new Variant(italic));
		Dispatch.put(font, "Underline", new Variant(underLine));
		Dispatch.put(font, "Color", colorSize);
		Dispatch.put(font, "Size", size);
	}

	/**
	 * 文件保存或另存为
	 * 
	 * @param savePath
	 *            保存或另存为路径
	 */
	public void save(String savePath) {
		Dispatch.call(Dispatch.call(word, "WordBasic").getDispatch(), "FileSaveAs", savePath);
	}

	/**
	 * 关闭当前word文档
	 * 
	 */
	public void closeDocument() {
		if (doc != null) {
			Dispatch.call(doc, "Save");
			Dispatch.call(doc, "Close", new Variant(saveOnExit));
			doc = null;
		}
	}

	/**
	 * 关闭全部应用
	 * 
	 */
	public void close() {
		closeDocument();
		if (word != null) {
			Dispatch.call(word, "Quit");
			word = null;
		}
		selection = null;
		documents = null;
	}

	/**
	 * 打印当前word文档
	 * 
	 */
	public void printFile() {
		if (doc != null) {
			Dispatch.call(doc, "PrintOut");
		}
	}


	public void setSaveOnExit(boolean saveOnExit) {
		this.saveOnExit = saveOnExit;
	}
	
}
