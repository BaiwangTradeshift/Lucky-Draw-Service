package com.baiwangmaoyi.luckydraw.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Profile("remote")
public class RemoteRandomProvider implements RandomProvider {

    private RestTemplate restTemplate;

    private final String URL_TEMPL = "https://www.random" +
            ".org/integer-sets/?sets=1&num=%s&min=%s&max=%s&seqnos=off&commas=on&sort=on&order=index&format=plain&rnd" +
            "=new";

    @PostConstruct
    public void init(){
        this.restTemplate = new RestTemplate();
    }

    public <T> List<T> pickRandomly(List<T> sourceList, int itemsToSelect) {
        if (sourceList == null || sourceList.size() == 0) {
            throw new RuntimeException("Source list can't be empty");
        }
        int max = sourceList.size()-1;
        String formattedUrl = String.format(URL_TEMPL,itemsToSelect,0,max);
        String resultString = this.restTemplate.getForObject(formattedUrl, String.class).trim();
        String[] idx = resultString.split(",");
        return Arrays.stream(idx).mapToInt(p-> Integer.parseInt(p.trim())).mapToObj(sourceList::get).collect
                (Collectors.toList());
    }
}
