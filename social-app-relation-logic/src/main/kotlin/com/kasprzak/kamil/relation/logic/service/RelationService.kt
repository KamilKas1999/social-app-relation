package com.kasprzak.kamil.relation.logic.service

import com.kasprzak.kamil.relation.domain.entity.Relation

interface RelationService {
    fun createNewRelation(firstUserId : Long, secondUserId: Long) : Relation
}