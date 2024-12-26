package it.uniroma2.dicii.ispw.MyLib.engineering.dao;

import it.uniroma2.dicii.ispw.MyLib.engineering.bean.BorrowBean;
import it.uniroma2.dicii.ispw.MyLib.model.Book;
import it.uniroma2.dicii.ispw.MyLib.model.Borrow;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ManageReservationInMemoryDAO implements ManageReservationDAO{
    private List<Borrow> pendingBorrows = new ArrayList<>();
    private List<Borrow> activeBorrows = new ArrayList<>();

    public ManageReservationInMemoryDAO() {
        pendingBorrows.add(new Borrow(new Book("9788806220457"), "user1@gmail.com", (short) 1, LocalDateTime.now(), "NAR-A-1-1"));
    }

    @Override
    public BorrowBean activateReservation(Borrow borrow) {
        for (Borrow b : pendingBorrows) {
            if (b.getBook().getISBN().equalsIgnoreCase(borrow.getBook().getISBN()) && b.getCopy() == borrow.getCopy() && b.getCostumer().equals(borrow.getCostumer()) && b.getInReq().equals(borrow.getInReq())) {
                pendingBorrows.remove(borrow);
                activeBorrows.add(new Borrow(borrow.getBook(), borrow.getCostumer(), borrow.getCopy(), borrow.getInReq(), borrow.getPosition(), LocalDate.now(), LocalDate.now().plusMonths(1)));
            }
        }

        return new BorrowBean(LocalDate.now(), LocalDate.now().plusMonths(1));
    }

    @Override
    public List<Borrow> getPendingReservations() {
        return pendingBorrows;
    }
}
