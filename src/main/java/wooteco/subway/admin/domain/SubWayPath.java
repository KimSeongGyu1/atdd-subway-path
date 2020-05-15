package wooteco.subway.admin.domain;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import wooteco.subway.admin.exception.NotFoundPathException;

import java.util.List;
import java.util.Objects;

public class SubWayPath {
    private final GraphPath<Station, LineStationEdge> path;
    private final PathType pathType;
    private final Station source;
    private final Station target;

    public SubWayPath(DijkstraShortestPath<Station, LineStationEdge> path, PathType pathType, Station source, Station target) {
        this.source = source;
        this.target = target;
        this.path = validate(path.getPath(source, target));
        this.pathType = pathType;
    }

    private GraphPath<Station, LineStationEdge> validate(GraphPath<Station, LineStationEdge> graphPath) {
        if (Objects.isNull(graphPath)) {
            throw new NotFoundPathException(source.getName(), target.getName());
        }

        return graphPath;
    }

    public List<Station> stations() {
        return path.getVertexList();
    }

    public int distance() {
        return pathType.calculateDistance(path);
    }

    public int duration() {
        return pathType.calculateDuration(path);
    }
}
