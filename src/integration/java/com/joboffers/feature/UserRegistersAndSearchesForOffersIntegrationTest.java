package com.joboffers.feature;

import com.joboffers.BaseIntegrationTest;

class UserRegistersAndSearchesForOffersIntegrationTest extends BaseIntegrationTest {
//         step 1: na zewnętrznym serwerze HTTP http://server/offers nie ma żadnych ofert (26.10.2025 09:00)
//         step 2: harmonogram uruchamia się po raz pierwszy o 12:00 i wysyła żądanie GET do zewnętrznego serwera
//         step 3: system dodaje 0 ofert do bazy danych
//         step 4: użytkownik próbuje uzyskać token JWT, wysyłając POST /token z mail=maksim@mail.con i password=12345 o 12:10
//         step 5: system zwraca UNAUTHORIZED (401)
//         step 6: użytkownik wysyła GET /offers bez tokena JWT o 12:15
//         step 7: system zwraca UNAUTHORIZED (401)
//         step 8: użytkownik wysyła POST /register z mail=maksim@mail.con i password=12345 o 12:20
//         step 9: system rejestruje użytkownika i zwraca OK (200)
//         step 10: użytkownik ponownie próbuje uzyskać token JWT, wysyłając POST /token z mail=maksim@mail.con i password=12345 o 12:25
//         step 11: system zwraca OK (200) oraz jwtToken="AAAA.BBBB.CCC"
//         step 12: użytkownik wysyła GET /offers z nagłówkiem Authorization: Bearer AAAA.BBBB.CCC o 12:30
//         step 13: system zwraca OK (200) z 0 ofert
//         step 14: na zewnętrznym serwerze pojawiły się 3 nowe oferty (26.10.2025 14:00)
//         step 15: harmonogram uruchamia się po raz drugi o 15:00 i wysyła żądanie GET do zewnętrznego serwera
//         step 16: system dodaje 3 nowe oferty o identyfikatorach 100, 200 i 300 do bazy danych
//         step 17: użytkownik wysyła GET /offers z nagłówkiem Authorization: Bearer AAAA.BBBB.CCC o 15:05
//         step 18: system zwraca OK (200) z 3 ofertami o identyfikatorach 1, 2 i 3
//         step 19: użytkownik wysyła GET /offers/1 o 15:10
//         step 20: system zwraca OK (200) z ofertą id=1
//         step 21: użytkownik wysyła GET /offers/100 o 15:15
//         step 22: system zwraca NOT_FOUND (404) z komunikatem "Oferta o id 100 nie została znaleziona"
//         step 23: na zewnętrznym serwerze pojawiły się kolejne 2 nowe oferty (26.10.2025 17:00)
//         step 24: harmonogram uruchamia się po raz trzeci o 18:00 i wysyła żądanie GET do zewnętrznego serwera
//         step 25: system dodaje 2 nowe oferty o identyfikatorach 4 i 5 do bazy danych
//         step 26: użytkownik wysyła GET /offers z nagłówkiem Authorization: Bearer AAAA.BBBB.CCC o 18:05
//         step 27: system zwraca OK (200) z 5 ofertami o identyfikatorach 1, 2, 3, 4 i 5

}
