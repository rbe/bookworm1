-- HOERSTP: Hoererstammdaten
CREATE TABLE hoererstamm (
  hoerernummer  VARCHAR(5)   NOT NULL PRIMARY KEY,
  anrede        VARCHAR(20)  NULL,
  vorname       VARCHAR(200) NULL,
  nachname      VARCHAR(200) NULL,
  name2         VARCHAR(200) NULL,
  strasse       VARCHAR(200) NOT NULL,
  plz           VARCHAR(10)  NOT NULL,
  ort           VARCHAR(200) NOT NULL,
  geburtsdatum  DATE,
  telefonnummer VARCHAR(25)  NULL,
  email         VARCHAR(50)  NULL,
  passwort      VARCHAR(16)  NULL
);

-- BKSTP: aktuelle Bestellkarte/Vorbestellungen
CREATE TABLE hoererstamm_aktuelle_bestellkarte (
  hoerernummer VARCHAR(4)    NOT NULL PRIMARY KEY,
  titelnummern VARCHAR(2500) NULL,
  datum_stand  DATE          NOT NULL
);

-- BKRXSTP: Archiv
CREATE TABLE hoererstamm_archiv (
  hoerernummer    VARCHAR(5) NOT NULL PRIMARY KEY,
  titelnummer     VARCHAR(5),
  datum_geliefert DATE       NOT NULL
);
