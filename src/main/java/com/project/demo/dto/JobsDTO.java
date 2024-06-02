package com.project.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobsDTO {

    private int id;
    private String url;
    private String jobTitle;
    private String companyName;
    private String companyLogo;
    private String jobGeo;
    private String jobLevel;
    private String jobDescription;
    private String pubDate;


}
