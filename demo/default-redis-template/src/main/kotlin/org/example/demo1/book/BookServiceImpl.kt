package org.example.demo1.book

import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BookServiceImpl(private val bookRepository: BookRepository): BookService {
    @Cacheable(value = [BookConst.CACHE_KEY_PREFIX], key = "#id")
    override fun findById(id: Long): BookEntity? {
        return bookRepository.findByIdOrNull(id)
    }

    override fun findByName(name: String): List<BookEntity> {
        return bookRepository.findByName(name)
    }

    override fun findAll(): List<BookEntity> {
        return bookRepository.findAll()
    }

    @CachePut(value = [BookConst.CACHE_KEY_PREFIX], key = "#result.id")
    override fun add(bookEntity: BookEntity): BookEntity {
        val name = bookEntity.name
        if (name.isNullOrEmpty()) {
            throw IllegalArgumentException("Book name is required")
        }
        bookEntity.id = null
        return bookRepository.save(bookEntity)
    }

    @CachePut(value = [BookConst.CACHE_KEY_PREFIX], key = "#result.id")
    override fun updateById(
        id: Long,
        bookEntity: BookEntity
    ): BookEntity {
        val book = findById(id) ?: throw IllegalArgumentException("Book not found")
        val name = bookEntity.name
        if (name.isNullOrEmpty()) {
            throw IllegalArgumentException("Book name is required")
        }

        book.id = id
        book.name = name
        book.author = bookEntity.author
        return bookRepository.save(book)
    }

    @CacheEvict(value = [BookConst.CACHE_KEY_PREFIX], key = "#id")
    override fun delete(id: Long) {
        if (!bookRepository.existsById(id)) {
            throw IllegalArgumentException("Book not found")
        }
        bookRepository.deleteById(id)
    }

}
