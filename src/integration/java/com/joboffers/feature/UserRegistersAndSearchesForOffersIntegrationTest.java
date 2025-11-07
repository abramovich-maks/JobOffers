package com.joboffers.feature;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.joboffers.BaseIntegrationTest;
import com.joboffers.ExampleJobOfferResponse;
import com.joboffers.domain.offer.dto.CreateOfferResponseDto;
import com.joboffers.domain.offer.dto.OfferResponseDto;
import com.joboffers.infrastructure.offer.scheduler.HttpOffersScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
class UserRegistersAndSearchesForOffersIntegrationTest extends BaseIntegrationTest implements ExampleJobOfferResponse {

    @Autowired
    private HttpOffersScheduler httpOffersScheduler;


    @Container
    public static final MongoDBContainer mongoDBContainerForLuckyPathTest = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainerForLuckyPathTest::getReplicaSetUrl);
        registry.add("job.offer.http.client.config.port", () -> wireMockServer.getPort());
        registry.add("job.offer.http.client.config.uri", () -> WIRE_MOCK_HOST);
    }

    @Test
    public void should_user_views_offers() throws Exception {
        // step 1: na zewnętrznym serwerze HTTP (http://ec2-3-127-218-34.eu-central-1.compute.amazonaws.com) nie ma żadnych ofert; harmonogram uruchamia się po raz pierwszy, wysyła żądanie GET do serwera i system dodaje 0 ofert do bazy danych
        // given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(bodyWithZeroOffersJson())));
        // when
        List<OfferResponseDto> newOffers = httpOffersScheduler.fetchAllOffersAndSaveIfNotExists();
        // then
        assertThat(newOffers).isEmpty();


//         step 2: użytkownik próbuje uzyskać token JWT, wysyłając POST /token z mail=maksim@mail.com i password=12345 and system returned UNAUTHORIZED(401)
        // given && when
        ResultActions failedLoginRequest = mockMvc.perform(post("/token").content(
                """
                        {
                        "login": "maksim@mail.com",
                        "password": "12345"
                        }
                        """.trim()).contentType(MediaType.APPLICATION_JSON));
        // then
        failedLoginRequest.andExpect(status().isUnauthorized())
                .andExpect(content().json("""
                        {
                          "message": "Bad credentials",
                          "status": "UNAUTHORIZED"
                        }
                        """.trim()));

//         step 3: użytkownik wysyła GET /offers bez tokena JWT ; system zwraca UNAUTHORIZED (401)
//         step 4: użytkownik wysyła POST /register z mail=maksim@mail.com i password=12345;  system rejestruje użytkownika i zwraca OK (200)
//         step 5: użytkownik ponownie próbuje uzyskać token JWT, wysyłając POST /token z mail=maksim@mail.com i password=12345;  system zwraca OK (200) oraz jwtToken="AAAA.BBBB.CCC"


        // step 6: użytkownik wysyła GET /offers z nagłówkiem “Authorization: Bearer AAAA.BBBB.CCC”; system zwraca OK (200) z 0 ofert
        //given && when
        ResultActions perform = mockMvc.perform(get("/offers")
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult mvcResult = perform.andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        List<OfferResponseDto> offers = objectMapper.readValue(json, new TypeReference<>() {
        });
        // then
        assertThat(offers).isEmpty();


        // step 7: na zewnętrznym serwerze pojawiają się 3 nowe oferty; harmonogram uruchamia się po raz drugi, wysyła żądanie GET do serwera i system dodaje 3 nowe oferty o identyfikatorach 100, 200 i 300 do bazy danych
        // given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(bodyWithThreeOffersJson())));
        // when
        List<OfferResponseDto> newOffers_2 = httpOffersScheduler.fetchAllOffersAndSaveIfNotExists();
        // then
        assertThat(newOffers_2).hasSize(3);


        // step 8: użytkownik wysyła GET /offers z nagłówkiem “Authorization: Bearer AAAA.BBBB.CCC”; system zwraca OK (200) z 3 ofertami o identyfikatorach 1, 2 i 3
        //given && when
        ResultActions performWithThreeOffers = mockMvc.perform(get("/offers"));
        MvcResult performResult = performWithThreeOffers.andExpect(status().isOk()).andReturn();
        String jsonWithThreeOffers = performResult.getResponse().getContentAsString();
        OfferResponseDto[] offerResponseWithThreeOffers = objectMapper.readValue(jsonWithThreeOffers, OfferResponseDto[].class);
        List<OfferResponseDto> threeOffers = List.of(offerResponseWithThreeOffers);
        // then
        assertThat(threeOffers).hasSize(3);
        OfferResponseDto firstOffer = threeOffers.get(0);
        OfferResponseDto secondOffer = threeOffers.get(1);
        OfferResponseDto thirdOffer = threeOffers.get(2);
        assertThat(threeOffers).containsExactlyInAnyOrder(
                new OfferResponseDto(firstOffer.offerId(), firstOffer.title(), firstOffer.company(), firstOffer.salary(), firstOffer.offerUrl()),
                new OfferResponseDto(secondOffer.offerId(), secondOffer.title(), secondOffer.company(), secondOffer.salary(), secondOffer.offerUrl()),
                new OfferResponseDto(thirdOffer.offerId(), thirdOffer.title(), thirdOffer.company(), thirdOffer.salary(), thirdOffer.offerUrl())
        );


        // step 9: użytkownik wysyła GET /offers/1; system zwraca OK (200) z ofertą o id=1
        // given
        String offerId = firstOffer.offerId();
        // when
        ResultActions performGetOfferWithExistId = mockMvc.perform(get("/offers/" + offerId));
        // then
        performGetOfferWithExistId.andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                        "title": "Junior Java Backend Developer",
                        "company": "VHV Informatyka Sp. z o.o.",
                        "salary": null,
                        "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-backend-developer-vhv-informatyka-warszawa"
                        }
                        """.trim()));


        // step 10: użytkownik wysyła GET /offers/100 o 15:15; system zwraca NOT_FOUND (404) z komunikatem "Offer with id 100 not found"
        // given
        // when
        ResultActions performGetOfferWithNotExistId = mockMvc.perform(get("/offers/100"));
        // then
        performGetOfferWithNotExistId.andExpect(status().isNotFound())
                .andExpect(content().json("""
                        {
                        "message" : "Offer with id 100 not found",
                        "status": "NOT_FOUND"
                        }
                        """.trim()));


        // step 11: na zewnętrznym serwerze pojawiają się kolejne 2 nowe oferty; harmonogram uruchamia się po raz trzeci, wysyła żądanie GET do serwera i system dodaje 2 nowe oferty o identyfikatorach 4 i 5 do bazy danych
        // given
        wireMockServer.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(bodyWithTwoOffersJson())));
        // when
        List<OfferResponseDto> nextTwoNewOffers = httpOffersScheduler.fetchAllOffersAndSaveIfNotExists();
        // then
        assertThat(nextTwoNewOffers).hasSize(2);


        // step 12: użytkownik wysyła GET /offers z nagłówkiem “Authorization: Bearer AAAA.BBBB.CCC”; system zwraca OK (200) z 5 ofertami o identyfikatorach 1, 2, 3, 4 i 5
        //given && when
        ResultActions performWithFiveOffers = mockMvc.perform(get("/offers"));
        MvcResult performResultWithFive = performWithFiveOffers.andExpect(status().isOk()).andReturn();
        String jsonWithFiveOffers = performResultWithFive.getResponse().getContentAsString();
        OfferResponseDto[] offerResponseWithFiveOffers = objectMapper.readValue(jsonWithFiveOffers, OfferResponseDto[].class);
        List<OfferResponseDto> fiveOffers = List.of(offerResponseWithFiveOffers);
        // then
        assertThat(fiveOffers).hasSize(5);
        OfferResponseDto fourthOffer = nextTwoNewOffers.get(0);
        OfferResponseDto fivesOffer = nextTwoNewOffers.get(1);
        assertThat(fiveOffers).contains(
                new OfferResponseDto(fourthOffer.offerId(), fourthOffer.title(), fourthOffer.company(), fourthOffer.salary(), fourthOffer.offerUrl()),
                new OfferResponseDto(fivesOffer.offerId(), fivesOffer.title(), fivesOffer.company(), fivesOffer.salary(), fivesOffer.offerUrl())
        );

        // step 13: użytkownik wysyła POST /offers z nagłówkiem “Authorization: Bearer AAAA.BBBB.CCC” oraz danymi oferty w body; system zwraca CREATED (201) i zapisuje nową ofertę
        // given
        // when
        ResultActions performPostOffer = mockMvc.perform(post("/offers").content(
                """
                        {
                        "title": "Junior Java Developer",
                        "company": "Amelco Limited",
                        "salary": "450-600 PLN/day on B2B",
                        "offerUrl": "https://www.linkedin.com/jobs/view/4310359141"
                        }
                        """.trim()).contentType(MediaType.APPLICATION_JSON));
        // then
        MvcResult createdOfferResult = performPostOffer.andExpect(status().isCreated()).andReturn();
        String createdOfferJson = createdOfferResult.getResponse().getContentAsString();
        CreateOfferResponseDto createOfferRequestDto = objectMapper.readValue(createdOfferJson, CreateOfferResponseDto.class);
        assertAll(
                () -> assertThat(createOfferRequestDto.message()).isEqualTo("Offer created."),
                () -> assertThat(createOfferRequestDto.offer().offerId()).isNotNull(),
                () -> assertThat(createOfferRequestDto.offer().title()).isEqualTo("Junior Java Developer"),
                () -> assertThat(createOfferRequestDto.offer().company()).isEqualTo("Amelco Limited"),
                () -> assertThat(createOfferRequestDto.offer().salary()).isEqualTo("450-600 PLN/day on B2B"),
                () -> assertThat(createOfferRequestDto.offer().offerUrl()).isEqualTo("https://www.linkedin.com/jobs/view/4310359141")
        );


        // step 14: użytkownik wysyła GET /offers z nagłówkiem “Authorization: Bearer AAAA.BBBB.CCC”; system zwraca OK (200) z 6 ofertami, w tym nowo utworzoną
        ResultActions finalPerform = mockMvc.perform(get("/offers"));
        String offersJson = finalPerform.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        OfferResponseDto[] finalResponse = objectMapper.readValue(offersJson, OfferResponseDto[].class);
        List<OfferResponseDto> finalListOffers = List.of(finalResponse);
        // then
        assertThat(finalListOffers).hasSize(6);
        finalPerform.andExpect(status().isOk()).andExpect(content().json("""
                [
                {
                       "title": "Junior Java Backend Developer",
                       "company": "VHV Informatyka Sp. z o.o.",
                       "salary": null,
                       "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-backend-developer-vhv-informatyka-warszawa"
                       },
                       {
                       "title": "Software Developer",
                       "company": "BrainworkZ",
                       "salary": null,
                       "offerUrl": "https://nofluffjobs.com/pl/job/software-developer-brainworkz-warsaw"
                       },
                       {
                       "title": "AI Engineer",
                       "company": "Strategy",
                       "salary": null,
                       "offerUrl": "https://nofluffjobs.com/pl/job/ai-engineer-strategy-warsaw"
                       },
                       {
                        "title": "Junior Java Developer NOWA",
                        "company": "Netcompany Poland Sp. z o.o.",
                        "salary": null,
                        "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-developer-netcompany-poland-warsaw-3"
                        },
                        {
                        "title": "Praktykant Java Developer NOWA",
                        "company": "Pentacomp Systemy Informatyczne S.A.",
                        "salary": null,
                        "offerUrl": "https://nofluffjobs.com/pl/job/praktykant-java-developer-pentacomp-systemy-informatyczne-warszawa"
                        },
                        {
                        "title": "Junior Java Developer",
                        "company": "Amelco Limited",
                        "salary": "450-600 PLN/day on B2B",
                        "offerUrl": "https://www.linkedin.com/jobs/view/4310359141"
                       }
                       ]
                """));
    }
}
