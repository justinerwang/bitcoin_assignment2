// import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

/* CompliantNode refers to a node that follows the rules (not malicious)*/
public class CompliantNode implements Node {

    Set<Transaction> broadcastedTxs;
    Set<Transaction> proposedTxs;
    boolean[] followees;
    int roundIndex; 
    int numRounds;

    public CompliantNode(double p_graph, double p_malicious, double p_txDistribution, int numRounds) {
        this.broadcastedTxs = new HashSet<Transaction>();
        this.proposedTxs = new HashSet<Transaction>();
        this.roundIndex = 0;
        this.numRounds = numRounds;
        
    }

    public void setFollowees(boolean[] followees) {
        this.followees = followees;
    }

    public void setPendingTransaction(Set<Transaction> pendingTransactions) {
        this.proposedTxs = pendingTransactions;
    }

    public Set<Transaction> sendToFollowers() {
        this.roundIndex ++;
        if(this.roundIndex == this.numRounds) {
            return this.broadcastedTxs;
        }
        
        broadcastedTxs.addAll(proposedTxs);
        Set<Transaction> temp = new HashSet<Transaction>();
        for(Transaction tx: this.proposedTxs) {
            temp.add(tx);
        }
        proposedTxs.clear();
        return temp;
    }

    public void receiveFromFollowees(Set<Candidate> candidates) {
        
        for(Candidate c: candidates) {
            if(!broadcastedTxs.contains(c.tx)){
                this.proposedTxs.add(c.tx);
            }
        }
    }
}
