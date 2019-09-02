### Total Score: 21/25

### Answers Score: 11/15
- Fibonacci: 4/5
testInductiveCase failed because Fibonacci returned 1 for n=2
- Ball: 1/1
- BallContainer: 2/4
Yes, but the first approach has its own advantages as well!
The advantage of computing the volume only when necessary is that other methods
that change the conceptual total volume in the BallContainer don't have to worry
about updating a field that tracks the total volume.  This advantage is
particularly noticable when you begin to extend the BallContainer class to
include more such methods.  You are more prone to error when you have to keep
the unrelated concept of total volume in your head at the same time as
implementing new methods.

Another minor advantage is that performance is better in the case that the
volume is seldom accessed.  In this case, the second implementation is keeping
track of the total volume for little reason.
- Box: 4/5
(-0) A high level description would have sufficed
Yes, but the second implementation can be better when getBallsFromSmallest is called
often, as repeat calls will be more efficient.

### Style Score: 10/10
(-0) When selecting a greeting in `RandomHello`, the best style would use the length
of the array to specify the maximum value for the random integer generation:
```
String nextGreeting = greetings[rand.nextInt(greetings.length)];
```
Notice how this benefits us later on if we wanted to change the number of
possible greetings in the array.

(-0) Your BallContainer add/remove methods are more complicated than they need to be.
Look at the documentation for Set.add and Set.remove and see whether you need to
explicitly handle cases the cases of adding something that already exists in the
set and removing something that doesn't exist in the set.

(-0) A more neat way to write ball comparison is to use `Double.compare`.  Check out
the Java documentation of these methods to see how you might use them.
