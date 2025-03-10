# Bnode

## Attribut
- `comment` : A string that contains the comment of the node.
- `refs` : A list of references to other nodes.
- `graph` : A reference to the graph that contains the node.
- `id` : An integer that contains the id of the node.

## Method
- `ins()` : Returns the list of incoming references.
- `forEachOutNodeField(Consumer<Field> consumer)`: Iterates over fields of the node that are of type BNode.
- `forEachOut(BiConsumer<String, BNode> consumer)`: Iterates over outgoing nodes and applies the given consumer.
- `forEachIn(BiConsumer<String, BNode> consumer)`: Iterates over incoming nodes and applies the given consumer.
- `isLeaf()`: Checks if the node is a leaf (no outgoing edges).
- `bfs(Consumer<BNode> consumer)`: Performs a breadth-first search starting from this node.
- `dfs(Consumer<BNode> consumer)`: Performs a depth-first search starting from this node.
- `search(Consumer<BNode> consumer, Function<List<BNode>, BNode> producer)`: Helper method for BFS and DFS.
- `bfs2list()`: Returns a list of nodes in BFS order.
- `outs()`: Returns a map of outgoing nodes.
- `outDegree()`: Returns the number of outgoing edges.
- `search(String query)`: Searches for nodes matching the query.
- `distanceToSearchString(String s)`: Computes the distance to a search string.
- `canSee(User user)`: Checks if a user can see this node.
- `canEdit(User user)`: Checks if a user can edit this node.
- `matches(NodeEndpoint<?> v)`: Checks if the node matches a given endpoint.
- `toString()`: Returns a string representation of the node.
- `saveOuts(Consumer<File> writingFiles)`: Saves outgoing nodes to files.
- `saveIns(Consumer<File> writingFiles)`: Saves incoming nodes to files.
- `directory()`: Returns the directory associated with this node.
- `outsDirectory()`: Returns the directory for outgoing nodes.
- `id()`: Returns the node's ID.
- `setID(int id)`: Sets the node's ID.
- `hashCode()`: Returns the hash code of the node.
- `equals(Object obj)`: Checks if this node is equal to another object.
- `toVertex()`: Converts the node to a vertex representation

## Nested Classes
- `BasicView`: An endpoint for basic node information.
- `Nav2`: An endpoint for navigation.
- `GraphView`: An endpoint for graph visualization.
- `OutNodeDistribution`: An endpoint for outgoing node distribution.