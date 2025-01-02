package it.uniroma2.dicii.ispw.mylib.view.cli.costumer;

import it.uniroma2.dicii.ispw.mylib.controller.MakeReservationController;
import it.uniroma2.dicii.ispw.mylib.engineering.bean.BookBean;
import it.uniroma2.dicii.ispw.mylib.engineering.bean.BorrowBean;
import it.uniroma2.dicii.ispw.mylib.engineering.bean.FilterBean;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.BookNotFoundException;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.mylib.engineering.exceptions.NoAvailableCopy;
import it.uniroma2.dicii.ispw.mylib.engineering.singleton.Configurations;
import it.uniroma2.dicii.ispw.mylib.other.Printer;
import it.uniroma2.dicii.ispw.mylib.other.SupportedFilterTypes;
import it.uniroma2.dicii.ispw.mylib.view.cli.State;
import it.uniroma2.dicii.ispw.mylib.view.cli.StateMachine;
import it.uniroma2.dicii.ispw.mylib.model.Costumer;

import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Logger;

public class BorrowState extends State {

    private static final Logger log = Logger.getLogger(Configurations.LOGGER_NAME);

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

        var bean = new FilterBean(filter, filterType);

        var makeReservationController = new MakeReservationController();
        List<BookBean> books = null;

        try {
            books = makeReservationController.searchMethod(bean);
        } catch (DAOException e) {
            log.severe(e.getMessage());
            Printer.print("Error while making reservation");

            stateMachine.goBack();
        }

        BookBean bookBean = null;

        try {
            bookBean = selectBook(books, in);
        } catch (BookNotFoundException e) {
            this.entry(stateMachine);
        }


        if(bookBean.getAvailability().equalsIgnoreCase("not available")) {
            notifyAvailability(bookBean);
            this.entry(stateMachine);

        }else{
            var borrowBean = new BorrowBean(bookBean, costumer.getEmail());
            try {
                makeReservationController.reserveBook(borrowBean, costumer);
            } catch (NoAvailableCopy e) {
                stateMachine.goBack();
            }
            Printer.println("Reservation succeeded!\n Go into section 'show profile' in order to see it.");

            stateMachine.goBack();
        }
    }

    private void notifyAvailability(BookBean bookBean) {
        Printer.println("Tracking availability of " + bookBean.getTitle());
        Printer.println("Notify feature not implemented yet.");
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


        SupportedFilterTypes filterType = SupportedFilterTypes.values()[--choice];
        return filterType.toString();

    }


    private String getFilter(Scanner in) {
        Printer.print("Insert filter you want to use: ");
        String filter;
        while (true) {
            try {
                filter = in.nextLine();
                break;
            } catch (NoSuchElementException e) {
                Printer.println("Please insert a filter.");
            }
        }
        return filter;
    }

    private BookBean selectBook(List<BookBean> books, Scanner in) throws BookNotFoundException {

        if (books.isEmpty()) {
            Printer.println("No book found. Try to use another filter...");
            throw new BookNotFoundException();
        } else {
            Printer.printlnBlu("Select the book you want to use (insert the relative number): ");

            int i = 1;
            for (BookBean book : books) {
                Printer.print(i++ + ") " + book.toString());
                if (book.getAvailability().equalsIgnoreCase("not available")) {
                    Printer.println(" -> notify availability");
                } else {
                    Printer.println(" -> borrow");
                }
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

            return books.get(--choice);
        }
    }
}
