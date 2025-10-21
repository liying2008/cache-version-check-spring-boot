package cc.duduhuo.cacheversioncheck.demo2.role

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/roles")
class RoleController(private val roleService: RoleService) {
    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): RoleEntity? {
        return roleService.findById(id)
    }

    @GetMapping("/name/{name}")
    fun findByName(@PathVariable name: String): RoleEntity? {
        return roleService.findByName(name)
    }

    @GetMapping
    fun findAll(): List<RoleEntity> {
        return roleService.findAll()
    }

    @PostMapping
    fun add(@RequestBody roleEntity: RoleEntity): RoleEntity {
        return roleService.add(roleEntity)
    }

    @PutMapping("/{id}")
    fun updateById(@PathVariable id: Long, @RequestBody roleEntity: RoleEntity): RoleEntity {
        return roleService.updateById(id, roleEntity)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        return roleService.delete(id)
    }

}
