# FiniteStateMachine
Fun project to implement Finite State Machine

* FSMDriver.java main method is starting point

Assumptions
* Producer can produce an event in one FSM only
* Subscriber can subscribe to any event across FSMs

Yet to be done
* Some of the entities attributes are un used 
* Some optimization like encapsulating FSM within producer , using state entity etc. are yet to be done


Output
```
. . FSM constructed . . 
1 A
2 L
No of FSM 2
. . Producers constructed . . 
 P : 2|L
 P : 1|A
No of Producers 2
e1
1,2
e17
3,4
ALL
5
. . Event constructed . . 
Event :ALL | 1
Event :e17 | 2
Event :e1 | 2
No of Events 3
I am notifying subscribers
Event Name is : e1
Event Name is : e1

Process finished with exit code 0
```
