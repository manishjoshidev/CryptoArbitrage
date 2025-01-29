package Backend.prediction.arbitrage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SolanaAPIController {

    @Autowired
    private SolanaAPIService solanaAPIService;

    @GetMapping("/solana/balance")
    public String getSolanaBalance(@RequestParam("publicKey") String publicKey) {
        return solanaAPIService.fetchBalance(publicKey);
    }
}
