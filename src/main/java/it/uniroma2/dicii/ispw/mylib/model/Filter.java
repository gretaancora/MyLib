package it.uniroma2.dicii.ispw.mylib.model;

public class Filter {
    private String flt;
    private String filterType;

    public Filter(String flt, String filterType) {
        this.flt = flt;
        this.filterType = filterType;
    }

    public String getFlt() {return this.flt;}
    public String getFilterType(){return this.filterType;}
}
