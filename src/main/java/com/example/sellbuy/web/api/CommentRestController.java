package com.example.sellbuy.web.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentRestController {

//    @GetMapping("/{routeId}/comments")
//    public ResponseEntity<List<CommentDisplayView>> getComments(@PathVariable("routeId") Long routeId) {
//        return ResponseEntity.ok(commentService.getAllCommentsForRoute(routeId));
//    }
}
