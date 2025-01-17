package it.uniroma2.dicii.ispw.mylib.engineering.dao;

import it.uniroma2.dicii.ispw.mylib.engineering.bean.BorrowBean;
import it.uniroma2.dicii.ispw.mylib.model.Book;
import it.uniroma2.dicii.ispw.mylib.model.Borrow;
import it.uniroma2.dicii.ispw.mylib.other.Printer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ManageReservationInMemoryDAO implements ManageReservationDAO{
    private static List<Borrow> pendingBorrows = new ArrayList<>();
    private static List<Borrow> activeBorrows = new ArrayList<>();

    static {
        pendingBorrows.add(new Borrow(new Book("9788806220457"), "user1@gmail.com", (short) 1, LocalDateTime.now(), "NAR-A-1-1"));
    }

    @Override
    public BorrowBean activateReservation(Borrow borrow) {

        for (Borrow b : pendingBorrows) {
            if (b.getBook().getIsbn().equalsIgnoreCase(borrow.getBook().getIsbn()) && b.getCopy() == borrow.getCopy() && b.getCostumer().equals(borrow.getCostumer()) && b.getInReq().equals(borrow.getInReq())) {
                pendingBorrows.remove(b);
                activeBorrows.add(new Borrow(borrow.getBook(), borrow.getCostumer(), borrow.getCopy(), borrow.getInReq(), borrow.getPosition(), LocalDate.now(), LocalDate.now().plusMonths(1)));
                break;
            }
        }

        return new BorrowBean(LocalDate.now(), LocalDate.now().plusMonths(1));
    }

    @Override
    public List<Borrow> getPendingReservations() {
        return pendingBorrows;
    }
}
