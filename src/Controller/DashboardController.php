<?php

namespace App\Controller;

use App\Repository\CommandeRepository;
use App\Repository\LigneCommandeRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class DashboardController extends AbstractController
{
    #[Route('/dashboard', name: 'dashboard')]
    public function index(CommandeRepository $commandeRepo, LigneCommandeRepository $ligneRepo): Response
    {
        // Rassembler les statistiques succinctes pour le dashboard
        $commandesEnCours = count($commandeRepo->commandesEnCoursDuJour());
        $commandesValidees =($commandeRepo->commandesValideesDuJour());
        $commandesAnnulees = count($commandeRepo->commandesAnnuleesDuJour());
        $recettes = $commandeRepo->recettesDuJour();

        $topBurgersRaw = $ligneRepo->burgersPlusVendusDuJour();
        // Adapter la clé 'totalVendu' renvoyée par le repository en 'quantite' attendue par le template
        $burgersPlusVendus = array_map(function($b){
            return [
                'nom' => $b['nom'] ?? ($b['0'] ?? ''),
                'quantite' => isset($b['totalVendu']) ? (int)$b['totalVendu'] : ((int)($b['1'] ?? 0)),
            ];
        }, $topBurgersRaw);

        // Menus les plus vendus
        $topMenusRaw = $ligneRepo->menusPlusVendusDuJour();
        $menusPlusVendus = array_map(function($m){
            return [
                'nom' => $m['nom'] ?? ($m['0'] ?? ''),
                'quantite' => isset($m['totalVendu']) ? (int)$m['totalVendu'] : ((int)($m['1'] ?? 0)),
            ];
        }, $topMenusRaw);

        $stats = [
            'commandesEnCours' => $commandesEnCours,
            'commandesValidees' => $commandesValidees,
            'commandesAnnulees' => $commandesAnnulees,
            'recettes' => $recettes,
            'burgersPlusVendus' => $burgersPlusVendus,
            'menusPlusVendus' => $menusPlusVendus,
        ];

        return $this->render('dashboard/index.html.twig', ['stats' => $stats]);
    }
}
