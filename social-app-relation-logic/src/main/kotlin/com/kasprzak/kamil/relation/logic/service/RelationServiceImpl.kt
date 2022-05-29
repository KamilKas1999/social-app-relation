package com.kasprzak.kamil.relation.logic.service

import com.kasprzak.kamil.relation.database.infrastructure.RelationRepo
import com.kasprzak.kamil.relation.domain.entity.Relation
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class RelationServiceImpl(val repository: RelationRepo) : RelationService {
    override fun createNewRelation(firstUserId: Long, secondUserId: Long) : Relation {
        val newRelation = Relation(-1L,firstUserId,secondUserId, LocalDate.now())
        return this.repository.save(newRelation)
    }
}