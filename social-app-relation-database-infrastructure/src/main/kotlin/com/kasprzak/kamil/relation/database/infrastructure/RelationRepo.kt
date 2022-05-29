package com.kasprzak.kamil.relation.database.infrastructure

import com.kasprzak.kamil.relation.domain.entity.Relation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RelationRepo : JpaRepository<Relation,Long>