package arbitrage;


import org.bitcoinj.core.Base58;
import org.p2p.solanaj.core.Transaction;
import org.p2p.solanaj.core.Account;
import org.p2p.solanaj.core.PublicKey;

import org.p2p.solanaj.rpc.Cluster;
import org.p2p.solanaj.rpc.RpcClient;

public class SolanaClient {
    private final RpcClient client;

    public SolanaClient() {
        // Connect to Solana Testnet or Mainnet
        this.client = new RpcClient(Cluster.TESTNET);
    }

    // Method to get account balance
    public long getBalance(String publicKey) {
        try {
            return client.getApi().getBalance(new PublicKey(publicKey));
        } catch (Exception e) {
            System.err.println("Failed to fetch balance: " + e.getMessage());
            return -1;
        }
    }

    // Method to transfer lamports
    public String transferLamports(String fromSecretKey, String toPublicKey, long lamports) {
        try {
            Account fromAccount = new Account(Base58.decode(fromSecretKey));
            PublicKey toKey = new PublicKey(toPublicKey);

            Transaction transaction = new Transaction();
           // transaction.addInput(SystemProgram.transfer(fromAccount.getPublicKey(), toKey, lamports));

            return client.getApi().sendTransaction(transaction, fromAccount);
        } catch (Exception e) {
            System.err.println("Failed to transfer lamports: " + e.getMessage());
            return null;
        }
    }
}
