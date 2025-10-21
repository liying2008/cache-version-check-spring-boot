package cc.duduhuo.cacheversioncheck.demo1.user

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {
    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): UserEntity? {
        return userService.findById(id)
    }

    @GetMapping("/name/{name}")
    fun findByName(@PathVariable name: String): UserEntity? {
        return userService.findByName(name)
    }

    @GetMapping
    fun findAll(): List<UserEntity> {
        return userService.findAll()
    }

    @PostMapping
    fun add(@RequestBody userEntity: UserEntity): UserEntity {
        return userService.add(userEntity)
    }

    @PutMapping("/{id}")
    fun updateById(@PathVariable id: Long, @RequestBody userEntity: UserEntity): UserEntity {
        return userService.updateById(id, userEntity)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        return userService.delete(id)
    }

}
