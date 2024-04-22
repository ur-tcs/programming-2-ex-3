# Programming 2 - Exercise 2 : Pattern Matching

This exercise set is intended to help you practice pattern matching.

As in the every exercise you need to obtain the exercise files. Clone or fork the repository: `git clone https://github.com/ur-tcs/programming-2-ex-3.git` or download it by clicking on the green `<code>` button and select `download`.

Your are allowed to copy/clone/fork this repository, but not to share solutions of the exercise in any public repository or web page.

In previous exercises, we used `if` conditionals and `.` field accessors to write functions on data types such as lists or trees. This week, we’ll use pattern matching to make these functions more succinct and readable. We’ll move from this:

```
def reduceIf(f: (Int, Int) => Int)(l: IntList): Int =
  if l.isEmpty then throw IllegalArgumentException("Empty list!")
  else if l.tail.isEmpty then l.head
  else f(l.head, reduceIf(f)(l.tail))
```

to this:

```
def reduceMatch(f: (Int, Int) => Int)(l: IntList): Int =
  l match
    case IntNil              => throw IllegalArgumentException("Empty list!")
    case IntCons(hd, IntNil) => hd
    case IntCons(hd, tl)     => f(hd, reduceMatch(f)(tl))
```

Most functional programmers find the second one much more readable, because it aligns the way the data is *destructed* (taken apart into a head and a tail) and the way the data is *constructed* (assembled from a head and a tail):

```
def constructDestruct =
  IntCons(1, IntCons(2, IntNil)) match
    case IntCons(a, IntCons(b, IntNil)) =>
      println(f"Found $a and $b")
    case _ => throw Exception("Not possible!")
```

**Warning:** Previously, we wrote `IntNil()` for empty `IntList`s. Now that we know about `enum`s and case classes, we can use the more succinct and convenient syntax `IntNil` (no parentheses).

## Weekdays

Days of the week are a great example of simple enumerations. So, which day is tomorrow? let’s implement a function to find out.

The `enum` representing weekdays is defined as follows:

```
enum Weekday:
  case Monday
  case Tuesday
  case Wednesday
  case Thursday
  case Friday
  case Saturday
  case Sunday
```

Complete the following two functions:

1. `next` returns the next day of the week:
   ```
   def next(d: Weekday): Weekday =
     ???
   ```
2. `prev` returns the previous day of the week:
   ```
   def prev(d: Weekday): Weekday =
     ???
   ```

**Hint:** Want to test your code? Run testOnly WeekdayOpsTest in sbt.

**Note:** This exercise is taken from [Logical Foundations](https://softwarefoundations.cis.upenn.edu/lf-current/Basics.html), a book about mathematical proofs of programs, and translated into Scala.

## Tri-booleans

By now you’re very familiar with Booleans. But in real life, not every thing is `Yes` or `No`: sometimes we just don’t know! Tri-boolean logic helps with this by adding an indeterminate value, `Maybe`:

```
enum TriBool:
  case Yes
  case No
  case Maybe
```

`Maybe` is like a Boolean value that is not yet known.

1. Take the time to think of how `Maybe` combine with `Yes` and `No`.

- If I have two objects, one which is for sure blue, and one that may be blue, then can I say that I have at least one blue item? Is that for sure or maybe? What does this entail about the “or” operator on tri-Booleans?

- If I have the same two objects (one blue, one maybe blue), can I conclusively say that not all or my objects are blue? Can I promise that they are all blue? What does this entail about the “and” operator on tri-Booleans?

2. Implement the following operations (we expect that you’ll find pattern-matching very nice for that!)

```
def neg(b: TriBool): TriBool =
    ???
```
```
  def and(b1: TriBool, b2: TriBool): TriBool =
    ???
```
```
  def or(b1: TriBool, b2: TriBool): TriBool =
    ???
```
```
  def nand(b1: TriBool, b2: TriBool): TriBool =
    ???
```

**Hint:** Want to test your code? Run `testOnly TriBoolOpsTest` in `sbt`.

**Note:** `nand` is a very surprising operator. If you’re not familiar with it, inspect the test cases, or [read more about it!](https://en.wikipedia.org/wiki/Sheffer_stroke)

## Contexts

Now that you have a bit of experience with pattern matching, let’s use it to construct more complex types. In this exercise we’ll study *contexts*, which are essentially lists of keys and values. 

Our contexts associate names (`String`s) with values (`Int`s), and let us do the following:

- Create an empty context;
- Add an additional key/value pair (called a “binding”) to a context;
- Look up a key to retrieve the corresponding value;
- Remove a key and its corresponding value.

For instance, we can have a context which associates `"x"` with `1` and `"y"` with `2`. We can then look up the keys: looking up `"x"` in the context produces `1`, looking up `"y"` produces `2`, and looking up `"z"` or any other name results in a not-found error. We can add more mapping in the context: after associating `"z"` with `3`, looking `"z"` up in the new context will result in `3`, instead of an error.

Think of what Scala types and features you may use to represent a context before proceeding.

**Note:** If you want to experiment with contexts in the playground, make sure to add `import EnumContext.*` to bring `Cons` and `Empty` into the worksheet’s scope.

Implement the following three functions:

- `empty` returns an empty context:
  ```
  def empty: Context =
    ???
  ```
- `cons` forms a new context by associating a new pair of name-value in an existing context:
  ```
  def cons(name: String, value: Int, rem: Context) =
    ???
  ```
- `lookup` looks a name up in a given context:
  ```
  def lookup(ctx: Context, name: String): LookupResult =
    ???
  ```

  Notice the return type: we used another `enum`, called `LookupResult`, to capture two possible results: either finding a value, or indicating that the name could not be found.
  ```
  enum LookupResult:
    case Ok(v: Int)
    case NotFound
  ```
  
- `erase` drops **all** bindings with the given name in the context:
  ```
  def erase(ctx: Context, name: String): Context =
    ???
  ```

- Finally, `filter` drops all bindings that fail the test `pred` (i.e. for which `pred` evaluated to false):
  ```
  def filter(ctx: Context, pred: (String, Int) => Boolean): Context =
    ???
  ```

**Hint:** Want to test your code? Run `testOnly EnumContextTest` in `sbt`.

## Tree Mapping

Last week we worked with trees using a clunky API of `.left`, `.right`, and `.isEmpty`. This time, let’s do it the right way. And, to mix things up a bit, we’ll see a tree that contains values only at the leaves, instead of in every node.

The `IntTree` `enum` is defined as the following:

```
enum IntTree:
  case Leaf(value: Int)
  case Branch(left: IntTree, right: IntTree)
```

What is the enum representation of the tree below? Test your code via running `testOnly IntTreeOpsTest` in `sbt`.

*BILD*

Let's take a look at the `treeMap` function:
```
def treeMap(tree: IntTree, op: Int => Int): IntTree =
    ???
```

It takes a tree and a function, and creates a new tree by applying the function on the value of each leaf node in the tree.

For instance, let’s name the tree we just saw in the diagram as `t`. The following diagram dipicts the computation of `treeMap(t, x => x + 1)`.

*BILD*

*BILD*

## IntList

Let’s implement functions on `IntList` again, this time with pattern matching.

### polishEval

First, rewrite `polishEval` (from last week) with pattern-matching:

  ```
  def polishEval(l: IntList): (Int, IntList) =
    ???
  ```

Throw `InvalidOperationNumber exception` if the operator is not defined, and `InvalidExpression` if the input is not a valid polish-notation expression.

Compare your version new version with the original `if`-based implementation. Which one is more readable?

### zipWith

`map`, `fold`, and other functions that we have previously studied operate on a single list. We have already seen that some of these generalize to trees; here, we will see how one of them (`map`) generalizes to multiple lists. This function is called `zipWith` in Scala:

  ```
  def zipWith(l1: IntList, l2: IntList, op: (Int, Int) => Int): IntList =
    ???
  ```

Here is one possible specification for `zipWith`, using `l[n]` to mean “the n-th element of list `l`”:

Given two lists `xs` and `ys` and an operator `op`, let `zs` be `zipWith(xs, ys, op)`. Then, `zs` should have length `min(length(xs), length(ys))`, and the `i`-th element of `zs` should be `op(xs[i], ys[i])` for all `i`.

Note the part about the length of `zs`. For instance, `zipWith(IntNil, IntCons(1, Nil), (x, y) => x + y)` should equal to `IntNil`.

Implement `zipWith`. Do you see a connection with `map`?

### A modular implementation of zipWith

`zipWith` does a lot of work at once, and we can make it a bit more modular. For this, let’s define an intermediate type `IntIntList` of lists of pairs of ints:

```
enum IntIntList:
  case IntIntNil
  case IntIntCons(xy: (Int, Int), xs: IntIntList)
import IntIntList.*
```

1. Define a function `zip` to construct a list of pairs from a pair of lists:

  ```
  def zip(l1: IntList, l2: IntList): IntIntList =
    ???
  ```

2. Define a function `unzip` to construct a pair of lists from a list of pairs:

  ```
  def unzip(l: IntIntList): (IntList, IntList) =
    ???
  ```

3. Define a function `map2to1` on lists of pairs that produces a list of ints:

  ```
  def map2to1(op: (Int, Int) => Int)(l: IntIntList): IntList =
    ???
  ````

4 Rewrite `zipWith` using (some of) the functions above.
  
  ```
  def zipThenWith(l1: IntList, l2: IntList, op: (Int, Int) => Int): IntList =
    ???
  ```

5. Use `zipWith` to implement a function `movingWindow` on lists that returns sequences of consecutive pairs in its input list. For example, `movingWindow` applied to the list `a b c d e` should produce the list `ab bc cd de`.

  ```
  def movingWindow(l: IntList): IntIntList =
    ???
  ```

### extractSecond

Enumerations and case classes are often useful to distinguish multiple kinds of results. In an imperative language this would often be done with an exception: a function returns a result, and may throw various exceptions if the result cannot be computed. In Scala (and functional programming), we tend to use case classes instead, with one case per kind of result.

For example, a function that extracts the second element of a list might return a type like the following:

```
enum ExtractResult:
  case SecondElem(i: Int)
  case NotLongEnough
  case EmptyList
```

When the input list is empty, `extractSecond` returns `EmptyList`; when the input list is not long enough to have a second element (i.e. it only has one element), `extractSecond` returns `NotLongEnough`; finally, when the input list has a second element, the function returns it.

Implement `extractSecond`:

  ```
  def extractSecond(l: IntList): ExtractResult =
    ???
  ```
