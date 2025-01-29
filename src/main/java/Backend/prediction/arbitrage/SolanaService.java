
package Backend.prediction.arbitrage;

import org.bitcoinj.core.Base58;
import org.p2p.solanaj.core.PublicKey;
import org.p2p.solanaj.core.Account;
import org.p2p.solanaj.programs.SystemProgram;
import org.p2p.solanaj.core.Transaction;
import org.p2p.solanaj.rpc.Cluster;
import org.p2p.solanaj.rpc.RpcClient;
import org.springframework.stereotype.Service;

@Service
public class SolanaService {
    // Declare RpcClient as a private final field
    private final RpcClient client;

    // Constructor to initialize RpcClient with the desired Solana cluster
    public SolanaService() {
        // Choose between TESTNET, MAINNET, or DEVNET
        this.client = new RpcClient(Cluster.TESTNET); // Replace TESTNET with MAINNET if needed
    }

    // Method to get account balance
    public long getBalance(String publicKey) {
        try {
            return client.getApi().getBalance(new PublicKey(publicKey));
        } catch (Exception e) {
            System.err.println("Failed to fetch balance: " + e.getMessage());
            return -1; // Return -1 to indicate an error
        }
    }

    // Method to transfer lamports
    public String transferLamports(String fromSecretKey, String toPublicKey, long lamports) {
        try {
            // Create account from secret key
            Account fromAccount = new Account(Base58.decode(fromSecretKey));
            PublicKey toKey = new PublicKey(toPublicKey);

            // Create a transaction with a transfer instruction
            Transaction transaction = new Transaction();
            transaction.addInstruction(SystemProgram.transfer(fromAccount.getPublicKey(), toKey, lamports));

            // Send the transaction
            return client.getApi().sendTransaction(transaction, fromAccount);
        } catch (Exception e) {
            System.err.println("Failed to transfer lamports: " + e.getMessage());
            return null; // Return null to indicate an error
        }
    }
}
