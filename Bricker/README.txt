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

