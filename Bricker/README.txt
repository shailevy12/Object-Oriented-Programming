shai.levy1
315262907

A. Explain how you planned each behavior you implemented - class lists, design templates
   that the departments implements, whether following your decision or following the planning of
   DanoGameLab.
   =============================================================================================

   brick_strategies package:
   ------------------------
   I built a basic strategy class which removes the brick after a collision has occurred.
   I also built an abstract class that is basically a decorator that each time accepts the base class as
   a parameter and adds its own unique strategy to it.
   At the end I've created a factory that randomly selects a strategy for each brick and
   returns an instance of it.

   gameobjects package:
   --------------------
   I built for each object of the game a class of its own that behaves according to the role of this object
   in the game. Each such class descends from the parent class Game Object.
   In addition, for status definers I have built a new abstract class that implements all of their
   common behaviors, for example: disappear after collide with paddle.
   For each status definer I have created a class that inherits from the abstract class and implements
   its unique behavior. (In my case, a clock which set the pace of the game if it collides with the paddle)

   BrickerGameManager:
   -------------------
   The main class of the game, where all the classes are created and all methods have been called.
   This class maintaining the main function so the program can run.


B. If this is a design template you have chosen - why did you choose these design templates? Why was
   It is better to use them than to design the code differently.
   =================================================================================================

   All of my design templates were inspired by Campus IL, the unique design template that I chose myself
   was to create an abstract class of status definer objects.
   I chose to do this that way because status definers objects have a lot of common behaviors and this
   abstract class will prevent duplicate code and also will allow us to add new status definers son classes
   more easily to our programs .
   When we want to create a new son class of status definers, we can only consider the unique behavior
   of the status definer which we want to create, without considering the common behaviors of all the status
   definers.


C. Algorithms:
   ============

   1. aLotStudyTime:
   -----------------
   i. We will first sort the two arrays in ascending order. Then we will go through all the tasks,
    and for each task we will go through all the time slots, we will check if the task enters in this time
    slot, if it is we will get out the loop and continue to the next task from the time window where we
    stopped (each time slot is allowed to have only one task.
    Since the arrays are sorted in ascending order, there is no time window j < k to contain the i + 1 task,
    when k is the timeslot which contain the i task.

   ii. Running time: at the worst case, for all tasks there is no time slot to contain it. So we will go
    along n tasks, and for the first task we will go for m time slots, so we get O(mXn).

   2. aMinLeap:
   -----------------
   i. We will go through a loop on all the jumping options of the leaves (LeapNum).
    We will start with the first leaf and each time we will select the maximum number of steps
    (in order to make as few jumps as possible) from the options.
    When we finish the possible number of steps of this leaf, we will update the possible number
    of steps to be the farthest leaf number we have reached less the number of leaf we are standing on now.
    Throughout the run we will set up a counter that tells us the total amount of jumps we have made.

   ii. Running time: at the worst case, each leave will have only one possible step, so we will go trough
    n leave (when n is the number of leaves) and will get a O(n) running time.

   3. Buckets:
    -----------------
   i. We will divide the problem into sub-problems, so that each time we have to bring j liters when j is
    in the range between 1 to n.
    At each stage of the iteration we have 2 options, either take 2 liters, or take one liter, so we will
    get that for a j-size problem our number of options to bring the water will be the number of
    options for a j-2-size problem plus the number of options for j-1-size problem.
    so we will build a table in size n, there we will keep in table[j] the number of options for
    j-size problem. We will fill the table from 1 to n using this recursive formula:

                                 table[j] = table[j-1] + table[j-2].
    when the base case will be: table[1]=1, table[2]=2


   ii. Running Time: we go through the table and fill it from 1 to n, doing operations that cost O(1).
    so we will do n operations mult by O(1) and get O(n) running time.

   4.     TreesNum:
     ----------------
   i. We will initialize a table to be of size nXn and fill it in rows from 1 to n, in each cell of the table
    we will keep the number of existing structural trees for the left / right sub tree and sum accordingly.
    That is for i the number of nodes in the right subtree and j the number of nodes in the left subtree we
    will get that:
                table [i][j] = table[i - 1][j]  if i > 0 and j > i
                table [i][j] += table[i][j-1]   if also j>0

    We will pay attention that this problem can be solved ny Catalan numbers, but I chose to implement the
    algorithm that way because Yoav wrote in the forum that its suppose to be a dynamic solution.

    ii. We will fill the table which its size if nXn, with O(1) cost of each operation. So we will get
    running time of O(nXn) = O(n^2).



