package com.project.demo.api.board.presentation;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Board API", description = "게시판 API")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardRestController {

}
