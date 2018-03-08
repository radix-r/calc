/**
 * Calc
 * Author: Ross Wagner
 * 3/7/2018
 * 
 * This is a breadth first search problem.
 * 
 * needs more documentation
*/

import java.util.*;

/**
 *
 * @author rwagner
 */
@SuppressWarnings("unchecked")// queue doesnt like being added to 142ish

public class calc{
    
    public static int MAX;
    public static int NUMDIGITS = 6;
    public static int START = 0;
    
    /**
     * This is so I can have a queue of nodes that can support having nulls for 
     * depth counting
     */
    public static class node{
        int value;
        
        public node(int value){
            this.value = value;
        }
        
    }
    
    
    /**
     * reads from stdin and runs BFS, prints number of levles decended to reach
     * target
     * @param args, command line agruments
     * 
     */
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        
        for(int i = 0; i < n; i++){
            int add = in.nextInt();
            int mult = in.nextInt();
            int div = in.nextInt();
            int target = in.nextInt();
            
            MAX = (int) Math.pow(10.0, (double)NUMDIGITS);
            
            
            
            int numSteps = BFS(add, mult, div, target, START);
            
            System.out.println(numSteps);
            
            
            
        }
        
    }
    
    /**
     * uses nulls to mark levels
     * 
     * 
     * @return minimum number of operations using add, mult, and 
     * div that must be taken to reach target. Returns -1 if target
     * is unreachable.
     */
    public static int BFS(int add, int mult, int div, int target, int start){
        // if used[i] == true i has been visited. I originaly used hashSet but it
        // was too slow
        boolean[] used = new boolean[MAX];
        
        // queue to hold nodes to travers. null means next level reached
        Queue<node> q = new LinkedList<node>();
        q.add(new node(start));
        q.add(null);
        
        // number of steps it take to get to target
        int numSteps = 0;
        
        if(start == target) return numSteps;
        
        // returning 1 less than expected
        numSteps++;
        
        while(!q.isEmpty()){
            node cur = q.poll();
            boolean found;
            node next;
            
            if (cur == null){
                // new level reache
                numSteps++;
                q.add(null);
                if(q.peek() == null) break; // consecutive nulls means end reached
                else continue;
            }
            
            // for each neighbor of cur (cur*mult, cur+add, cur/div) check if target
            // and add to queue if unique
            next = new node((cur.value * mult)%MAX);// only take last N didits
            found = check(next, target, q, used); 
            if (found) return numSteps;
            
            next = new node((cur.value + add)%MAX);
            found = check(next, target, q, used); 
            if (found) return numSteps;

            
            next = new node(cur.value / div); // always shrinks no need to mod
            found = check(next, target, q, used); 
            if (found) return numSteps;

            
        }
        
        // all reachable nodes searched target not reachable   
        return -1;
    }
    
   
    /**
     * A simple helper function that checks if a node's value is the target, adds
     * it to the queue, then marks it as used
     */
    public static boolean check(node next, int target, Queue q, boolean[] used){
        if(!used[next.value]){
                if(next.value == target){
                    return true;
                }
             
                q.offer(next);// this guy gives an unchecked warning 
                used[next.value] = true;   
        }
        return false;
    }
}
