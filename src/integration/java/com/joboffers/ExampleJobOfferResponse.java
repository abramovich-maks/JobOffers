package com.joboffers;

public interface ExampleJobOfferResponse {

    default String bodyWithZeroOffersJson() {
        return "[]";
    }

    default String bodyWithTwoOffersJson() {
        return """
                [
                {
                "offerId": "690899a30f7c5b2be04fe499",
                "title": "Junior Java Developer NOWA",
                "company": "Netcompany Poland Sp. z o.o.",
                "salary": null,
                "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-developer-netcompany-poland-warsaw-3"
                },
                {
                "offerId": "690899a30f7c5b2be04fe49a",
                "title": "Praktykant Java Developer NOWA",
                "company": "Pentacomp Systemy Informatyczne S.A.",
                "salary": null,
                "offerUrl": "https://nofluffjobs.com/pl/job/praktykant-java-developer-pentacomp-systemy-informatyczne-warszawa"
                }
                ]
                """.trim();
    }

    default String bodyWithThreeOffersJson() {
        return """
                [
                {
                "offerId": "690899a30f7c5b2be04fe49b",
                "title": "Junior Java Backend Developer",
                "company": "VHV Informatyka Sp. z o.o.",
                "salary": null,
                "offerUrl": "https://nofluffjobs.com/pl/job/junior-java-backend-developer-vhv-informatyka-warszawa"
                },
                {
                "offerId": "690899a30f7c5b2be04fe49c",
                "title": "Software Developer",
                "company": "BrainworkZ",
                "salary": null,
                "offerUrl": "https://nofluffjobs.com/pl/job/software-developer-brainworkz-warsaw"
                },
                {
                "offerId": "690899a30f7c5b2be04fe49d",
                "title": "AI Engineer",
                "company": "Strategy",
                "salary": null,
                "offerUrl": "https://nofluffjobs.com/pl/job/ai-engineer-strategy-warsaw"
                }
                ]
                """.trim();
    }
}