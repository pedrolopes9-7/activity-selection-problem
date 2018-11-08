# activity-selection-problem
Greedy, Dynamic Programming and Backtracking Algorithms for the Activity Selection Problem

This program solves the problem to select a set of compatible activities from a input set of activities. An activity is compatible with another, if their time interval (finish time - start time) is comptabible. The problem is to find the optimal subset (with maximum size) of activities with the property of compatibility betwwen every pair of activities.

To compile: 
```bash
  $ javac Main.java
```

To run:
```bash
  $ java Main -i <instance_name> -g|-d|-b
```

-g: Greedy Algorithm
-d: Dynamic Programming Algorithm
-b: Backtracking Algorithm

Instances can be found on ~/instances/ directory. Any number of intances with any size can be generated by:

```bash
  $ java Main -k
```

Make shure to change the global variables FILE_SIZE and FILE_QNT (size of every instance and quantity of instances, respectively).
