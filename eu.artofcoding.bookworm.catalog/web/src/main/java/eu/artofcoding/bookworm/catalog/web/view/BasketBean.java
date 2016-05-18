package eu.artofcoding.bookworm.catalog.web.view;

import eu.artofcoding.bookworm.common.persistence.basket.Basket;
import eu.artofcoding.bookworm.common.persistence.book.Book;

import java.io.Serializable;

public interface BasketBean extends Serializable {

    String getName();

    Basket getBasket();

    boolean isEmpty();

    int getItemCount();

    Basket getOrderedBasket();

    boolean isInBasket(Book book);

    String add(Book book);

    String remove(Book book);

    void wasOrdered();

}
