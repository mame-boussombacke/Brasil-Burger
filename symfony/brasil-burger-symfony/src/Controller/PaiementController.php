<?php
namespace App\Controller;

use App\Entity\Paiement;
use App\Entity\Commande;
use App\Form\PaiementType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/paiement')]
class PaiementController extends AbstractController
{
    #[Route('/', name:'paiement_index')]
    public function index(EntityManagerInterface $em): Response
    {
        $paiements = $em->getRepository(Paiement::class)->findAll();
        return $this->render('paiement/index.html.twig', compact('paiements'));
    }

    #[Route('/new/{commandeId}', name:'paiement_new')]
    public function new(int $commandeId, Request $request, EntityManagerInterface $em): Response
    {
        $commande = $em->getRepository(Commande::class)->find($commandeId);
        if(!$commande){
            $this->addFlash('error','Commande introuvable');
            return $this->redirectToRoute('commande_index');
        }

        // Vérifie si paiement déjà effectué
        $existing = $em->getRepository(Paiement::class)->findOneBy(['commande'=>$commande]);
        if($existing){
            $this->addFlash('error','Paiement déjà effectué');
            return $this->redirectToRoute('commande_index');
        }

        $paiement = new Paiement();
        $paiement->setCommande($commande);

        $form = $this->createForm(PaiementType::class, $paiement);
        $form->handleRequest($request);

        if($form->isSubmitted() && $form->isValid()){
            $em->persist($paiement);
            $em->flush();
            $this->addFlash('success','Paiement effectué !');
            return $this->redirectToRoute('paiement_index');
        }

        return $this->render('paiement/new.html.twig',['form'=>$form->createView(),'commande'=>$commande]);
    }
}
