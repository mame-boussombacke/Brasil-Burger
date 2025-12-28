<?php
namespace App\Controller;

use App\Repository\CommandeRepository;
use App\Repository\LigneCommandeRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/statistiques')]
class StatistiqueController extends AbstractController
{
    private CommandeRepository $commandeRepo;
    private LigneCommandeRepository $ligneRepo;

    public function __construct(CommandeRepository $commandeRepo, LigneCommandeRepository $ligneRepo)
    {
        $this->commandeRepo = $commandeRepo;
        $this->ligneRepo = $ligneRepo;
    }

    #[Route('/', name: 'statistiques_index')]
    public function index(): Response
    {
        // Commandes
        $commandesEnCours = $this->commandeRepo->findEnCoursDuJour();
        $commandesValidees = $this->commandeRepo->findValideesDuJour();
        $commandesAnnulees = $this->commandeRepo->findAnnuleesDuJour();

        // Recettes
        $recettesJour = $this->commandeRepo->recettesDuJour();

        // Top burgers & menus
        $topBurgers = $this->ligneRepo->burgersPlusVendusDuJour();
        $topMenus = $this->ligneRepo->menusPlusVendusDuJour();

        return $this->render('statistiques/index.html.twig', [
            'commandesEnCours' => $commandesEnCours,
            'commandesValidees' => $commandesValidees,
            'commandesAnnulees' => $commandesAnnulees,
            'recettesJour' => $recettesJour,
            'topBurgers' => $topBurgers,
            'topMenus' => $topMenus
        ]);
    }
}
