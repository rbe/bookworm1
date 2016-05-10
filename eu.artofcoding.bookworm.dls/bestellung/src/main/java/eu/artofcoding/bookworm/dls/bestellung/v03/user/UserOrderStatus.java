package eu.artofcoding.bookworm.dls.bestellung.v03.user;

import eu.artofcoding.bookworm.dls.bestellung.v03.book.BookOrderStatus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
class UserOrderStatus {

    private String userId;

    private List<BookOrderStatus> bookOrderStatusList = new ArrayList<>();

    UserOrderStatus() {
    }

    UserOrderStatus(final String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public List<BookOrderStatus> getBookOrderStatusList() {
        return bookOrderStatusList;
    }

    public void setBookOrderStatusList(final List<BookOrderStatus> bookOrderStatusList) {
        this.bookOrderStatusList = bookOrderStatusList;
    }

}
