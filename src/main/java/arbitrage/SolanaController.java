package arbitrage;

import arbitrage.SolanaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SolanaController {
    @Autowired
    private SolanaService solanaService;

    // Endpoint to get account balance
    @GetMapping("/solana/balance")
    public long getBalance(@RequestParam String publicKey) {
        return solanaService.getBalance(publicKey);
    }

    // Endpoint to transfer lamports
    @PostMapping("/solana/transfer")
    public String transferLamports(@RequestParam String fromSecretKey,
                                   @RequestParam String toPublicKey,
                                   @RequestParam long lamports) {
        return solanaService.transferLamports(fromSecretKey, toPublicKey, lamports);
    }
}
