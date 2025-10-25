package com.joboffers.domain.loginandregister;

class LoginAndRegisterConfiguration {

    public static LoginAndRegisterFacade createdLoginAndRegisterFacade(UserRepository userRepository) {
        UserRetriever userRetriever = new UserRetriever(userRepository);
        UserAdder userAdder = new UserAdder(userRepository);
        return new LoginAndRegisterFacade(userRetriever, userAdder);
    }
}
