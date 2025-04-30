package com.example.toy.users.entity

import jakarta.persistence.*

@Entity
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
//    @Id
//    val id: String = java.util.UUID.randomUUID().toString(),

    val name: String,

    @Column(unique = true)
    val email: String,

    val password: String,
){
    // Hibernate를 위한 기본 생성자
    constructor() : this(0, "", "", "")
}

//fun User.toModel(): UserModel {
//    return UserModel(
//        id = this.id,
//        name = this.name,
//        email = this.email,
//        password = this.password
//    )
//}