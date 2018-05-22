package com.code.codesnippets.flux.client;

import com.alibaba.fastjson.JSONObject;
import com.code.codesnippets.flux.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


public class TestClient {

   private static final Logger log = LoggerFactory.getLogger(TestClient.class);

   public static void main(String[] args) {
       WebClient client = WebClient.create("http://localhost:8080/");

       Mono<User> result = client.post()// 请求方法,get,post...
               .uri("persion/getPersion/{id}.json", "123")// 请求相对地址以及参数
               .accept(MediaType.APPLICATION_JSON).retrieve()// 请求类型
               .bodyToMono(User.class);// 返回类型
       User user = result.block();
       log.info(JSONObject.toJSONString(user));

   }

}