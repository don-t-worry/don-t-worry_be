package kwangwoon.chambit.dontworry.global.common.request;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.core.util.Json;
import jakarta.annotation.PostConstruct;
import kwangwoon.chambit.dontworry.domain.portfolio.domain.Portfolio;
import kwangwoon.chambit.dontworry.global.common.dto.StockResponseDto;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Deprecated
public class StockPriceService {

    private String appKey;
    private String appSecret;
    private WebClient webClient;

    @Setter
    private String token;

    public StockPriceService(
            @Value("${kis.appkey}")String appKey,
            @Value("${kis.secret}") String secret
    ){
        this.appKey = appKey;
        this.appSecret = secret;
        webClient = WebClient.builder()
                .baseUrl("https://openapi.koreainvestment.com:9443")
//                //Request Header 로깅 필터
//                .filter(
//                        ExchangeFilterFunction.ofRequestProcessor(
//                                clientRequest -> {
//                                    log.info(">>>>>>>>> REQUEST <<<<<<<<<<");
//                                    log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
//                                    clientRequest.headers().forEach(
//                                            (name, values) -> values.forEach(value -> log.info("{} : {}", name, value))
//                                    );
//                                    return Mono.just(clientRequest);
//                                }
//                        )
//                )
//                //Response Header 로깅 필터
//                .filter(
//                        ExchangeFilterFunction.ofResponseProcessor(
//                                clientResponse -> {
//                                    log.info(">>>>>>>>>> RESPONSE <<<<<<<<<<");
//                                    clientResponse.headers().asHttpHeaders().forEach(
//                                            (name, values) -> values.forEach(value -> log.info("{} {}", name, value))
//                                    );
//                                    return Mono.just(clientResponse);
//                                }
//                        )
//                )
                .defaultHeader("content-type","application/json")
                .defaultHeader("charset","UTF-8")
                .build();
    }

    @PostConstruct
    public void init(){
        //스케줄러 해야함
        token = getToken();
    }

    public String getToken(){

        Map<String,String> body = new HashMap<>();
        body.put("grant_type","client_credentials");
        body.put("appkey",appKey);
        body.put("appsecret",appSecret);

        JsonNode response = webClient.post()
                .uri("/oauth2/tokenP")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        String responseToken = String.valueOf(response.get("access_token"));

        return "Bearer " +responseToken.substring(1,responseToken.length()-1);
    }

    public Flux<Long> getPresentStockPrice(List<Portfolio> chunk){
        return webClient
                .get()
                .uri(uriBuilder ->{
                    UriBuilder path = uriBuilder.path("/uapi/domestic-stock/v1/quotations/intstock-multprice");
                    int index = 1;
                    for(Portfolio p : chunk){
                        String stockCode = p.getStock().getStockCode();
                        path.queryParam("FID_COND_MRKT_DIV_CODE_"+index,"J");
                        path.queryParam("FID_INPUT_ISCD_"+index,stockCode);
                        index++;
                    }
                    return path.build();
                })
                .headers(x -> {
                    x.set("authorization",token);
                    x.set("appkey",appKey);
                    x.set("appsecret",appSecret);
                    x.set("tr_id","FHKST11300006");
                    x.set("custtype","P");
                })
                .retrieve()
                .bodyToMono(StockResponseDto.class)
                .map(StockResponseDto::getOutput).flatMapMany(Flux::fromIterable)
                .map(response -> {
                    String s = String.valueOf(response.get("inter2_prpr"));
                    return Long.parseLong(s.substring(1,s.length()-1));
                });
    }

}
