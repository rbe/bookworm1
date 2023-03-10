package eu.artofcoding.bookworm.catalog.web.view;

import eu.artofcoding.bookworm.common.persistence.basket.Basket;
import eu.artofcoding.bookworm.common.persistence.book.Book;

import java.util.Iterator;
import java.util.List;

public class AbstractBasketBean implements BasketBean {

    protected Basket basket = new Basket();

    private void remove(final Basket basket, final Book book) {
        if (null != book) {
            for (Iterator<Book> iterator = basket.getBooks().iterator(); iterator.hasNext(); ) {
                Book b = iterator.next();
                if (b.equals(book)) {
                    iterator.remove();
                }
            }
        }
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public Basket getBasket() {
        return basket;
    }

    @Override
    public List<Book> getBooks() {
        return basket.getBooks();
    }

    @Override
    public boolean isEmpty() {
        return basket.itemCount() == 0;
    }

    @Override
    public int getItemCount() {
        return basket.itemCount();
    }

    @Override
    public boolean isInBasket(final Book book) {
        return basket.isInBasket(book);
    }

    @Override
    public String add(final Book book) {
        basket.getBooks().add(book);
        return null;
    }

    @Override
    public String remove(final Book book) {
        remove(basket, book);
        return null;
    }

    @Override
    public void wasOrdered() {
        basket = new Basket();
    }

}
