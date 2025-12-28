<?php
namespace App\Controller;

use App\Entity\Commande;
use App\Entity\Burger;
use App\Entity\Menu;
use App\Entity\Complement;
use App\Entity\Zone;
use App\Entity\Livreur;
use App\Form\CommandeType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/commande')]
class CommandeController extends AbstractController
{
    #[Route('/', name:'commande_index')]
    public function index(EntityManagerInterface $em): Response
    {
        $commandes = $em->getRepository(Commande::class)->findBy([], ['dateCommande'=>'DESC']);
        return $this->render('commande/index.html.twig', compact('commandes'));
    }

    #[Route('/new', name:'commande_new')]
    public function new(Request $request, EntityManagerInterface $em): Response
    {
        $commande = new Commande();
        $form = $this->createForm(CommandeType::class, $commande);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            // calcul du total
            $total = 0;
            foreach($commande->getBurgers() as $burger) { $total += $burger->getPrix(); }
            foreach($commande->getMenus() as $menu) { $total += $menu->getPrixTotal(); }
            foreach($commande->getComplements() as $complement) { $total += $complement->getPrix(); }
            $commande->setTotal($total);

            $em->persist($commande);
            $em->flush();
            $this->addFlash('success','Commande créée !');
            return $this->redirectToRoute('commande_index');
        }

        return $this->render('commande/new.html.twig',['form'=>$form->createView()]);
    }

    #[Route('/edit/{id}', name:'commande_edit')]
    public function edit(Commande $commande, Request $request, EntityManagerInterface $em)
    {
        $form = $this->createForm(CommandeType::class, $commande);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            // recalcul total
            $total = 0;
            foreach($commande->getBurgers() as $burger) { $total += $burger->getPrix(); }
            foreach($commande->getMenus() as $menu) { $total += $menu->getPrixTotal(); }
            foreach($commande->getComplements() as $complement) { $total += $complement->getPrix(); }
            $commande->setTotal($total);

            $em->flush();
            $this->addFlash('success','Commande modifiée !');
            return $this->redirectToRoute('commande_index');
        }

        return $this->render('commande/edit.html.twig',['form'=>$form->createView()]);
    }

    #[Route('/delete/{id}', name:'commande_delete')]
    public function delete(Commande $commande, EntityManagerInterface $em)
    {
        $em->remove($commande);
        $em->flush();
        $this->addFlash('success','Commande supprimée !');
        return $this->redirectToRoute('commande_index');
    }

    #[Route('/change-etat/{id}/{etat}', name:'commande_change_etat')]
    public function changeEtat(Commande $commande, string $etat, EntityManagerInterface $em)
    {
        $commande->setEtat($etat);
        $em->flush();
        $this->addFlash('success','État de la commande mis à jour !');
        return $this->redirectToRoute('commande_index');
    }

    #[Route('/affecter-livreur/{id}/{livreurId}', name:'commande_affect_livreur')]
    public function affecterLivreur(Commande $commande, int $livreurId, EntityManagerInterface $em)
    {
        $livreur = $em->getRepository(Livreur::class)->find($livreurId);
        if($livreur){
            $commande->setLivreur($livreur);
            $em->flush();
            $this->addFlash('success','Livreur affecté !');
        }
        return $this->redirectToRoute('commande_index');
    }
}
