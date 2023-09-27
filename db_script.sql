create schema db_example;
use db_example;

/*Questa tabella contiene una lista di tutte le utenze,
siano esse clienti o macchine.*/

create table utenti (
	id int auto_increment primary key,
    tipo_utente varchar(16),
    check(tipo_utente in ("cliente", "macchina"))
);

/*Questa tabella tiene traccia delle informazioni
anagrafiche dei clienti utili all'applicazione.
La data di nascita serve solo per impedire l'iscrizione
a minorenni*/

create table clienti (
	ref_id int references utente(id),
	nome varchar(64) not null,
    cognome varchar(64) not null,
    data_di_nascita date not null,
    codice_fiscale char(16) primary key
);

/*Questa tabella contiene tutte le credenziali della
applicazione. In questo caso la chiave primaria è solo
l'email per evitare che più utenti abbiano la stessa
e-mail.*/
create table credenziali (
	ref_id int references utente(id),
    email varchar(64) not null primary key,
    /*La password sarà di dimensione fissa poiché
    conservata nel DB con hash SHA-256.*/
    keyword char(64) not null
);

/*Questa tabella tiene traccia di tutte le carte 
(indipendentemente dal fatto che siano di debito/credito/
prepagate, ecc.) usate all'interno dell'applicazione e
associate ad utenti della stessa*/
create table carte (
	ref_codice_fiscale char(16),
    /*Per il numero della carta, sono esattamente 16 cifre*/
    numero char(16),
    data_scadenza date,
    /*Il CVV è utilizzato per aggiungere un fattore di
    sicurezza alle transazioni con carta. È modellato
    come un intero di tre cifre (il tipo è smallint perchè
    occupa meno spazio nel DB).*/
    cvv smallint,
    check (cvv >= 100 and cvv < 1000),
    /*Per il saldo, massimo 10 cifre prima della virgola e
    2 dopo la virgola (fino ai centesimi)*/
    saldo decimal(10,2),
    /*Ogni carta è caratterizzata dalla coppia
    (possessore, numero), dato che più carte possono
    essere associate allo stesso utente e scadere lo stesso
    giorno, pur avendo numero diverso*/
    primary key (ref_codice_fiscale, numero),
    /*Una carta non può appartenere a un utente non nel DB*/
    foreign key (ref_codice_fiscale) references clienti(codice_fiscale)
);

/*Questa tabella tiene traccia di tutte le macchinette
a disposizione, in particolare dello stato in cui sono e,
indirettamente, della possibilità di essere adoperate o
meno.*/

create table macchine (
	ref_id smallint primary key references utenti(id),
    indirizzo varchar(64) not null,
    stato boolean not null
);

create table prodotti (
	id smallint primary key,
    nome varchar(32),
    prezzo decimal(3,2)
);

/*Questa tabella esplicita le connessioni delle macchinette*/

create table connessioni (
	ref_id_macchina smallint primary key references macchine.ref_id,
    ref_cf_cliente char(16) references clienti.codice_fiscale
);

/*Questa tabella tiene traccia di tutte le transazioni
effettuate in app, sebbene possa non essere buono per
motivi di privacy.*/

create table transazioni (
	id_transazione int auto_increment primary key,
    data_transazione date,
	ref_cod_fis char(16) references clienti(codice_fiscale),
    ref_id_macchina smallint references macchine(id),
    ref_id_prodotto smallint references prodotti(id),
    importo decimal(3,2)
);

/*POPOLAZIONE DB*/

/*Macchine*/
insert into utenti values (1, 'macchina'),
(2, 'macchina'),
(3, 'macchina'),
(4, 'macchina'),
(5, 'macchina'),
(6, 'macchina'),
(7, 'macchina'),
(8, 'macchina');

/*Per comodità le password sono i digest SHA-256 di macchina001, macchina002, ecc.*/
insert into credenziali values (1, 'macchina001@svm', '59a64306ddb6eac861c2fb9da0dee92bb90e971eecc67fdb985f6bcf0fb74f95'),
(2, 'macchina002@svm', 'fe04235e872bd2ae25b326a281b11a9e5fca8bb90e501f7e6664c395d2427010'),
(3, 'macchina003@svm', 'd3a3bfec7f6a4c95a97d808d4003b89a1a5ee73a51a8cd2c018862dccbe1668a'),
(4, 'macchina004@svm', '0c2f81dada26391eeb2e6783bfb15411ea1efe6a350f9a546d768a2cfb40d845'),
(5, 'macchina005@svm', '6bfaea7f0543548127b38d5efa04ea4bdcb9c6c11cd433a8585a33bee31de139'),
(6, 'macchina006@svm', 'a256783652bc1028c2ef154ec283c492d679dd935c48a05732fc7ef98d856285'),
(7, 'macchina007@svm', '80c6678d7d02515000a73c70d399f17960fa2326b30bf98c459037f5237162c2'),
(8, 'macchina008@svm', '8cc13d1a1afabf476cbc4ab5a36626a6a24a68c543584b740876e5f1ca9ebfc5');

insert into macchine values (1, 'Via E. Basile, 64', true),
(2, 'Via E. Basile, 128', true),
(3, 'Via E. Basile, 192', true),
(4, 'Via E. Basile, 256', true),
(5, 'Corso Tukory, 16', true),
(6, 'Corso Tukory, 32', true),
(7, 'Corso Tukory, 56', true),
(8, 'Corso Tukory, 65', true);

/*Prodotti*/
insert into prodotti values 
(1, 'Caffè LQ', 0.4),
(2, 'Caffè HQ', 0.6),
(3, 'Caffè macchiato', 0.8),
(4, 'Cappuccino', 1.2),
(5, 'Latte caldo', 0.8),
(6, 'Acqua (Bicchiere)', 0.1);

/* Clienti */
insert into utenti values
(9, 'cliente'),
(10, 'cliente'),
(11, 'cliente'),
(12, 'cliente');

/*Per comodità le password sono i digest SHA-256 di nome.cognome*/
insert into credenziali values
(9, 'mario.rossi@gmail.com', '1729ec9149d3d2af04ee1838ce459779c1aa95cc73056d21dc25595b6ef97fa0'),
(10, 'giuseppe.verdi@libero.it', 'd05e8b3f637dc14091138f088a2eff9220bacae6d07f4856d632312eec3a4a6d'),
(11, 'giorgio.bianchi@live.com', 'c8b071077c7730de76a1da64e5df83d7422f213e38bdfbc611ba05fb1dde5123'),
(12, 'carlo.bianchi@mysql.com', '29041e8a047666ea32fadc9bf120f37cbde581c9e911d3cba7cfab7cb0a74c1d');

insert into clienti values
(9, 'MARIO', 'ROSSI', '1993-06-02', 'RSSMRA93H02F205X'),
(10, 'GIUSEPPE', 'VERDI', '1956-10-14', 'VRDGPP56R14L219D'),
(11, 'GIORGIO', 'BIANCHI', '2001-11-11', 'BNCGRG01S11G273A'),
(12, 'CARLO', 'BIANCHI', '1972-10-14', 'BNCCRL72R14G273X');

/*Connessioni Default*/
insert into connessioni values
(1, ''),
(2, ''),
(3, ''),
(4, ''),
(5, ''),
(6, ''),
(7, ''),
(8, '')
