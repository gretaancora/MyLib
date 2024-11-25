package it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.bean;


public class FilterBean {
    private String filter;
    private String filterType;

    public FilterBean(String filter) {
        this.filter = filter;
    }
    public FilterBean(String filter, String filterType) {
        this.filter = filter;
        this.filterType = filterType;
    }

    public String getFilter(){return this.filter;}
    public String getFilterType() {return this.filterType;}
}
