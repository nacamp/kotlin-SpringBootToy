package com.example.toy.users



import com.example.toy.users.dto.CreateUserDto
import com.example.toy.users.dto.UpdateUserDto
import org.springframework.web.bind.annotation.*
import java.util.concurrent.atomic.AtomicLong

@RestController
@RequestMapping("/users")
class UserController {

    private val mockDb = mutableMapOf<Long, Map<String, String>>()
    private val counter = AtomicLong()

    @PostMapping
    fun create(@RequestBody dto: CreateUserDto): Map<String, Any> {
        val id = counter.incrementAndGet()
        mockDb[id] = mapOf("name" to dto.name, "email" to dto.email)
        return mapOf("id" to id, "message" to "User created")
    }

    @GetMapping
    fun findAll(): List<Map<String, String>> {
        return mockDb.values.toList()
    }

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: Long): Map<String, Any> {
        val user = mockDb[id]
        return if (user != null) mapOf("id" to id, "user" to user)
        else mapOf("error" to "User not found")
    }

    @PatchMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody dto: UpdateUserDto
    ): Map<String, Any> {
        val existing = mockDb[id] ?: return mapOf("error" to "User not found")
        val updated = mapOf(
            "name" to (dto.name ?: existing["name"]!!),
            "email" to (dto.email ?: existing["email"]!!)
        )
        mockDb[id] = updated
        return mapOf("id" to id, "updated" to updated)
    }

    @DeleteMapping("/{id}")
    fun remove(@PathVariable id: Long): Map<String, Any> {
        return if (mockDb.remove(id) != null)
            mapOf("message" to "User $id deleted")
        else
            mapOf("error" to "User not found")
    }

    @PatchMapping("/{id}/password")
    fun changePassword(@PathVariable id: Long, @RequestBody body: Map<String, String>): Map<String, Any> {
        val pw = body["password"]
        return if (mockDb.containsKey(id) && pw != null)
            mapOf("message" to "Password changed for user $id")
        else
            mapOf("error" to "User not found or missing password")
    }
}
