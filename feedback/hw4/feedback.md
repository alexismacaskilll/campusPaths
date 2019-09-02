### Total Score: 27/36

### Answers Score: 19/26
- Pseudocode (Problem 0): 8/8
- Altered Rep Invs (Problems 1a, 2b, and 2c): 4/6
2b) Weakening the rep invariant to not require RatTerms with zero coefficients to
have zero exponents will also cause changes in checkRep, equals, getExpt, and hashCode. 
2c) Additionally, changes to checkRep would need to be made to reflect this new 
representation invariant.
- Mutability (Problem 1b): 1/2
1b) Additionally, violates the @modifies of the method spec.
- checkRep Usage (Problems 1c, 2a, and 3a): 4/6
1c) No mention of the "final" keyword to guarantee immutability by the java compiler.
- RatPoly Design (Problem 3b): 2/4
3b) No advantage listed.

### Code Style Score: 8/10

#### Specific Feedback
Great use of invariants!
In RatPoly.isNaN, just return the boolean expression istead of putting it in an if loop
RatPoly.newCopy is unnecessary - you can just use list's copy constructor

#### General Feedback

Use defensive programming
