package kwangwoon.chambit.dontworry.global.config;

public enum DomainConfig {
    LocalHttp("http://localhost:3000"),
    LocalHttps("https://localhost:3000"),
    FrontServer("https://www.don-t-worry.com");

    private final String address;

    DomainConfig(String address){
        this.address = address;
    }

    public String getPresentAddress(){
        return LocalHttp.address;
    }
}
