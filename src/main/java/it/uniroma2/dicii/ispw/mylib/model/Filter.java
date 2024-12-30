package it.uniroma2.dicii.ispw.mylib.model;

public class Filter {
    private String filter;
    private String filterType;

    public Filter(String filter, String filterType) {
        this.filter = filter;
        this.filterType = filterType;
    }

    public String getFilter() {return this.filter;}
    public String getFilterType(){return this.filterType;}
}
