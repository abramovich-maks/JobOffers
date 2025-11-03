package com.joboffers.domain.offer;

import com.joboffers.domain.offer.dto.CreateOfferRequestDto;
import com.joboffers.domain.offer.dto.CreateOfferResponseDto;
import com.joboffers.domain.offer.dto.GetOfferResponseDto;
import com.joboffers.domain.offer.dto.OfferResponseDto;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class OffersFacadeTest {

    private final InMemoryOfferFacadeTestImp offersRepository = new InMemoryOfferFacadeTestImp();
    private final InMemoryFetcherTestImpl fetcherTest = new InMemoryFetcherTestImpl(
            List.of(
                    new GetOfferResponseDto("Junior Java", "Company", "1200", "url//...1"),
                    new GetOfferResponseDto("Java Developer", "Nowe", "7000-8000", "url//...2"),
                    new GetOfferResponseDto("Senior Java", "Visa", "6000", "url//...3"),
                    new GetOfferResponseDto("Software developer", "C423", "8000", "url//...4"),
                    new GetOfferResponseDto("Java", "inPost", "5000", "url//...5"),
                    new GetOfferResponseDto("Mid", "Allegro", "3000", "url//...6")
            ));

    OfferFacade offerFacade = new OfferConfiguration().offerFacade(offersRepository, fetcherTest);

    @Test
    public void should_fetch_from_jobs_from_remote_and_save_all_offers_when_repository_is_empty() {
        // given
        assertThat(offerFacade.findAllOffers()).isEmpty();
        // when
        List<OfferResponseDto> result = offerFacade.fetchAllOffersAndSaveAllIfNotExists();
        // then
        assertThat(result).hasSize(6);
    }

    @Test
    public void should_save_only_2_offers_when_repository_had_4_added_with_offer_urls() {
        // given
        offerFacade.saveOffer(new CreateOfferRequestDto("Junior Java", "Company", "1200", "url//...1"));
        offerFacade.saveOffer(new CreateOfferRequestDto("Java Developer", "Nowe", "7000-8000", "url//...2"));
        offerFacade.saveOffer(new CreateOfferRequestDto("Senior Java", "Visa", "6000", "url//...3"));
        offerFacade.saveOffer(new CreateOfferRequestDto("Software developer", "C423", "8000", "url//...4"));
        assertThat(offerFacade.findAllOffers()).hasSize(4);
        // when
        List<OfferResponseDto> response = offerFacade.fetchAllOffersAndSaveAllIfNotExists();
        // then
        assertThat(response).hasSize(2);
        assertThat(offerFacade.findAllOffers()).hasSize(6);
        assertThat(List.of(
                response.get(0).offerUrl(),
                response.get(1).offerUrl())
        ).containsExactlyInAnyOrder("url//...5", "url//...6");
    }

    @Test
    public void should_save_4_offers_when_there_are_no_offers_in_database() {
        // given
        // when
        offerFacade.saveOffer(new CreateOfferRequestDto("Junior Java", "Company", "1200", "url//...1"));
        offerFacade.saveOffer(new CreateOfferRequestDto("Java Developer", "Nowe", "7000-8000", "url//...2"));
        offerFacade.saveOffer(new CreateOfferRequestDto("Senior Java", "Visa", "6000", "url//...3"));
        offerFacade.saveOffer(new CreateOfferRequestDto("Software developer", "C423", "8000", "url//...4"));
        // then
        assertThat(offerFacade.findAllOffers()).hasSize(4);
    }

    @Test
    public void should_find_offer_by_id_when_offer_was_saved() {
        // given
        CreateOfferResponseDto createOfferResponseDto = offerFacade.saveOffer(new CreateOfferRequestDto("Junior Java", "Company", "1200", "url//...1"));
        // when
        OfferResponseDto offerById = offerFacade.findOfferById(createOfferResponseDto.offer().offerId());
        // then
        assertThat(offerById).isEqualTo(OfferResponseDto
                .builder()
                .offerId(createOfferResponseDto.offer().offerId())
                .title("Junior Java")
                .company("Company")
                .salary("1200")
                .offerUrl("url//...1")
                .build()
        );
    }

    @Test
    public void should_throw_not_found_exception_when_offer_not_found() {
        // given
        assertThat(offerFacade.findAllOffers()).isEmpty();

        // when
        Throwable thrown = catchThrowable(() -> offerFacade.findOfferById("100"));
        // then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(OfferNotFoundException.class)
                .hasMessage("Offer with id 100 not found");
    }

    @Test
    public void should_throw_duplicate_key_exception_when_save_2_offers_with_duplicate_url() {
        // given
        CreateOfferRequestDto firstOfferRequest = new CreateOfferRequestDto("Junior Java", "Company", "1200", "url//...1");
        CreateOfferRequestDto secondOfferRequest = new CreateOfferRequestDto("Junior Java", "Company", "1200", "url//...1");
        offerFacade.saveOffer(firstOfferRequest);
        // when
        Throwable thrown = catchThrowable(() -> offerFacade.saveOffer(secondOfferRequest));
        // then
        assertThat(thrown)
                .isInstanceOf(DuplicateKeyException.class)
                .hasMessage("Offer with offerUrl [url//...1] already exists");
    }
}