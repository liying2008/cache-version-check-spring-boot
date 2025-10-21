package org.example.demo1.book

interface BookService {
    fun findById(id: Long): BookEntity?

    fun findByName(name: String): List<BookEntity>

    fun findAll(): List<BookEntity>

    fun add(bookEntity: BookEntity): BookEntity

    fun updateById(id: Long, bookEntity: BookEntity): BookEntity

    fun delete(id: Long)
}
