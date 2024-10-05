package com.easypark.solutionsback.controller;

import com.easypark.solutionsback.service.VacancyStatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/statistic")
@RequiredArgsConstructor
public class VacancyStatisticController {

    private final VacancyStatisticService vacancyStatisticService;
}
