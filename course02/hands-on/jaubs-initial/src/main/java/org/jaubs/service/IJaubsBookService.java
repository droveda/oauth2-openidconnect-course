package org.jaubs.service;

import java.util.List;

public interface IJaubsBookService {

    void createBookItem(BookItem item);

    void updateBookItem(BookItem item);

    BookItem getItem(long id);

    void deleteItem(long id);

    void buyItem(long id);

    List<BookItem> findAllOpenItems();

    List<SoldItem> findSoldItems(String user);

}
