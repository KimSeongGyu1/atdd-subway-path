package wooteco.subway.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wooteco.subway.admin.domain.Line;
import wooteco.subway.admin.dto.*;
import wooteco.subway.admin.service.LineService;

import java.net.URI;
import java.util.List;

@RestController
public class LineController {
    private LineService lineService;

    public LineController(LineService lineService) {
        this.lineService = lineService;
    }

    @PostMapping(value = "/lines")
    public ResponseEntity<LineResponse> createLine(@RequestBody LineRequest view) {
        Line persistLine = lineService.save(view.toLine());

        return ResponseEntity
                .created(URI.create("/lines/" + persistLine.getId()))
                .body(LineResponse.of(persistLine));
    }

    @GetMapping("/lines")
    public ResponseEntity<List<LineResponse>> findLines() {
        return ResponseEntity.ok().body(LineResponse.listOf(lineService.findLines()));
    }

    @GetMapping("lines/detail")
    public ResponseEntity<WholeSubwayResponse> retrieveLines() {
        return ResponseEntity.ok().body(lineService.findAllLinesWithStations());
    }

    @GetMapping("/lines/{id}")
    public ResponseEntity<LineDetailResponse> retrieveLine(@PathVariable Long id) {
        return ResponseEntity.ok().body(lineService.findLineWithStationsById(id));
    }

    @PutMapping("/lines/{id}")
    public ResponseEntity updateLine(@PathVariable Long id, @RequestBody LineRequest view) {
        lineService.updateLine(id, view);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/lines/{id}")
    public ResponseEntity deleteLine(@PathVariable Long id) {
        lineService.deleteLineById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/lines/{lineId}/stations")
    public ResponseEntity addLineStation(@PathVariable Long lineId, @RequestBody LineStationCreateRequest view) {
        lineService.addLineStation(lineId, view);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/lines/{lineId}/stations/{stationId}")
    public ResponseEntity removeLineStation(@PathVariable Long lineId, @PathVariable Long stationId) {
        lineService.removeLineStation(lineId, stationId);
        return ResponseEntity.noContent().build();
    }
}
