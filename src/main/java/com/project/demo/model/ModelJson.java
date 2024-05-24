package com.project.demo.model;

import com.project.demo.dto.JobsDTO;
import lombok.Data;

import java.awt.*;
import java.util.List;

@Data

public class ModelJson {

    private List<JobsDTO> jobs;
}
