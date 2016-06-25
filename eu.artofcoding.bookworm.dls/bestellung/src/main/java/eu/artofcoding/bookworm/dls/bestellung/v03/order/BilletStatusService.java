package eu.artofcoding.bookworm.dls.bestellung.v03.order;

import org.apache.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class BilletStatusService {

    private static final Logger LOGGER = Logger.getLogger(BilletStatusService.class);

    private final BilletSender billetSender;

    @Autowired
    BilletStatusService(final BilletSender billetSender) {
        this.billetSender = billetSender;
    }

    String invoke(final String userId, final String aghNummer) {
        MDC.put("userId", userId);
        MDC.put("aghNummer", aghNummer);
        try {
            final BilletSender.ServerStatus serverStatus = billetSender.serverStatus(userId, aghNummer);
            return serverStatus.name();
        } finally {
            MDC.clear();
        }
    }

}
