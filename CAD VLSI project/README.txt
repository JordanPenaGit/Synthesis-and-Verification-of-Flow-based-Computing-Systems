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

So, within the code, after taking the variables of how many variables there are, we create a 2^n truth table. With every possible combination, we run each scenario through the BDD and gives us a result. For example, we have 1001. 1 will lead us to 2 (the positive branch) , at 2 we have 0 (negative branch) so we take 4. 4 we have 0 again which leads us to 7. At 7, since they have no children, we end it at the number on the 4th column, so 0. We save all of these results for later in a text file.


Xbar.java

vars 4
rows 4
cols 3
0 1 -2 
-3 4 0 
99 -1 0
99 0 3

The first line specifies the number of variables. The second line specifies the number of crossbar rows. The third line specifies the number of columns. The subsequent R rows specify the state of memristors in the corresponding rows. Memristors that are on (off) are encoded with the values 99 and 0, respectively. Boolean variables xi and ¬xi are encoded using i and -i, respectively.

In the code itself, again we generate a truth table and create 2^n different inputs for the crossbar. After we've done that, we run all possible combinations through an evaluation function and then when we have that, we run a depth first search algorithm as we need to see if there is indeed a path from input of the memristor to the output (usually from the bottom left node to the top left).
If there is a path, we print 1. Otherwise, it's 0. 
When that is done, we save the truthtable with the results to a text file.

evaluate.java
When both text files from the xbar.java and BDD.java are created, we can check if they are equivalent by simply checking if the results from both truthtables are equivalent. They must be same size truth tables and if there is a single results that does not match then it returns not equivalent.

Mapping.java
This one is a little more tricky. The purpose of Mapping.java is to create a xbar equivalent of a BDD file. When looking at the BDD file, we look at the possible paths from one node to the other and exclude all the paths that would lead to a node that points to 0. The format of the xbar being created has the rows being the nodes of the BDD and the columns are the edges between the nodes.

