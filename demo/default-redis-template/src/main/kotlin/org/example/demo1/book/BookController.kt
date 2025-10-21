package org.example.demo1.book

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/books")
class BookController(private val bookService: BookService) {
    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): BookEntity? {
        return bookService.findById(id)
    }

    @GetMapping("/name/{name}")
    fun findByName(@PathVariable name: String): List<BookEntity> {
        return bookService.findByName(name)
    }

    @GetMapping
    fun findAll(): List<BookEntity> {
        return bookService.findAll()
    }

    @PostMapping
    fun add(@RequestBody bookEntity: BookEntity): BookEntity {
        return bookService.add(bookEntity)
    }

    @PutMapping("/{id}")
    fun updateById(@PathVariable id: Long, @RequestBody bookEntity: BookEntity): BookEntity {
        return bookService.updateById(id, bookEntity)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        return bookService.delete(id)
    }

}
