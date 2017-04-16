package eu.artofcoding.bookworm.dls.bestellung.v03.order;

import org.apache.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class PlaceBilletService {

    private static final Logger LOGGER = Logger.getLogger(PlaceBilletService.class);

    private final BilletSender billetSender;

    @Autowired
    PlaceBilletService(BilletSender billetSender) {
        this.billetSender = billetSender;
    }

    BookOrder invoke(final String userId, final String aghNummer) {
        MDC.put("userId", userId);
        MDC.put("aghNummer", aghNummer);
        try {
            final String abrufkennwort = billetSender.sendToServer(userId, aghNummer);
            final BilletSender.ServerStatus serverStatus = billetSender.serverStatus(userId, aghNummer);
            return new BookOrder(abrufkennwort, serverStatus.name());
        } catch (Exception e) {
            LOGGER.error("Konnte Bestellung nicht aufgeben", e);
            return null;
        } finally {
            MDC.clear();
        }
    }

}
