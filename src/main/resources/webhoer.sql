CREATE OR REPLACE VIEW v_webhoer AS
  SELECT
    bo.hoerernummer HOENR
    , bob.books_titelnummer TITNR
    , b.aghnummer TIAGNR
    , 12345678901 DLSID -- TODO bo.dlsid
    , DATE_FORMAT(NOW(), '%Y%m%d') ABFRDT
    , DATE_FORMAT(NOW(), '%H%i%s') ABFRZT
    , 3 STATUS -- TODO
    , DATE_FORMAT(bo.ausleihdatum, '%Y%m%d') AUSLDT
    , DATE_FORMAT(bo.ausleihdatum, '%H%i%s') AUSLZT
    , DATE_FORMAT(bo.ausleihdatum, '%Y%m%d') RUEGDT -- TODO
    , DATE_FORMAT(bo.ausleihdatum, '%H%i%s') RUEGZT -- TODO
  FROM BlistaOrder bo
    INNER JOIN BlistaOrder_Book bob ON bo.id = bob.blistaorder_id
    INNER JOIN Book b ON bob.books_titelnummer = b.titelnummer
    INNER JOIN HoererBuchstamm hbs ON hbs.hoerernummer = bo.hoerernummer
;

DELIMITER $$

CREATE EVENT webhoer_csv
  ON SCHEDULE EVERY 1 DAY
DO BEGIN
  SELECT
    CONCAT(
        LPAD(hoenr, 5, ' ')
        , LPAD(titnr, 6, ' ')
        , LPAD(tiagnr, 13, ' ')
        , LPAD(dlsid, 11, ' ')
        , LPAD(abfrdt, 8, ' ')
        , LPAD(abfrzt, 6, ' ')
        , LPAD(status, 1, ' ')
        , LPAD(ausldt, 8, ' ')
        , LPAD(auslzt, 6, ' ')
        , LPAD(ruegdt, 8, ' ')
        , LPAD(ruegzt, 6, ' ')
    )
  FROM v_webhoer
  INTO OUTFILE '/tmp/webhoer.csv'
    -- FIELDS ENCLOSED BY ''
    -- FIELDS TERMINATED BY ''
    -- ESCAPED BY ''
  LINES TERMINATED BY '\r\n'
  ;

END $$

DELIMITER ;
