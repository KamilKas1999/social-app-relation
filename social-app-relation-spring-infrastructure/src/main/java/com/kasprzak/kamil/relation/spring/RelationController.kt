package com.kasprzak.kamil.relation.spring

import com.kasprzak.kamil.relation.domain.request.CreateRelationRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestHeader
import com.kasprzak.kamil.relation.logic.service.RelationService
import org.springframework.http.HttpHeaders

@RestController
@RequestMapping("/api")
class RelationController(val relationService: RelationService) {

    @PostMapping("/relation/create")
    fun createRelation(@RequestBody requestBody: CreateRelationRequest,
                       @RequestHeader(HttpHeaders.AUTHORIZATION) token: String
    ){
        relationService.createNewRelation(requestBody.firstUserId,requestBody.secondUserId)
    }
}