package javautils.jdbc;

import java.util.ArrayList;
import java.util.List;

public class PageList
{
    private List<?> list;
    private int count;
    
    public PageList() {
        this.list = new ArrayList<Object>();
        this.count = 0;
    }
    
    public PageList(final List<?> list, final int count) {
        this.list = list;
        this.count = count;
    }
    
    public List<?> getList() {
        return this.list;
    }
    
    public void setList(final List<?> list) {
        this.list = list;
    }
    
    public int getCount() {
        return this.count;
    }
    
    public void setCount(final int count) {
        this.count = count;
    }
}
