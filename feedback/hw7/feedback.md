### Total Score: 1/1
- Documenting Changes: 1/1

The below scores are separate:

### Specification Score: 2/3

Missing documentation of type variables.

Missing doucmentation of what the distance between two nodes means.

### Implementation Score: 2/3

Use of raw types in `equals` implementation.  Instantiate with wildcard types
instead.

Bit odd to have the node type be `CampusBuilding`.  The building names are
unnecessary fields.

### Design Score: 2/3

The edge label type of a graph is unnecessarily restrictive.

`loadGraph` should be part of the model, but it's in the `ModelConnector`.

### Testing Score: 2/3

Missing basic cases.  Test actual paths instead of purely edge cases?

#### Code Review Feedback

None.

#### Graded By: Alexey C Beall
