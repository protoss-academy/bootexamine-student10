package com.protosstechnology.train.bootexamine.document;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/document")

public class DocumentController {

    private final DocumentRepository repository;

    @Operation(summary = "Get Document by id")
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable Long id) {
        Optional<Document> document = repository.findById(id);
        if (document.isPresent()) return ResponseEntity.ok(document.get());

        return ResponseEntity.badRequest().body(new Error(400, "Not Found"));
    }

    @PostMapping
    public ResponseEntity save(@RequestBody Document document) {
        Document result = repository.save(document);
        if (result == null || result.getId() == null)
            return ResponseEntity.badRequest().body(new Error(400, "Bad Request"));

        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody Document document, @PathVariable Long id) {
        Optional<Document> resultOpt = repository.findById(id);
        if (!resultOpt.isPresent())
            return ResponseEntity.badRequest().body(new Error(400, "Bad Request"));

        Document result = resultOpt.get();
        result.setDocumentNumber(document.getDocumentNumber());
        result.setDescription(document.getDescription());
        repository.save(result);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        Optional resultOpt = repository.findById(id);
        if (!resultOpt.isPresent())
            return ResponseEntity.badRequest().body(new Error(400, "Bad Request"));

        repository.deleteById(id);
        Optional deletedOpt = repository.findById(id);
        if (deletedOpt.isPresent())
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Error(500, "System Error"));

        return ResponseEntity.ok(new Error(200, "Deleted"));
    }

}
