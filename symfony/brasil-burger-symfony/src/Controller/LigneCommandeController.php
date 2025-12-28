<?php
namespace App\Controller;

use App\Entity\LigneCommande;
use App\Form\LigneCommandeType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/ligne-commande')]
class LigneCommandeController extends AbstractController
{
    #[Route('/', name:'ligne_commande_index')]
    public function index(EntityManagerInterface $em)
    {
        $lignes = $em->getRepository(LigneCommande::class)->findAll();
        return $this->render('ligne_commande/index.html.twig', compact('lignes'));
    }

    #[Route('/new', name:'ligne_commande_new')]
    public function new(Request $request, EntityManagerInterface $em)
    {
        $ligne = new LigneCommande();
        $form = $this->createForm(LigneCommandeType::class, $ligne);
        $form->handleRequest($request);

        if($form->isSubmitted() && $form->isValid()){
            $em->persist($ligne);
            $em->flush();
            $this->addFlash('success','Ligne de commande ajoutée !');
            return $this->redirectToRoute('ligne_commande_index');
        }

        return $this->render('ligne_commande/new.html.twig',['form'=>$form->createView()]);
    }

    #[Route('/edit/{id}', name:'ligne_commande_edit')]
    public function edit(LigneCommande $ligne, Request $request, EntityManagerInterface $em)
    {
        $form = $this->createForm(LigneCommandeType::class, $ligne);
        $form->handleRequest($request);

        if($form->isSubmitted() && $form->isValid()){
            $em->flush();
            $this->addFlash('success','Ligne de commande modifiée !');
            return $this->redirectToRoute('ligne_commande_index');
        }

        return $this->render('ligne_commande/edit.html.twig',['form'=>$form->createView()]);
    }

    #[Route('/delete/{id}', name:'ligne_commande_delete')]
    public function delete(LigneCommande $ligne, EntityManagerInterface $em)
    {
        $em->remove($ligne);
        $em->flush();
        $this->addFlash('success','Ligne de commande supprimée !');
        return $this->redirectToRoute('ligne_commande_index');
    }
}
