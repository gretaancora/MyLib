package it.uniroma2.dicii.ispw.librarymanagmentsystem.controller;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.bean.FilterBean;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.dao.MakeReservationDAO;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Book;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Filter;

import java.util.List;

public class MakeReservationController {


    public List<Book> searchMethod(FilterBean filterBean) {

        Filter filter = new Filter(filterBean.getFilter(), filterBean.getFilterType());

        var makeReservationDAO = new MakeReservationDAO();

        return makeReservationDAO.searchBooks(filter);

    }
}
