package com.KHH.novelsite.novel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CoverController {
    @GetMapping("/cover/list")
    public List<String> getCoverList() {
        // 실제 서버 내 경로로 바꿔줘야 함
        String folderPath = "src/main/resources/static/img/pyoji";
        File folder = new File(folderPath);

        // 파일명이 jpg, png만 리턴 (원하면 확장자 더 추가 가능)
        return Arrays.stream(folder.listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .filter(name -> name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".gif"))
                .collect(Collectors.toList());
    }
}
