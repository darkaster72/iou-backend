package com.example.iou.records

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("api/v1/entries")
class EntryController(
    val entryService: EntryService,
) {

    @PostMapping
    fun addEntry(@RequestBody request: Mono<EntryRequest>): Mono<ResponseEntity<Unit>> {
        return request.flatMap { entryService.addEntry(it) }
            .map { ResponseEntity.ok().build() }
    }
}