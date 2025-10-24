package com.joboffers.domain.offer;

class OfferConfiguration {

    public static OfferFacade createOfferFacade(OfferRepository offerRepository, JobOfferFetchable jobOfferFetchable) {
        OfferAdder offerAdder = new OfferAdder(offerRepository);
        OffersRetriever offersRetriever = new OffersRetriever(offerRepository);
        OfferFetchable offerFetchable = new OfferFetchable(jobOfferFetchable, offerRepository);
        return new OfferFacade(offersRetriever, offerAdder, offerFetchable);

    }
}
