package com.example.toy.users

import com.example.toy.users.dto.CreateUserDto
import com.example.toy.users.dto.UpdateUserDto
import com.example.toy.users.entity.User
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val usersService: UsersService
) {

    @PostMapping
    fun create(@RequestBody dto: CreateUserDto): Map<String, Any> {
        val saved = usersService.create(dto)
        return mapOf("id" to saved.id, "message" to "User created")
    }

    @GetMapping
    fun findAll(): List<User> = usersService.findAll()

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: Long): ResponseEntity<Any> {
        val user = usersService.findOne(id)
        return if (user != null) ResponseEntity.ok(user)
        else ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to "User not found"))
    }

    @PatchMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody dto: UpdateUserDto): ResponseEntity<Any> {
        val updated = usersService.update(id, dto)
        return if (updated != null) ResponseEntity.ok(updated)
        else ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to "User not found"))
    }

    @DeleteMapping("/{id}")
    fun remove(@PathVariable id: Long): ResponseEntity<Any> {
        return if (usersService.delete(id))
            ResponseEntity.ok(mapOf("message" to "User $id deleted"))
        else
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to "User not found"))
    }

    @PatchMapping("/{id}/password")
    fun changePassword(@PathVariable id: Long, @RequestBody body: Map<String, String>): ResponseEntity<Any> {
        val pw = body["password"]
        return if (pw != null && usersService.changePassword(id, pw))
            ResponseEntity.ok(mapOf("message" to "Password changed for user $id"))
        else
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to "User not found or missing password"))
    }
}
