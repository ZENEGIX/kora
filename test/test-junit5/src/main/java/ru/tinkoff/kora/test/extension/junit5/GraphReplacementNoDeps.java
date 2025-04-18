package ru.tinkoff.kora.test.extension.junit5;

import org.junit.jupiter.api.extension.ExtensionConfigurationException;
import ru.tinkoff.kora.application.graph.ApplicationGraphDraw;
import ru.tinkoff.kora.application.graph.Node;
import ru.tinkoff.kora.application.graph.Wrapped;

import java.lang.reflect.ParameterizedType;
import java.util.function.Function;

record GraphReplacementNoDeps<T>(Function<KoraAppGraph, ? extends T> function,
                                 GraphCandidate candidate) implements GraphModification {

    @Override
    public void accept(ApplicationGraphDraw graphDraw) {
        var nodesToReplace = GraphUtils.findNodeByTypeOrAssignable(graphDraw, candidate());
        if (nodesToReplace.isEmpty()) {
            throw new ExtensionConfigurationException("Can't find Nodes to Replace: " + candidate());
        }

        for (var nodeToReplace : nodesToReplace) {
            @SuppressWarnings("unchecked")
            var casted = (Node<Object>) nodeToReplace;
            graphDraw.replaceNode(casted, g -> {
                var replacement = function.apply(new DefaultKoraAppGraph(graphDraw, g));
                if (nodeToReplace.type() instanceof Class<?> tc && Wrapped.class.isAssignableFrom(tc)) {
                    return (Object) (Wrapped<?>) () -> replacement;
                } else if (nodeToReplace.type() instanceof ParameterizedType pt && Wrapped.class.isAssignableFrom(((Class<?>) pt.getRawType()))) {
                    return (Object) (Wrapped<?>) () -> replacement;
                } else {
                    return replacement;
                }
            });
        }
    }
}
