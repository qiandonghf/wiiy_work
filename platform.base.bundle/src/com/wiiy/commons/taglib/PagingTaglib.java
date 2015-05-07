package com.wiiy.commons.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public abstract class PagingTaglib extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String url = null;  
    private int pageIndex;  
    private int pageMax;  
  
    public void setUrl(String url) {  
        this.url = url;  
    }  
  
    public String getUrl() {  
        return this.url;  
    }  
  
    public void setPageIndex(int pageIndex) {  
        this.pageIndex = pageIndex;  
    }  
  
    public int getPageIndex() {  
        return this.pageIndex;  
    }  
  
    public void setPageMax(int pageMax) {  
        this.pageMax = pageMax;  
    }  
  
    public int getPageMax() {  
        return this.pageMax;  
    }  
  
  
    @Override  
    public int doStartTag() throws JspException {  
        String str = "";  
        if (pageIndex == 1) {  
            str += "首页 上一页 ";  
        } else {  
            str += " <a href='" + url + "pageIndex=1'>首页</a> "  
                    + "<a href='" + url + "pageIndex=" + (pageIndex - 1) + "'>上一页</a> ";  
        }  
        if (pageIndex / 6 < 1.0 || pageMax < 10) {  
            for (int i = 1; i <= 9; i++) {  
                if (i <= pageMax) {  
                    if (pageIndex != i) {  
                        str += "<a href='" + url + "pageIndex=" + i + "'>[" + i + "]</a> ";  
                    } else {  
                        str += "  " + i + " ";  
                    }  
                }  
            }  
        } else if (pageIndex / 6 >= 1.0 && pageMax >= 10) {  
            int fri = 0;  
            int max = 0;  
            if (pageMax - pageIndex > 4) {  
                fri = pageIndex - 4;  
                max = pageIndex + 4;  
            } else {  
                fri = pageMax - 8;  
                max = pageMax;  
            }  
            for (int i = fri; i <= max; i++) {  
                if (i <= pageMax) {  
                    if (pageIndex != i) {  
                        str += "<a href='" + url + "pageIndex=" + i + "'>[" + i + "]</a> ";  
                    } else {  
                        str += "  " + i + " ";  
                    }  
                }  
            }  
        }  
        if (pageIndex == pageMax || pageMax < 2) {  
            str += "下一页 尾页";  
        } else {  
            str += "<a href='" + url + "pageIndex=" + (pageIndex + 1) + "'>下一页</a> "  
                    + "<a href='" + url + "pageIndex=" + pageMax + "'>尾页</a>";  
        }  
        try {  
            if (str != "") {  
                pageContext.getOut().write(str);  
            }  
        } catch (Exception e) {  
            throw new JspException(e);  
        }  
        return EVAL_PAGE;  
    }  
}
