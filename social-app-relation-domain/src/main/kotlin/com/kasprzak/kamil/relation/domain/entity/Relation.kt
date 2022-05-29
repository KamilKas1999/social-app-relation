package com.kasprzak.kamil.relation.domain.entity

import lombok.Builder
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "relations")
class Relation(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = -1,
    var firstUserId: Long  = -1,
    var secondUserId: Long  = -1,
    var createdData: LocalDate = LocalDate.now()
)