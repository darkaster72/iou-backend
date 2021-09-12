package com.example.iou.records

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
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

    @GetMapping
    fun getEntries(): Flux<EntryResponse> {
        return entryService.getEntries()
    }

    @GetMapping("/to/{destinationId}")
    fun getEntriesFor(@PathVariable destinationId: String): Flux<EntryResponse> {
        return entryService.getEntriesForDestination(destinationId)
    }
}