package it.uniroma2.dicii.ispw.librarymanagmentsystem.view.cli.librarian;

import it.uniroma2.dicii.ispw.librarymanagmentsystem.controller.ManageReservationsController;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.bean.BorrowBean;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.engineering.exceptions.DAOException;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.model.Librarian;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.other.Printer;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.view.cli.State;
import it.uniroma2.dicii.ispw.librarymanagmentsystem.view.cli.StateMachine;

import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Logger;

public class ManagePendingReservationsState extends State {

    private static final Logger log = Logger.getLogger(ManagePendingReservationsState.class.getName());

    private Librarian librarian;

    public ManagePendingReservationsState(Librarian librarian) {
        this.librarian = librarian;
    }

    @Override
    public void execute(StateMachine stateMachine) {
        var in = new Scanner(System.in);
        var manageReservationController = new ManageReservationsController();

        List<BorrowBean> borrows = null;

        try {
            borrows = manageReservationController.getPendingReservations();
        } catch (DAOException e) {
            log.severe(e.getMessage());
            Printer.errorPrint("Error while getting pending reservations");

            stateMachine.goBack();
        }

        BorrowBean borrowBean = selectBorrow(borrows, in);

        try {
            manageReservationController.activateReservation(borrowBean);
        } catch (DAOException e) {
            log.severe(e.getMessage());
            Printer.errorPrint("Error while getting pending reservations");

            stateMachine.goBack();
        }

    }


    @Override
    public void showHeadline() {
        Printer.printlnBlu("--------------MANAGE PENDING RESERVATIONS--------------");
    }


    public BorrowBean selectBorrow(List<BorrowBean> borrows, Scanner in) {
        int i = 0;
        int choice;

        for (BorrowBean borrow : borrows) {
            Printer.println(i++ + ") " + borrow.toString());
        }

        while(true){
            try {
                choice = in.nextInt();
                in.nextLine();
                break;
            }catch (InputMismatchException e) {
                Printer.println("Please enter a valid option!");
                in.nextLine();
            } catch (NoSuchElementException e) {
                Printer.println("Please enter a valid option!");
            }
        }

        return borrows.get(choice);
    }
}
