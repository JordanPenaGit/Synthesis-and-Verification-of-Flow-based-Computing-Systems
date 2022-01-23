Background:
BDD and Crossbar formats represent non-volatile memory devices. They are a two terminal device with programmable resistance and that device acts as a switch. 
BDDs can be mapped to a crossbar design. The point of this project was to create a way to evaluate a BDD or crossbar design, map a BDD to a crossbar or check if a BDD and a crossbar are equivalent.  

============================================
Compiling and running it:
For BDD.java
After compiling, specifiy in the terminal argument the path of your bdd file.
For xbar.java
Similar process 

After running either or both, it will generate a text file that contains the truth table and the results.
When both text files are generated, running evaluate.java will check if the BDD and xbar files are equivalent.

Running Mapping.java will convert a user specified bdd into a crossbar equivalent, generating "newxbar.xbar"

============================================

Explanation of code:
BDD.java
vars 4
nodes 7
1 2 7 1
2 3 4 2
3 5 7 3
4 6 7 4
5 6 7 4
6 -1 -1 1
7 -1 -1 0
The first line specifies the number of unique Boolean variables. The second line specifies the number of nodes N in the BDD. The subsequent N lines describe the N nodes in the BDD.
For the internal nodes, the four numbers denote the node_id, left_child_node_id, right_child_node_id, and the decision_variable_id. The left_child is the positive branch in the BDD with respect to the Boolean variable decision_variable_id. The decision_variable_id for xi is i. The first line "1 2 7 1" specifies that the node labeled "1" represents a decision on the variable x1 with the positive edge pointing to node 2 and the negative edge pointing to node 7. For the terminal nodes, the four numbers denote node_id, -1, -1, function_output. The "-1" basically indicates that there are no child nodes. The function_output indicates the Boolean 1 or Boolean 0. 




