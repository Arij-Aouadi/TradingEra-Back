package tn.esprit.shadowtradergo.RestControllers;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import tn.esprit.shadowtradergo.Services.Classes.ChatBotService;
import tn.esprit.shadowtradergo.openai.OutputDto;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*",maxAge=3600)

//@CrossOrigin(origins = "http://localhost:4200")

public class ChatBotController {
    private final ChatBotService chatBotService;

    @GetMapping("/admin/livechat/{question}")
    public String summarizeComplaint(@PathVariable String question) {
        OutputDto out = null;
        try {
            // Refine the prompt to specify that it's for the "TradingEra" game
            String prompt = "/n /n Bienvenue dans TradingEra, le jeu de trading où vous intervenez dans la salle de marchés des actions/options en consultant le portefeuille et les cours de marchés, en effectuant des achats/ventes. "
                    + "Vous pouvez acheter ou vendre une action/option au prix du marché ou à un prix déterminé à l'avance, c'est-à-dire lorsque le titre a un cours égal à celui prévu dans l'ordre de vente ou d'achat. "
                    + "La vente à découvert n'est pas autorisée. Le logiciel gère un carnet d'ordres trié par ordre de prix de ventes/achats croissant. Une transaction ne peut se réaliser que lorsqu'un acheteur "
                    + "et un vendeur ont décidé d'un prix de vente/achat commun. Le carnet d'ordres est visible sur l'interface du jeu. Si un ordre ne peut pas être passé en entier par manque de contrepartie, "
                    + "une transaction est effectuée sur ce qui peut être négocié, laissant le reste dans le carnet d'ordres. Une aide à la décision sous forme de graphes d'analyse technique (chandeliers japonais, moyennes mobiles, etc.) est fournie. "
                    + "L'interface propose une aide en ligne sur les produits et le vocabulaire financier. Les flux boursiers sont simulés par des modèles mathématiques avec la possibilité d'introduire des discontinuités "
                    + "simulant des événements macroéconomiques (krach boursier, par exemple). Obtenez des informations détaillées sur le monde du trading en posant vos questions.";

            out = chatBotService.sendPrompt(prompt + question);
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération de la réponse du backend : " + e.getMessage());
            throw new RuntimeException(e);
        }
        return out.getAnswer();
    }

}
