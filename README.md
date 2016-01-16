# ChaosGame
Clojure program to make simple random dot fractals.

This is a leiningen project for a very simple chaos game program.  It uses the Seesaw graphics library.


Instructions: click on the canvas to add some corner points, then hit "Set Polygon".  Then add a starting point for the iteration.  Then hitting "Next!" will create a new point, which is the midpoint between the last iteration and a randomly chosen corner point.  The "+100" button performs 100 iterations.  

By default the new iteration is .5\*\<previous iteration\> + .5\*\<random corner\>.  You can change the coefficients to
(1-ratio)\*\<previous iteration\> + ratio\*\<random corner\> by setting the value of ratio (enter a number and hit return).
