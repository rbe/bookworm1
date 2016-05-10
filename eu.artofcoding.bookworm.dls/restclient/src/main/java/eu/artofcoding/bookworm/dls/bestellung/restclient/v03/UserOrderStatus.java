package eu.artofcoding.bookworm.dls.bestellung.restclient.v03;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class UserOrderStatus {

    private String userId;

    private List<BookOrderStatus> bookOrderStatusList;

    public UserOrderStatus() {
    }

    public UserOrderStatus(final String userId) {
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
