package it.uniroma2.dicii.ispw.librarymanagmentsystem.view.cli.costumer;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.controller.MakeReservationController;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.bean.BookBean;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.bean.BorrowBean;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.bean.FilterBean;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.other.Printer;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.other.SupportedFilterTypes;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.view.cli.State;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.view.cli.StateMachine;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Costumer;

import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Logger;

public class BorrowState extends State {

    private static final Logger log = Logger.getLogger(BorrowState.class.getName());

    private Costumer costumer;

    public BorrowState(Costumer costumer) {
        super();
        this.costumer = costumer;
    }


    @Override
    public void execute(StateMachine stateMachine) {
        var in = new Scanner(System.in);

        String filterType = getFilterType(in);
        String filter = getFilter(in);

        var bean = new FilterBean(filterType, filter);

        var makeReservationController = new MakeReservationController();
        List<BookBean> books = null;

        try {
            books = makeReservationController.searchMethod(bean);
        } catch (DAOException e) {
            log.severe(e.getMessage());
            Printer.print("Error while making reservation");

            stateMachine.goBack();
        }

        BookBean bookBean = selectBook(books, in);

        var borrowBean = new BorrowBean(bookBean, costumer.getEmail());
        makeReservationController.reserveBook(borrowBean);

        stateMachine.goBack();
    }


    @Override
    public void showHeadline() {
        Printer.printlnBlu("--------------BORROW--------------");
    }

    public String getFilterType(Scanner in) {
        int choice;

        while (true) {
            try {
                Printer.println("Insert the type of filter you want to use: ");
                Printer.println("1) title");
                Printer.println("2) author");
                Printer.println("3) all fields");

                choice = in.nextInt();
                in.nextLine();
                break;

            } catch (InputMismatchException e) {
                Printer.println("Please enter a valid option!");
                in.nextLine();
            } catch (NoSuchElementException e) {
                Printer.println("Please enter a valid option!");
            }
        }

        SupportedFilterTypes filterType = SupportedFilterTypes.values()[choice];
        return filterType.toString();
    }


    private String getFilter(Scanner in) {
    Printer.print("Insert filter you want to use: ");
    String filter = in.nextLine();
    return filter;
    }

    private BookBean selectBook(List<BookBean> books, Scanner in) {
        Printer.printlnBlu("Select the book you want to use (insert the relative number): ");

        int i = 0;
        for (BookBean book : books) {
            Printer.println(i++ + ") " + book.toString());
        }

        int choice;
        while (true) {
            try {
                choice = in.nextInt();
                in.nextLine();
                break;

            } catch (InputMismatchException e) {
                Printer.println("Insert a valid option!");
                in.nextLine();
            } catch (NoSuchElementException e) {
                Printer.println("Please enter a valid option!");
            }
        }

        return books.get(choice);
    }
}
