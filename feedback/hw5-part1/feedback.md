### Total Score: 20/25

### Answers Score: 15/20
- Problem 1a: 6/7
The representation invariant for `IntQueue2` ought to be:
```
entries != null && 0 <= front < entries.length && 0 <= size <= entries.length
```

- Problem 1b: 1/1
- Problem 1c: 5/6
For #6, it's not necessary for the field to be public and non-final.  This
can cause representation exposure no matter what the modifiers on the field
are.

- Problem 2: 3/3
- Problem 3: 0/3

### Following Directions Score: 5/5



The below scores are separate:

### Documentation Score: 2/3

Would be helpful to specify what exactly it means for two edges to be equal.

### Design Score: 3/3

Why are there two classes of edges that are fairly identical?  Remove one of
them.

### Testing Score: 3/3

#### Code Review Feedback

None.